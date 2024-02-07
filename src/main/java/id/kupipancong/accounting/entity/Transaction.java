package id.kupipancong.accounting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime transactionTime;
    private String description;
    private String createdBy;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime updatedAt;
    private String updatedBy;
}
