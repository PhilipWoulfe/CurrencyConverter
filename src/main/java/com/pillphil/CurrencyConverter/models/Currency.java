package com.pillphil.CurrencyConverter.models;

import com.sun.javafx.binding.StringFormatter;
import org.jsoup.helper.Validate;

public class Currency implements Comparable {
    private String currencyCode;
    private String currencyFullName;
    private String currencySymbol;
    private boolean isPrepended;

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
