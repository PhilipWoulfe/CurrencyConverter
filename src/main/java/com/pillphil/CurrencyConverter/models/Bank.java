package com.pillphil.CurrencyConverter.models;

import org.jsoup.helper.Validate;

public class Bank {
    private String  bankName;
    private double  commission;

    public Bank (String bankName, Double commission) {
        Validate.notNull(bankName, "Bank Name can't be null");
        Validate.notNull(commission, "Commission can't be null");

        this.bankName   = bankName;
        this.commission = commission;
    }

    public String getBankName() {
        return bankName;
    }

    public double getCommission() {
        return commission;
    }

    @Override
    public String toString() {
        return bankName;
    }
}
