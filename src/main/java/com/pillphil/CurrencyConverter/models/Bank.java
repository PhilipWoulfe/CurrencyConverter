package com.pillphil.CurrencyConverter.models;

import org.jsoup.helper.Validate;

import java.math.BigDecimal;

public class Bank implements Comparable {
    private String bankName;
    private BigDecimal commission;

    public Bank(String bankName, BigDecimal commission) {
        Validate.notNull(bankName, "Bank Name can't be null");
        Validate.notNull(commission, "Commission can't be null");

        this.bankName = bankName;
        this.commission = commission;
    }

    public String getBankName() {
        return bankName;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    @Override
    public String toString() {
        return bankName;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
