package id.kupipancong.accounting.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchLedgerEntryRequest {
    private String accountId;
    private String transactionId;
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
