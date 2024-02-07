package id.kupipancong.accounting.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    @JsonProperty("parent_code")
    private String parrentCode;
    @NotEmpty
    private String code;
    @NotEmpty
    private String description;
}
