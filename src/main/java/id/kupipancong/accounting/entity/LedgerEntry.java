package id.kupipancong.accounting.entity;

import id.kupipancong.accounting.enums.LedgerType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "ledger_entries",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"transaction_id", "ledger_type", "account_id"})
        }
)
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private LedgerType ledgerType;
    private Date date;
    private Double amount;

    @ManyToOne
    private Account account;
    private String createdBy;
    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    private String updatedBy;
    @UpdateTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
