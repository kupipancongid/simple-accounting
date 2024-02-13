package id.kupipancong.accounting.repository;

import id.kupipancong.accounting.entity.LedgerEntry;
import id.kupipancong.accounting.enums.LedgerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, String>, JpaSpecificationExecutor<LedgerEntry> {
    Boolean existsByAccount_CodeAndLedgerTypeAndTransaction_Id(String accountCode, LedgerType ledgerType, String transactionId);
}
