package id.kupipancong.accounting.controller;

import id.kupipancong.accounting.model.request.CreateTransactionRequest;
import id.kupipancong.accounting.model.request.SearchTransactionEnteredRequest;
import id.kupipancong.accounting.model.request.SearchTransactionRequest;
import id.kupipancong.accounting.model.response.PagingResponse;
import id.kupipancong.accounting.model.response.TransactionEnteredResponse;
import id.kupipancong.accounting.model.response.TransactionResponse;
import id.kupipancong.accounting.model.response.WebResponse;
import id.kupipancong.accounting.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping(
            path = "/api/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TransactionResponse>> search(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "date", required = false) Date date,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "amount", required = false) Double amount,
            @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = true, defaultValue = "10") Integer size
    ){
        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .id(id)
                .date(date)
                .description(description)
                .amount(amount)
                .page(page)
                .size(size)
                .build();

        Page<TransactionResponse> transactionResponses = transactionService.search(request);
        return WebResponse.<List<TransactionResponse>>builder()
                .data(transactionResponses.getContent())
                .paging(
                        PagingResponse.builder()
                                .totalRecords(transactionResponses.getTotalElements())
                                .currentPage(transactionResponses.getNumber())
                                .totalPage(transactionResponses.getTotalPages())
                                .size(transactionResponses.getSize())
                                .nextPage(transactionResponses.isLast() ? null : transactionResponses.getNumber()+1)
                                .previousePage(transactionResponses.isFirst() ? null : transactionResponses.getNumber()-1)
                                .build()
                )
                .build();
    }
    @GetMapping(
            path = "/api/transactions/entered",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TransactionEnteredResponse>> search(
            @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = true, defaultValue = "10") Integer size
    ){
        SearchTransactionEnteredRequest request = SearchTransactionEnteredRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<TransactionEnteredResponse> transactionEnteredResponses = transactionService.searchEntered(request);
        return WebResponse.<List<TransactionEnteredResponse>>builder()
                .data(transactionEnteredResponses.getContent())
                .paging(
                        PagingResponse.builder()
                                .totalRecords(transactionEnteredResponses.getTotalElements())
                                .currentPage(transactionEnteredResponses.getNumber())
                                .totalPage(transactionEnteredResponses.getTotalPages())
                                .size(transactionEnteredResponses.getSize())
                                .nextPage(transactionEnteredResponses.isLast() ? null : transactionEnteredResponses.getNumber()+1)
                                .previousePage(transactionEnteredResponses.isFirst() ? null : transactionEnteredResponses.getNumber()-1)
                                .build()
                )
                .build();
    }


    @GetMapping(
            path = "/api/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransactionResponse> get(
            @PathVariable(name = "id", required = true) String id
    ){
        TransactionResponse transactionResponse = transactionService.get(id);
        return WebResponse.<TransactionResponse>builder()
                .data(transactionResponse)
                .build();
    }

    @PostMapping(
            path = "/api/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransactionResponse> register(
            @RequestBody CreateTransactionRequest request
    ){
        TransactionResponse transactionResponse = transactionService.create(request);

        return WebResponse.<TransactionResponse>builder()
                .data(transactionResponse)
                .build();
    }

}
