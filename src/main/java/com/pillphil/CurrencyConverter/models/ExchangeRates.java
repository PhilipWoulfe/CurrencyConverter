package com.pillphil.CurrencyConverter.models;

import javafx.util.converter.LocalDateStringConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.helper.Validate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class ExchangeRates {
    private String baseCurrency;
    private LocalDate date;
    private HashMap<String, BigDecimal> rates;

//    public ExchangeRates(String baseCurrency, Date date, HashMap<String, BigDecimal> rates) {
//        Validate.notNull(baseCurrency, "Base Currency can't be null");
//        Validate.notNull(date, "Date can't be null");
//        Validate.notNull(rates, "Rates can't be null");
//
//
//    }

    public ExchangeRates (JSONObject json) {
        Validate.notNull(json, "Json can't be null");
        Validate.notNull(json.get("base"), "Json must contain base key");
        Validate.notNull(json.get("date"), "Json must contain date key");
        Validate.notNull(json.get("rates"), "Json must contain rates key");


        // TODO make JSON Reader JSON manipulation
        // TODO Move string to JSON to there
        // TODO move JSON to HashMap there
        JSONParser parser = new JSONParser();
        JSONObject object = new JSONObject();
        try {
            object = (JSONObject) parser.parse(json.get("rates").toString());
        } catch (Exception e) {
            // TODO Exception Handling
        }

        Object[] keys = object.keySet().toArray();




        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        for (int i = 0; i < keys.length; i++) {
            map.put((String)keys[i], BigDecimal.valueOf((double)object.get((String)keys[i])));
        }
        // TODO Fix this shit
        Local
        LocalDateStringConverter l = new LocalDateStringConverter(json.get("date").toString());
        this.baseCurrency = json.get("base").toString();
        this.date = LocalDate.parse(json.get("date"));
        this.rates = map;

    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getRate(String targetCurrency) {
        return rates.get(targetCurrency);
    }
}
