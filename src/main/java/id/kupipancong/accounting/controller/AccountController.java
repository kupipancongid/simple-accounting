package id.kupipancong.accounting.controller;

import id.kupipancong.accounting.model.request.CreateAccountRequest;
import id.kupipancong.accounting.model.response.AccountResponse;
import id.kupipancong.accounting.model.response.WebResponse;
import id.kupipancong.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(
            path = "/api/accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> register(
            @RequestBody CreateAccountRequest request
    ){
        AccountResponse accountResponse = accountService.create(request);

        return WebResponse.<AccountResponse>builder()
                .data(accountResponse)
                .build();
    }
}
