package id.kupipancong.accounting.service;

import id.kupipancong.accounting.entity.LedgerEntry;
import id.kupipancong.accounting.entity.Transaction;
import id.kupipancong.accounting.model.request.CreateTransactionRequest;
import id.kupipancong.accounting.model.request.SearchTransactionEnteredRequest;
import id.kupipancong.accounting.model.request.SearchTransactionRequest;
import id.kupipancong.accounting.model.response.LedgerEntryResponse;
import id.kupipancong.accounting.model.response.TransactionEnteredResponse;
import id.kupipancong.accounting.model.response.TransactionLedgerEntryResponse;
import id.kupipancong.accounting.model.response.TransactionResponse;
import id.kupipancong.accounting.repository.TransactionRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ValidationService validationService;

    @Autowired
    AccountService accountService;

    public TransactionResponse create(CreateTransactionRequest request){
        validationService.validate(request);
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTime(request.getTime());
        transaction.setDescription(request.getDescription());
        transaction.setType(request.getType());
        transactionRepository.save(transaction);

        return toTransactionResponse(transaction);
    }

    public Page<TransactionResponse> search(SearchTransactionRequest request){
        Specification<Transaction> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getId())){
                if (!request.getId().equals("")){
                    predicates.add(criteriaBuilder.equal(
                            root.get("id"), request.getId()
                    ));
                }
            }

            if (Objects.nonNull(request.getDescription())){
                if (!request.getDescription().equals("")){
                    predicates.add(criteriaBuilder.like(
                            root.get("description"), "%"+request.getDescription()+"%"
                    ));
                }
            }

            List<Order> orders = new ArrayList<>();
            orders.add(criteriaBuilder.asc(root.get("time")));
            query.orderBy(orders);

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);
        List<TransactionResponse> transactionResponses = transactions.getContent().stream().map(this::toTransactionResponse).toList();

        return new PageImpl<>(transactionResponses, pageable, transactions.getTotalElements());
    }
    public Page<TransactionEnteredResponse> searchEntered(SearchTransactionEnteredRequest request){
        Specification<Transaction> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            List<Order> orders = new ArrayList<>();
            orders.add(criteriaBuilder.asc(root.get("time")));
            query.orderBy(orders);

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);
        List<TransactionEnteredResponse> transactionEnteredResponses = transactions.getContent().stream().map(this::toTransactionEnteredResponse).toList();
        return new PageImpl<>(transactionEnteredResponses, pageable, transactions.getTotalElements());
    }


    public TransactionResponse get(String transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );

        return toTransactionResponse(transaction);
    }

    public Transaction getById(String id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow();

        return transaction;
    }

    public TransactionResponse toTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .time(transaction.getTime())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .build();
    }

    public TransactionEnteredResponse toTransactionEnteredResponse(Transaction transaction){
        return TransactionEnteredResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .time(transaction.getTime())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .transactionLedgerEntryResponses(transaction.getLedgerEntries().stream().map(this::toTransactionLedgerEntryResponse).collect(Collectors.toList()))
                .build();
    }

    public TransactionLedgerEntryResponse toTransactionLedgerEntryResponse(LedgerEntry ledgerEntry){
        return TransactionLedgerEntryResponse.builder()
                .id(ledgerEntry.getId())
                .ledgerType(ledgerEntry.getLedgerType())
                .date(ledgerEntry.getDate())
                .amount(ledgerEntry.getAmount())
                .accountResponse(ledgerEntry.getAccount() != null ? accountService.toAccountResponse(ledgerEntry.getAccount()) : null)
                .build();
    }

}
