package com.pillphil.CurrencyConverter.models;

/**
 * <h1>Currency</h1>
 * <p>/p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */

import org.jsoup.helper.Validate;

public class Currency implements Comparable {
    private String currencyCode;
    private String currencyFullName;
    private String currencySymbol;
    private boolean isPrepended;

    /**
     * Constructor for Currency
     * @param currencyCode currency code
     * @param currencyFullName currency name
     * @param currencySymbol currency symbol
     * @param isPrepended currency is added to start of currency string when displaying
     */
    public Currency(String currencyCode, String currencyFullName, String currencySymbol, boolean isPrepended) {
        Validate.notNull(currencyCode, "Currency Code can't be null");
        Validate.notNull(currencyFullName, "Currency Full Name can't be null");
        Validate.notNull(currencySymbol, "Currency Symbol can't be null");

        this.currencyCode = currencyCode;
        this.currencyFullName = currencyFullName;
        this.currencySymbol = currencySymbol;
        this.isPrepended = isPrepended;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getCurrencyFullName() {
        return this.currencyFullName;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public boolean isPrepended() {
        return isPrepended;
    }

    @Override
    public String toString() {
        return currencyFullName;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
