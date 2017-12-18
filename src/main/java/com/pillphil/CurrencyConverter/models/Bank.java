package com.pillphil.CurrencyConverter.models;

/**
 * <h1>Bank</h1>
 * <p>Bank Model</p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */


import org.jsoup.helper.Validate;

import java.math.BigDecimal;

public class Bank implements Comparable {
    private String bankName;
    private BigDecimal commission;

    /**
     * Bank Constructor
     *
     * @param bankName name of bank object:
     * @param commission rate of comission bank charges for currency exchange
     */
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
