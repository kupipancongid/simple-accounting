package id.kupipancong.accounting.service;

import id.kupipancong.accounting.entity.Account;
import id.kupipancong.accounting.model.request.CreateAccountRequest;
import id.kupipancong.accounting.model.request.SearchAccountRequest;
import id.kupipancong.accounting.model.response.AccountResponse;
import id.kupipancong.accounting.repository.AccountRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
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
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ValidationService validationService;

    public Page<AccountResponse> search(SearchAccountRequest request){
        Specification<Account> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getId())){
                if (!request.getId().equals("")){
                    predicates.add(criteriaBuilder.equal(
                            root.get("id"), request.getId()
                    ));
                }
            }

            if (Objects.nonNull(request.getCode())){
                if (!request.getCode().equals("")){
                    predicates.add(criteriaBuilder.like(
                            root.get("code"), request.getCode()+"%"
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
            orders.add(criteriaBuilder.asc(root.get("code")));
            query.orderBy(orders);

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Account> accounts = accountRepository.findAll(specification, pageable);
        List<AccountResponse> accountResponses = accounts.getContent().stream().map(this::toAccountResponse).toList();

        return new PageImpl<>(accountResponses, pageable, accounts.getTotalElements());
    }

    public AccountResponse get(String id){
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );

        return toAccountResponse(account);
    }

    public Account getByCode(String code){
        Optional<Account> accountOptional = accountRepository.findAccountByCode(code);
        if (accountOptional.isEmpty()){
            return null;
        }else {
            return accountOptional.get();
        }
    }

    public AccountResponse create(CreateAccountRequest request){
        validationService.validate(request);
        Account accountParrent = null;

        if (request.getParrentCode() != null){
            accountParrent = accountRepository.findAccountByCode(request.getParrentCode()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent account not found")
            );
        }
        String accountCode = accountParrent != null ? accountParrent.getCode() + request.getCode() : request.getCode();
        if (accountRepository.existsByCode(accountCode)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account code exist");
        }

        Account account = new Account();
        account.setCode(accountCode);
        account.setDescription(request.getDescription());
        accountRepository.save(account);

        return toAccountResponse(account);
    }


    public AccountResponse toAccountResponse(Account account){
        return AccountResponse.builder()
                .id(account.getId())
                .code(account.getCode())
                .description(account.getDescription())
                .build();
    }

}
