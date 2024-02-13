package id.kupipancong.accounting.model.request;

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
public class SearchTransactionEnteredRequest {
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
