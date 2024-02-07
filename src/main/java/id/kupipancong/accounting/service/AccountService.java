package id.kupipancong.accounting.service;

import id.kupipancong.accounting.entity.Account;
import id.kupipancong.accounting.model.request.CreateAccountRequest;
import id.kupipancong.accounting.model.response.AccountResponse;
import id.kupipancong.accounting.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ValidationService validationService;

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

    private AccountResponse toAccountResponse(Account account){
        return AccountResponse.builder()
                .id(account.getId())
                .code(account.getCode())
                .description(account.getDescription())
                .build();
    }

}
