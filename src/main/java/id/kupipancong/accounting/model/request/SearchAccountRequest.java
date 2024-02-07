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
public class SearchAccountRequest {
    private String id;
    private String code;
    private String description;
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
