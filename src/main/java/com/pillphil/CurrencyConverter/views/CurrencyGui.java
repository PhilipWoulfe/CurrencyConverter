package com.pillphil.CurrencyConverter.views;

/**
 * <h1>Currency GUI</h1>
 * <p>/p>
 *
 * @author Philip Woulfe
 * @version 1.0
 * @since 2017-11-27
 */

import com.pillphil.CurrencyConverter.models.Currency;
import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.ExchangeRate;
import com.pillphil.CurrencyConverter.models.ExchangeRates;
import com.sun.javafx.binding.StringFormatter;

import javax.swing.*;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;

public class CurrencyGui extends JFrame implements ICurrency {

    private static final long serialVersionUID = 1L;

    private JCheckBox chkCustomRate;

    private JComboBox<Currency> cboBaseCurrency;
    private JComboBox<Currency> cboTargetCurrency;
    private JComboBox<Bank> cboBank;

    private JLabel lblBaseCurrency;
    private JLabel lblTargetCurrency;
    private JLabel lblUseCustomRate;
    private JLabel lblBaseAmount;
    private JLabel lblTargetAmount;
    private JLabel lblConversionRate;
    private JLabel lblBank;
    private JLabel lblCommissionRate;

    private JTextField txtBaseAmount;
    private JTextField txtTargetAmount;
    private JTextField txtConversionRate;
    private JTextField txtCommissionRate;

    private JButton btnConvert;
    private JButton btnReset;
    private JButton btnClose;
    private JButton btnPrint;

    private Currency defaultCurrency;

    private Currency[] currencies;
    private Bank[] banks;
    private ExchangeRates[] rates;

    /**
     * Constructor for Currency Screen
     */
    public CurrencyGui(Currency[] currencies, Bank[] banks, ExchangeRates[] rates, Currency defaultCurrency) {

        this.currencies = currencies;
        this.banks = banks;
        this.rates = rates;
        this.defaultCurrency = defaultCurrency;

        add(getMainPanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);
        setVisible(true);
        pack();

        reset();
    }

    private JPanel getMainPanel() {
        JPanel pane = new JPanel();
        pane.add(getInputPanel(), BorderLayout.CENTER);
        pane.add(getOutputPanel(), BorderLayout.EAST);

        return pane;
    }

    private JPanel getInputPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        pane.add(getCurrencyPanel());
        pane.add(getConversionPanel());
        pane.add(getBankPanel());

        return pane;
    }

    private JPanel getCurrencyPanel() {
        JPanel pane;

        lblBaseCurrency = new JLabel("Base Currency");
        lblBaseCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        cboBaseCurrency = new JComboBox<>();
        cboBaseCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
        Arrays.sort(currencies);
        cboBaseCurrency.setModel(new DefaultComboBoxModel<>(currencies));
        cboBaseCurrency.setEditable(false);
        cboBaseCurrency.addItemListener(e -> {
            setRate(getRate(getBaseCurrency(), getTargetCurrency()));
            setTargetAmount(calculateTotal());
        });

        lblTargetCurrency = new JLabel("Conversion Currency");
        lblTargetCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        cboTargetCurrency = new JComboBox<>();
        cboTargetCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
        Arrays.sort(currencies);
        cboTargetCurrency.setModel(new DefaultComboBoxModel<>(currencies));
        cboTargetCurrency.setEditable(false);
        cboTargetCurrency.addItemListener(e -> {
            setRate(getRate(getBaseCurrency(), getTargetCurrency()));
            setTargetAmount(calculateTotal());
        });


        pane = getDoublePanel(
                getDoublePanel(lblBaseCurrency, cboBaseCurrency, 5),
                getDoublePanel(lblTargetCurrency, cboTargetCurrency, 5),
                10
        );

        addTitledBorder(pane, "Currency Settings");

        return pane;
    }

    private JPanel getConversionPanel() {
        JPanel pane;

        lblUseCustomRate = new JLabel("Use Custom Rate");
        lblUseCustomRate.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        chkCustomRate = new JCheckBox();
        chkCustomRate.setSelected(false);
        chkCustomRate.addItemListener(e -> {
            lblBaseCurrency.setEnabled(!chkCustomRate.isSelected());
            cboBaseCurrency.setEnabled(!chkCustomRate.isSelected());
            lblTargetCurrency.setEnabled(!chkCustomRate.isSelected());
            cboTargetCurrency.setEnabled(!chkCustomRate.isSelected());

            if (chkCustomRate.isSelected())
                txtConversionRate.setText("0");
            else
                txtConversionRate.setText(getRate(getBaseCurrency(), getTargetCurrency()).toString());

            txtConversionRate.setEditable(chkCustomRate.isSelected());
            setTargetAmount(calculateTotal());
        });

        lblConversionRate = new JLabel("Conversion Rate");
        lblConversionRate.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        txtConversionRate = new JTextField();
        txtConversionRate.setEditable(false);
        setRate(getRate(getBaseCurrency(), getTargetCurrency()));

        pane = getDoublePanel(
                getDoublePanel(lblUseCustomRate, chkCustomRate, 5),
                getDoublePanel(lblConversionRate, txtConversionRate, 5),
                10
        );

        addTitledBorder(pane, "Conversion Settings");

        return pane;
    }

    private JPanel getBankPanel() {
        JPanel pane;

        lblBank = new JLabel("Select Bank");
        lblBank.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        cboBank = new JComboBox<>();
        cboBank.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
        Arrays.sort(banks);
        cboBank.setModel(new DefaultComboBoxModel<>(banks));
        cboBank.addItemListener(e -> {
            txtCommissionRate.setText(getBank().getCommission() + "%");
            setTargetAmount(calculateTotal());
        });

        lblCommissionRate = new JLabel("Commission");
        lblCommissionRate.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        txtCommissionRate = new JTextField();
        txtCommissionRate.setEditable(false);
        txtCommissionRate.setText(getBank().getCommission() + "%");

        pane = getDoublePanel(
                getDoublePanel(lblBank, cboBank, 5),
                getDoublePanel(lblCommissionRate, txtCommissionRate, 5),
                10
        );

        addTitledBorder(pane, "Bank Settings");

        return pane;
    }

    private JPanel getDoublePanel(Component a, Component b, int margin) {
        JPanel pane = new JPanel();

        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 50;

        pane.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

        pane.add(a, gbc);

        gbc.gridy = 1;

        gbc.insets = new Insets(2, 2, 2, 2);
        pane.add(b, gbc);

        return pane;
    }

    private JPanel getOutputPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblBaseAmount = new JLabel("Set Base Amount");
        lblBaseAmount.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        txtBaseAmount = new JTextField();
        txtBaseAmount.setColumns(10);
        txtBaseAmount.setText("0");
        txtBaseAmount.addActionListener(e -> setTargetAmount(calculateTotal()));

        txtTargetAmount = new JTextField();
        txtTargetAmount.setColumns(10);
        txtTargetAmount.setEditable(false);

        lblTargetAmount = new JLabel("Conversion");
        lblTargetAmount.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        pane.add(lblBaseAmount);
        pane.add(createSpacing(0, 10));
        pane.add(txtBaseAmount);
        pane.add(createSpacing(0, 20));
        pane.add(lblTargetAmount);
        pane.add(createSpacing(0, 10));
        pane.add(txtTargetAmount);

        return pane;
    }

    private JPanel getButtonPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnConvert = new JButton("Convert");
        btnConvert.addActionListener(e -> setTargetAmount(calculateTotal()));

        btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> reset());

        btnClose = new JButton("Close");
        btnClose.addActionListener(e -> System.exit(0));

        // right align
        pane.add(Box.createHorizontalGlue());
        pane.add(btnConvert);
        pane.add(createSpacing(10, 0)); // space between buttons
        pane.add(btnReset);
        pane.add(createSpacing(10, 0));
        pane.add(btnClose);

        return pane;
    }

    private void addTitledBorder(JPanel pane, String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        pane.setBorder(titledBorder);
    }

    private Component createSpacing(int width, int height) {
        return Box.createRigidArea(new Dimension(width, height));
    }

    //A convenience method for creating a MaskFormatter.
    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    private BigDecimal calculateTotal() {
        if (getBaseAmount() == BigDecimal.valueOf(0))
            return BigDecimal.valueOf(0);

        BigDecimal input = getBaseAmount() != null ? getBaseAmount() : BigDecimal.valueOf(0);
        Bank b = (Bank) cboBank.getSelectedItem();
        BigDecimal totalExcludingComission = input.multiply(
                useCustomRate() ? getCustomRate() : getRate(getBaseCurrency(), getTargetCurrency()));
        BigDecimal commission = (b.getCommission().divide(BigDecimal.valueOf(100))).multiply(totalExcludingComission);
        BigDecimal totalIncludingCommission = totalExcludingComission.subtract(commission);

        return totalIncludingCommission;
    }

    @Override
    public Currency getBaseCurrency() {
        return (Currency) cboBaseCurrency.getSelectedItem();
    }

    @Override
    public Currency getTargetCurrency() {
        return (Currency) cboTargetCurrency.getSelectedItem();
    }


    @Override
    public boolean useCustomRate() {
        return chkCustomRate.isSelected();
    }

    @Override
    public void setRate(BigDecimal rate) {
        txtConversionRate.setText(rate.toString());
    }

    @Override
    public BigDecimal getCustomRate() {
        return BigDecimal.valueOf(Double.parseDouble(txtConversionRate.getText()));
    }

    @Override
    public Bank getBank() {
        return (Bank) cboBank.getSelectedItem();
    }

    @Override
    public void setCommission(BigDecimal commission) {
        txtCommissionRate.setText(commission.toString());
    }

    @Override
    public BigDecimal getBaseAmount() {
//        if(!letterCheckPassed()) {
//            JOptionPane.showMessageDialog(null, "Invalid input!");
//            return BigDecimal.valueOf(0);
//        } else {
        return txtBaseAmount.getText() == null || txtBaseAmount.getText().trim().equals("")
                ? BigDecimal.valueOf(0)
                : BigDecimal.valueOf((Double.parseDouble(txtBaseAmount.getText())));
//        }
    }

    @Override
    public void setTargetAmount(BigDecimal targetAmount) {
        String symbol = getTargetCurrency().getCurrencySymbol();
        boolean isPrepended = getTargetCurrency().isPrepended();
        String outputString;

        if (isPrepended)
            outputString = String.format("%s%.2f", symbol, targetAmount);
        else
            outputString = String.format("%.2f %s", targetAmount, symbol);

        txtTargetAmount.setText(outputString);
    }

    public void reset() {
        cboBaseCurrency.setSelectedItem(defaultCurrency);
        cboTargetCurrency.setSelectedItem(cboTargetCurrency.getItemAt(0));
        cboTargetCurrency.setEditable(false);
        txtBaseAmount.setText("0");
        chkCustomRate.setSelected(false);

        cboBank.setSelectedItem(0);

        txtTargetAmount.setText("");
        txtBaseAmount.setText("");
    }

    public BigDecimal getRate(Currency baseRate, Currency targetRate) {
        BigDecimal result = null;

//        if(!letterCheckPassed()) {
//            JOptionPane.showMessageDialog(null, "Invalid input!");
//            return BigDecimal.valueOf(0);
//        }
//        else {

        if (baseRate == targetRate)
            result = BigDecimal.valueOf(1);
        else
            for (int i = 0; i < rates.length; i++) {
                if (rates[i].getBaseCurrency().equals(baseRate.getCurrencyCode()))
                    result = rates[i].getRate(targetRate.getCurrencyCode());
            }
//        }
        return result;
    }

    private boolean letterCheckPassed() {
        return isNum(txtConversionRate.getText()) && (txtBaseAmount != null && isNum(txtBaseAmount.getText()));
    }

    private boolean isNum(String name) {
        return name != null || name.matches("[1-9]+");
    }

    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }
}