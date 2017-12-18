package com.pillphil.CurrencyConverter.controllers;

/**
 * <h1>Currency Controller</h1>
 * <p>Controller for implementation of MVC Model</p>
 * <p>Ok, I'll admit it, I gave up on MVC once I started running out of Time</p>
 * <p>Most of the logic is in the GUI</p>
 * <p>Actually, now that I look at it, I don't think this is doing anything</p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */

import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.Currency;
import com.pillphil.CurrencyConverter.models.ExchangeRates;
import com.pillphil.CurrencyConverter.views.CurrencyGui;
import org.jsoup.helper.Validate;

public class CurrencyController {

    /**
     * Currency Controller Constructor
     *
     * @param currencies Currencies model for currency controller
     * @param rates Rates model for currency controller
     * @param banks Banks model for currency controller
     * @param gui GUI for currency Controller
     */
    public CurrencyController(Currency[] currencies, ExchangeRates[] rates, Bank[] banks, CurrencyGui gui) {
        Validate.notNull(currencies, "Currencies cannot be null");
        Validate.notNull(rates, "Exchange Rates cannot be null");
        Validate.notNull(banks, "Banks cannot be null");
        Validate.notNull(gui, "gui cannot be null");
    }
}
