package id.kupipancong.accounting.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.kupipancong.accounting.enums.LedgerType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LedgerEntryResponse {
    private String id;
    @JsonProperty("ledger_type")
    @Enumerated(EnumType.STRING)
    private LedgerType ledgerType;
    private Date date;
    private Double amount;
    @JsonProperty("account")
    private AccountResponse accountResponse;
    @JsonProperty("transaction")
    private TransactionResponse transactionResponse;
}
