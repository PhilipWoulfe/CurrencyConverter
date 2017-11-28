package com.pillphil.CurrencyConverter.views;

/**
 * <h1>Currency GUI</h1>
 * <p>/p>
 *
 * @author  Philip Woulfe
 * @version 1.0
 * @since   2017-11-27
 */

import com.pillphil.CurrencyConverter.models.Bank;
import com.pillphil.CurrencyConverter.models.Currency;

import com.pillphil.CurrencyConverter.models.ExchangeRates;
import org.jsoup.helper.Validate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;

public class CurrencyGui extends JFrame {

    private static final long serialVersionUID = 1L;

    private JCheckBox               chkCustomRate;

    private JComboBox<Currency>     cboBaseCurrency;
    private JComboBox<Currency>     cboTargetCurrency;
    private JComboBox<Bank>         cboBank;

    private JLabel                  lblBaseCurrency;
    private JLabel                  lblTargetCurrency;
    private JLabel                  lblUseCustomRate;
    private JLabel                  lblBaseAmount;
    private JLabel                  lblTargetAmount;
    private JLabel                  lblConversionRate;
    private JLabel                  lblBank;
    private JLabel                  lblCommissionRate;

    private JFormattedTextField     txtBaseAmount;
    private JFormattedTextField     txtTargetAmount;
    private JFormattedTextField     txtConversionRate;
    private JFormattedTextField     txtCommissionRate;

    private JButton                 btnConvert;
    private JButton                 btnReset;
    private JButton                 btnClose;
    private JButton                 btnPrint;

    private Currency[]              currencies;
    private String                  defaultCurrency;
    private Bank[]                  banks;

    /**
     * Constructor for Currency Screen
     * @param currencies set currency drop downs based on currency array list
     * @param banks set banks drop down based on currency array list
     */
    public CurrencyGui(Currency[] currencies, Bank[] banks, String defaultCurrency) {
        Validate.notNull(currencies, "Currency Array can't be null");
        Validate.notNull(banks, "Bank Array can't be null");
        Validate.notNull(defaultCurrency, "Default Currency can't be null");

        this.currencies = currencies;
        this.defaultCurrency = defaultCurrency;
        this.banks = banks;

        add(getMainPanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);
        setVisible(true);
        pack();

    }
    // TODO Move elsewhere - maybe pass euro object to constructor?
    private Currency getDefaultCurrency(Currency[] currencies, String currency) {
        Currency c = null;
        for (int i = 0; i < currencies.length; i++) {

            try {
                if (currencies[i].getCurrencyCode().equals("EUR")) {
                    c = currencies[i];
                    break;
                } else
                    throw new Exception("Euro not found in currencies!");
            } catch (Exception e) {
                // do something
            }

        }

        return c;
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
        cboBaseCurrency.setModel(new DefaultComboBoxModel<>(currencies));

        lblTargetCurrency = new JLabel("Conversion Currency");
        lblTargetCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        cboTargetCurrency = new JComboBox<>();
        cboTargetCurrency.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
        cboTargetCurrency.setModel(new DefaultComboBoxModel<>(currencies));

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

        lblConversionRate = new JLabel("Conversion Rate");
        lblConversionRate.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        txtConversionRate = new JFormattedTextField(createFormatter("#####"));

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
        cboBank.setModel(new DefaultComboBoxModel<>(banks));

        lblCommissionRate = new JLabel("Commission");
        lblCommissionRate.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));

        txtCommissionRate = new JFormattedTextField(createFormatter("#####"));
        txtCommissionRate.setEditable(false);

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

        txtBaseAmount = new JFormattedTextField(createFormatter("#####"));
        txtBaseAmount.setColumns(10);

        txtTargetAmount = new JFormattedTextField(createFormatter("#####"));
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

        btnConvert  = new JButton("Convert");
        btnReset    = new JButton("Convert");
        btnPrint    = new JButton("Print");
        btnClose    = new JButton("Close");

        // right align
        pane.add(Box.createHorizontalGlue());
        pane.add(btnConvert);
        pane.add(createSpacing(10, 0)); // space between buttons
        pane.add(btnReset);
        pane.add(createSpacing(10, 0));
        pane.add(btnPrint);
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

    // TODO Move to controller
//    public void reset() {
//        cboBaseCurrency.setSelectedItem(getDefaultCurrency(currencies, defaultCurrency));
//        cboTargetCurrency.setSelectedItem(-1);
//        cboTargetCurrency.setEditable(true);
//
//        chkCustomRate.setSelected(false);
//        Currency baseCurrency = (Currency) cboBaseCurrency.getSelectedItem();
//        ExchangeRates
//        txtConversionRate.setText()
//
//    }

    public JComboBox getSelectedBaseCurrency() {
        return cboBaseCurrency;
    }

    public JComboBox getSelectedTargetCurrency() {
        return cboTargetCurrency;
    }

    public JFormattedTextField
//    // RDB One Way
//    /**
//     * get one way selected
//     * @return if one way is selected
//     */
//    public boolean getRdbtnOneWaySelected() {
//        return rdbtnOneWay.isSelected();
//    }
}
