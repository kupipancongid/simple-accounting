package id.kupipancong.accounting.service;

import id.kupipancong.accounting.entity.Account;
import id.kupipancong.accounting.entity.LedgerEntry;
import id.kupipancong.accounting.entity.Transaction;
import id.kupipancong.accounting.model.request.CreateLedgerEntryRequest;
import id.kupipancong.accounting.model.request.SearchLedgerEntryRequest;
import id.kupipancong.accounting.model.response.LedgerEntryResponse;
import id.kupipancong.accounting.repository.LedgerEntryRepository;
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
import java.util.Optional;

@Slf4j
@Service
public class LedgerEntryService {

    @Autowired
    ValidationService validationService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    LedgerEntryRepository ledgerEntryRepository;

    public LedgerEntryResponse create(CreateLedgerEntryRequest request){
        validationService.validate(request);
        if (request.getTransactionId() != null){
            if (ledgerEntryRepository.existsByAccount_CodeAndLedgerTypeAndTransaction_Id(request.getAccountCode(), request.getLedgerType(), request.getTransactionId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ledger exist");
            }
        }
        Account account;
        if (request.getAccountCode() != null){
            account = accountService.getByCode(request.getAccountCode());
            if (account == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found");
            }
        }else{
            account = null;
        }

        Transaction transaction;
        if (request.getTransactionId() != null){
            transaction = transactionService.getById(request.getTransactionId());
        }else {
            transaction = null;
        }

        if (ledgerEntryRepository.existsByAccount_CodeAndLedgerTypeAndTransaction_Id(request.getAccountCode(), request.getLedgerType(), request.getTransactionId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record exist");
        }

        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setLedgerType(request.getLedgerType());
        ledgerEntry.setDate(request.getDate());
        ledgerEntry.setAccount(account);
        ledgerEntry.setTransaction(transaction);
        ledgerEntry.setAmount(request.getAmount());
        ledgerEntryRepository.save(ledgerEntry);

        return toLedgerEntryResponse(ledgerEntry);
    }
    public Page<LedgerEntryResponse> search(SearchLedgerEntryRequest request){
        Specification<LedgerEntry> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            List<Order> orders = new ArrayList<>();
            orders.add(criteriaBuilder.asc(root.get("createdAt")));
            query.orderBy(orders);

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<LedgerEntry> ledgerEntries = ledgerEntryRepository.findAll(specification, pageable);
        List<LedgerEntryResponse> ledgerEntryResponses = ledgerEntries.getContent().stream().map(this::toLedgerEntryResponse).toList();

        return new PageImpl<>(ledgerEntryResponses, pageable, ledgerEntries.getTotalElements());
    }

    public LedgerEntryResponse get(String id){
        LedgerEntry ledgerEntry = ledgerEntryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ledger entry not found")
        );

        return toLedgerEntryResponse(ledgerEntry);
    }

    public LedgerEntryResponse toLedgerEntryResponse(LedgerEntry ledgerEntry){
        return LedgerEntryResponse.builder()
                .id(ledgerEntry.getId())
                .ledgerType(ledgerEntry.getLedgerType())
                .date(ledgerEntry.getDate())
                .amount(ledgerEntry.getAmount())
                .accountResponse(ledgerEntry.getAccount() != null ? accountService.toAccountResponse(ledgerEntry.getAccount()) : null)
                .transactionResponse(ledgerEntry.getTransaction() != null ? transactionService.toTransactionResponse(ledgerEntry.getTransaction()) : null)
                .build();
    }
}
