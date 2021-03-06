package com.pillphil.CurrencyConverter.models;

/**
 * <h1>ExchangeRates</h1>
 * <p>/p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */

import org.json.simple.JSONObject;
import org.jsoup.helper.Validate;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.HashMap;

public class ExchangeRates {
    private String baseCurrency;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;

    /**a
     * Constructor for Exchange Rate
     *
     * @param json JSONObject JsonObject for xchange rate
     */
    public ExchangeRates(JSONObject json) {
        Validate.notNull(json, "Json can't be null");
        Validate.notNull(json.get("base"), "Json must contain base key");
        Validate.notNull(json.get("date"), "Json must contain date key");
        Validate.notNull(json.get("rates"), "Json must contain rates key");

        String dateString = json.get("date").toString();
        String ratesString = json.get("rates").toString();
        JSONObject ratesJsonObject = JsonReader.stringToJson(ratesString);
        HashMap<String, BigDecimal> map = JsonReader.jsonToHashMap(ratesJsonObject);

        this.baseCurrency = json.get("base").toString();

        try {
            this.date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format, please use yyyy-MM-dd");
            throw e;
        }

        this.rates = map;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRate(String targetCurrency) {
        return rates.get(targetCurrency);
    }

    @Override
    public String toString() {
        return baseCurrency + " " + date + " " + rates.get("EUR");
    }
}
