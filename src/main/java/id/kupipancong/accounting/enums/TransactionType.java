package id.kupipancong.accounting.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionType {
    IN("Penerimaan"),
    OUT("Pengeluaran");
    private final String descriptiion;
}
