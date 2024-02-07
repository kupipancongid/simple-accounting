package id.kupipancong.accounting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String code;
    private String description;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime updatedAt;
    private String updatedBy;
}
