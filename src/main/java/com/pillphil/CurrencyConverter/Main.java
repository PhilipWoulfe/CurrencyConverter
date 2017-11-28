package com.pillphil.CurrencyConverter;

import com.pillphil.CurrencyConverter.controllers.CurrencyController;
import com.pillphil.CurrencyConverter.models.*;

import com.pillphil.CurrencyConverter.views.CurrencyGui;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Currency[] currencies = readCurrenciesFromFile();

        ExchangeRates[] rates = readExchangeRates(currencies);

        Bank[] banks = readBanksFromFile();

        CurrencyGui gui = new CurrencyGui(currencies, banks, rates, getDefaultCurrency(currencies, "EUR"));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // new CurrencyController(currencies, rates, banks, gui).gui.setRate(rates[0].getRate("EUR"));
                new CurrencyController(currencies, rates, banks, gui);
            }
        });
    }

    private static Currency getDefaultCurrency(Currency[] currencies, String currency) {
        Currency c = null;

        for (int i = 0; i < currencies.length; i++) {

            try {
                if (currencies[i].getCurrencyCode().equals(currency)) {
                    c = currencies[i];
                    break;
                } else
                    throw new Exception("Currency not found!");
            } catch (Exception e) {
                // do something
            }
        }

        return c;
    }

    public static Currency[] readCurrenciesFromFile() {
        Currency[] currencyArr;
        List<Currency> currencyList = new ArrayList<>();

        Iterable<CSVRecord> records = readFromCsv("Currency_CodeToName.csv");

        for (CSVRecord record : records) {
            String code = record.get(0).trim();
            String name = record.get(1).trim();
            String symbol = record.get(2).trim();
            boolean isPrepended = Boolean.parseBoolean(record.get(3).trim());

            Currency c = new Currency(code, name, symbol, isPrepended);
            currencyList.add(c);
        }

        // convert list to array of typo currency
        currencyArr = currencyList.toArray(new Currency[currencyList.size()]);

        return currencyArr;
    }

    public static Bank[] readBanksFromFile() {
        Bank[] bankArr;
        List<Bank> bankList = new ArrayList<>();

        Iterable<CSVRecord> records = readFromCsv("banks.csv");

        for (CSVRecord record : records) {
            String bankName = record.get(0).trim();
            BigDecimal commission = BigDecimal.valueOf(Double.parseDouble(record.get(1).trim()));

            Bank b = new Bank(bankName, commission);
            bankList.add(b);
        }

        // convert list to array of typo currency
        bankArr = bankList.toArray(new Bank[bankList.size()]);

        return bankArr;
    }

//    public static <T> T[] arrayListToTypeArray(ArrayList<T> arrayList) {
//        arrayList.toArray(getArray(arrayList, arrayList.size())[arrayList.size()]);
//    }
//
//    public <E> E[] getArray(Class<E> clazz, int size) {
//        @SuppressWarnings("unchecked")
//        E[] arr = (E[]) Array.newInstance(clazz, size);
//
//        return arr;
//    }

    public static Iterable<CSVRecord> readFromCsv(String filePath) {
        Reader in;
        Iterable<CSVRecord> records = null;

        try {
            in = new FileReader(filePath);
            records = CSVFormat.EXCEL.parse(in);
        } catch (FileNotFoundException e) {
            // File not found
        } catch (IOException e) {
            // File not found
        }

        return records;
    }

    public static ExchangeRates readExchangeRatesFromJson(String str) {

        JSONObject json = new JSONObject();

        try {
            json = JsonReader.getJson("http://api.fixer.io/latest?base=" + str);
        } catch (Exception e) {
            // ?
        }

        ExchangeRates e = new ExchangeRates(json);

        return e;
    }


    public static ExchangeRates[] readExchangeRates(Currency[] currencies) {
        ExchangeRates[] ratesArr;

        List<ExchangeRates> ratesList = new ArrayList<>();

        for (Currency c : currencies) {
            ratesList.add(readExchangeRatesFromJson(c.getCurrencyCode()));
        }

        ratesArr = ratesList.toArray(new ExchangeRates[ratesList.size()]);

        return ratesArr;
    }
}
