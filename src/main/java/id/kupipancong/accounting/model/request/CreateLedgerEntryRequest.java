package id.kupipancong.accounting.model.request;

import id.kupipancong.accounting.enums.LedgerType;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLedgerEntryRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private LedgerType ledgerType;
    @NotNull
    private Date date;
    @NotNull
    private Double amount;
    @Nullable
    private String transactionId;
    @NotNull
    private String accountCode;
}
