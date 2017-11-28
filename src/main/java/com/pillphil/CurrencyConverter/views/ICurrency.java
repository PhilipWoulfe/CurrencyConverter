package com.pillphil.CurrencyConverter.views;

import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.Currency;

import javax.swing.*;
import java.awt.event.ActionListener;
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

    //    ActionListener stateChanged();
//
//    ActionListener getConvertAction();
    void reset();
//    ActionListener getPrintAction();
//    ActionListener getExitAction();

}
