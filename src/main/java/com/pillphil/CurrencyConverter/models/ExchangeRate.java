// TODO Remove this class if not needed
package com.pillphil.CurrencyConverter.models;

import org.jsoup.helper.Validate;

import java.math.BigDecimal;

public class ExchangeRate {
    private String currencyCode;
    private BigDecimal exchangeRate;

    public ExchangeRate (String currencyCode, BigDecimal exchangeRate) {
        Validate.notNull(currencyCode, "Currency Code can't be null");
        Validate.notNull(exchangeRate, "Exchange Rate can't be null");

        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
