package id.kupipancong.accounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.kupipancong.accounting.enums.TransactionType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private LocalDateTime time;
    @Column(columnDefinition = "decimal(20,2)")
    private Double amount;
    private String description;
    private String createdBy;
    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    private String updatedBy;
    @UpdateTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<LedgerEntry> ledgerEntries;
}
