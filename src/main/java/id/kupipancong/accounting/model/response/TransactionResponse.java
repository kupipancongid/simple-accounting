package id.kupipancong.accounting.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.kupipancong.accounting.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private LocalDateTime time;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String description;
}
