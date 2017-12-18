package com.pillphil.CurrencyConverter.views;

/**
 * <h1>ICurrency</h1>
 * <p>Interface for Currency GUIs</p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */

import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.Currency;

import java.math.BigDecimal;

public interface ICurrency {

    Currency getBaseCurrency();

    Currency getTargetCurrency();

    boolean useCustomRate();

    BigDecimal getCustomRate();

    void setRate(BigDecimal rateDouble);

    Bank getBank();

    void setCommission(BigDecimal commissionDouble);

    BigDecimal getBaseAmount();

    void setTargetAmount(BigDecimal targetAmount);

    void reset();
}
