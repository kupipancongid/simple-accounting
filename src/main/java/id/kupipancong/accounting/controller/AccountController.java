package id.kupipancong.accounting.controller;

import id.kupipancong.accounting.model.request.CreateAccountRequest;
import id.kupipancong.accounting.model.request.SearchAccountRequest;
import id.kupipancong.accounting.model.response.AccountResponse;
import id.kupipancong.accounting.model.response.PagingResponse;
import id.kupipancong.accounting.model.response.WebResponse;
import id.kupipancong.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(
            path = "/api/accounts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AccountResponse>> search(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ){
        SearchAccountRequest request = SearchAccountRequest.builder()
                .id(id)
                .code(code)
                .description(description)
                .page(page)
                .size(size)
                .build();

        Page<AccountResponse> accountResponses = accountService.search(request);
        return WebResponse.<List<AccountResponse>>builder()
                .data(accountResponses.getContent())
                .paging(
                        PagingResponse.builder()
                                .totalRecords(accountResponses.getTotalElements())
                                .currentPage(accountResponses.getNumber())
                                .totalPage(accountResponses.getTotalPages())
                                .size(accountResponses.getSize())
                                .nextPage(accountResponses.isLast() ? null : accountResponses.getNumber()+1)
                                .previousePage(accountResponses.isFirst() ? null : accountResponses.getNumber()-1)
                                .build()
                )
                .build();
    }

    @GetMapping(
            path = "/api/accounts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> get(
            @PathVariable(name = "id", required = true) String id
    ){
        AccountResponse accountResponse = accountService.view(id);
        return WebResponse.<AccountResponse>builder()
                .data(accountResponse)
                .build();
    }

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
