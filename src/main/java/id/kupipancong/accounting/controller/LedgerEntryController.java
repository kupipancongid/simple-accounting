package id.kupipancong.accounting.controller;

import id.kupipancong.accounting.model.request.CreateAccountRequest;
import id.kupipancong.accounting.model.request.CreateLedgerEntryRequest;
import id.kupipancong.accounting.model.request.SearchLedgerEntryRequest;
import id.kupipancong.accounting.model.request.SearchTransactionRequest;
import id.kupipancong.accounting.model.response.*;
import id.kupipancong.accounting.service.LedgerEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class LedgerEntryController {
    @Autowired
    LedgerEntryService ledgerEntryService;

    @GetMapping(
            path = "/api/ledger-entries",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<LedgerEntryResponse>> search(
            @RequestParam(value = "account_id", required = false) String accountId,
            @RequestParam(value = "transaction_id", required = false) String transactionId,
            @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = true, defaultValue = "10") Integer size
    ){
        SearchLedgerEntryRequest request = SearchLedgerEntryRequest.builder()
                .accountId(accountId)
                .transactionId(transactionId)
                .page(page)
                .size(size)
                .build();

        Page<LedgerEntryResponse> ledgerEntryResponses = ledgerEntryService.search(request);
        return WebResponse.<List<LedgerEntryResponse>>builder()
                .data(ledgerEntryResponses.getContent())
                .paging(
                        PagingResponse.builder()
                                .totalRecords(ledgerEntryResponses.getTotalElements())
                                .currentPage(ledgerEntryResponses.getNumber())
                                .totalPage(ledgerEntryResponses.getTotalPages())
                                .size(ledgerEntryResponses.getSize())
                                .nextPage(ledgerEntryResponses.isLast() ? null : ledgerEntryResponses.getNumber()+1)
                                .previousePage(ledgerEntryResponses.isFirst() ? null : ledgerEntryResponses.getNumber()-1)
                                .build()
                )
                .build();
    }

    @GetMapping(
            path = "/api/ledger-entries/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<LedgerEntryResponse> get(
            @PathVariable(name = "id", required = true) String id
    ){
        LedgerEntryResponse ledgerEntryResponse = ledgerEntryService.get(id);
        return WebResponse.<LedgerEntryResponse>builder()
                .data(ledgerEntryResponse)
                .build();
    }

    @PostMapping(
            path = "/api/ledger-entries",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<LedgerEntryResponse> create(
            @RequestBody CreateLedgerEntryRequest request
    ){
        LedgerEntryResponse ledgerEntryResponse = ledgerEntryService.create(request);

        return WebResponse.<LedgerEntryResponse>builder()
                .data(ledgerEntryResponse)
                .build();
    }
}
