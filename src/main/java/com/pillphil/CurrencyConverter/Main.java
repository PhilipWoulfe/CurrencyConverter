package com.pillphil.CurrencyConverter;

import com.pillphil.CurrencyConverter.models.ExchangeRate;
import com.pillphil.CurrencyConverter.models.ExchangeRates;
import com.pillphil.CurrencyConverter.models.JsonReader;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        JSONObject json = new JSONObject();
        try {
            json = JsonReader.getJson("http://api.fixer.io/latest?base=USD");
        }
        catch (Exception e) {
            // ?
        }

        ExchangeRates e = new ExchangeRates(json);
        System.out.println();

    }
}
