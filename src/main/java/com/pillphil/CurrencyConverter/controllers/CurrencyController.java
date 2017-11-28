package com.pillphil.CurrencyConverter.controllers;

import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.Currency;
import com.pillphil.CurrencyConverter.models.ExchangeRates;
import com.pillphil.CurrencyConverter.views.CurrencyGui;
import org.jsoup.helper.Validate;

public class CurrencyController {

    private Currency[] currencies;
    private ExchangeRates[] rates;
    private Bank[] banks;
    public CurrencyGui gui;

    public CurrencyController(Currency[] currencies, ExchangeRates[] rates, Bank[] banks, CurrencyGui gui) {
        Validate.notNull(currencies, "Currencies cannot be null");
        Validate.notNull(rates, "Exchange Rates cannot be null");
        Validate.notNull(banks, "Banks cannot be null");
        Validate.notNull(gui, "gui cannot be null");

        this.currencies = currencies;
        this.rates = rates;
        this.banks = banks;
        this.gui = gui;


    }

    private String[] currencyNamesFromCurrency() {
        String[] currencyNames = new String[currencies.length];

        for (int i = 0; i < currencies.length; i++) {
            currencyNames[i] = currencies[i].getCurrencyFullName();
        }

        return currencyNames;
    }

    private Currency currencyFromCurrencyName(String name) {
        Currency result = null;

        for (Currency c : currencies) {
            if (c.getCurrencyFullName().equals(name)) {
                result = c;
                break;
            }
        }

        return result;
    }

    private String[] bankNamesFromBanks() {
        String[] bankNames = new String[banks.length];

        for (int i = 0; i < banks.length; i++) {
            bankNames[i] = banks[i].getBankName();
        }

        return bankNames;
    }

    private Bank bankFromBankName(String name) {
        Bank result = null;

        for (Bank b : banks) {
            if (b.getBankName().equals(name)) {
                result = b;
                break;
            }
        }

        return result;
    }

    // TODO Move elsewhere - maybe pass euro object to constructor?
    private String getDefaultCurrencyName(Currency[] currencies, String currency) {
        String s = null;
        for (int i = 0; i < currencies.length; i++) {

            try {
                if (currencies[i].getCurrencyCode().equals(currency)) {
                    s = currencies[i].getCurrencyFullName();
                    break;
                } else
                    throw new Exception("Currency not found!");
            } catch (Exception e) {
                // do something
            }

        }

        return s;
    }
}
