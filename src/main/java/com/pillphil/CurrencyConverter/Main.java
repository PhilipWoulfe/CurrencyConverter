package com.pillphil.CurrencyConverter;

/**
 * <h1>Main</h1>
 * <p>Main class - this is where everything runs from/p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */


import com.pillphil.CurrencyConverter.controllers.CurrencyController;
import com.pillphil.CurrencyConverter.models.*;

import com.pillphil.CurrencyConverter.views.CurrencyGui;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Currency[] currencies = readCurrenciesFromFile();

        ExchangeRates[] rates = readExchangeRates(currencies);

        Bank[] banks = readBanksFromFile();

        CurrencyGui gui = new CurrencyGui(currencies, banks, rates, getDefaultCurrency(currencies, "EUR"));

        // I don't think this is doing anything...
        SwingUtilities.invokeLater(() -> new CurrencyController(currencies, rates, banks, gui));
    }

    /**
     * Get default currency object based in input string
     * @param currencies - Currency array
     * @param currency - String code of desired currency object
     *
     * @return returns currency object with code of input string
     */
    private static Currency getDefaultCurrency(Currency[] currencies, String currency) {
        Currency c = null;

        for (Currency currency1 : currencies) {

            try {
                if (currency1.getCurrencyCode().equals(currency)) {
                    c = currency1;
                    break;
                } else
                    throw new Exception("Currency not found!");
            } catch (Exception e) {
                System.out.println( currency + " Currency Not Found - Check internet connection");
            }
        }

        return c;
    }

    /**
     * read currencies from file and return currency array
     *
     * @return returns currency array populated from file
     */
    private static Currency[] readCurrenciesFromFile() {
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

    /**
     * read banks from file and return bank array
     *
     * @return returns bank array populated from file
     */
    private static Bank[] readBanksFromFile() {
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

    /**
     * read from file and return CSVRecord object
     * @param filePath String of file location
     *
     * @return CSVRecord -  returns csv file and return
     */
    private static Iterable<CSVRecord> readFromCsv(String filePath) {
        Reader in;
        Iterable<CSVRecord> records = null;

        try {
            in = new FileReader(filePath);
            records = CSVFormat.EXCEL.parse(in);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found!");
        }

        return records;
    }

    /**
     * read currencies from file and return currency array
     * @param str currency code for desired currency
     * @return returns ExchangeRates Object populated from fixer.io api
     */
    private static ExchangeRates readExchangeRatesFromJson(String str) {

        JSONObject json = new JSONObject();

        try {
            json = JsonReader.getJson("http://api.fixer.io/latest?base=" + str);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot connect to API - Check internet connection");
        }

        return new ExchangeRates(json);
    }

    /**
     * Create Exchange Rates array populated with exchange rates from input
     * @param currencies Currency array
     * @return returns exchange rate array populated from currency
     */
    private static ExchangeRates[] readExchangeRates(Currency[] currencies) {
        ExchangeRates[] ratesArr;

        List<ExchangeRates> ratesList = new ArrayList<>();

        for (Currency c : currencies) {
            ratesList.add(readExchangeRatesFromJson(c.getCurrencyCode()));
        }

        ratesArr = ratesList.toArray(new ExchangeRates[ratesList.size()]);

        return ratesArr;
    }
}
