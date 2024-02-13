package id.kupipancong.accounting.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.kupipancong.accounting.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @NotNull
    private Double amount;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;
    @NotEmpty
    private String description;
}
