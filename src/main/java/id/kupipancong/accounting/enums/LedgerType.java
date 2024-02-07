package id.kupipancong.accounting.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LedgerType {
    D("Debit"),
    C("Credit");
    private final String descriptiion;
}
