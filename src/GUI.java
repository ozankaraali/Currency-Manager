import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by ozan on 6/6/17.
 */
public class GUI {
    public GUI() {
        setupUI();
    }

    private JTabbedPane tabbedPane1;
    private JPanel generalPanel;
    private JTextField moneyToConvertTextField;
    private JComboBox conversionFComboBox;
    private JTextField convertedMoneyTextField;
    private JComboBox rateTableComboBox;
    private JComboBox conversionSComboBox;
    private JTable conversionTable;
    private JPanel conversionPanel;

    private void setupUI() {
        ItemListener cBListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    String first =conversionFComboBox.getSelectedItem().toString();
                    String second = conversionSComboBox.getSelectedItem().toString();
                    convertedMoneyTextField.setText(String.valueOf(Math.round((Double.parseDouble(moneyToConvertTextField.getText())*Main.getRate(first,second))*10000.0)/10000.0));
                }
                catch (Exception e1){
                    convertedMoneyTextField.setText("Error!");
                    e1.printStackTrace();
                }
            }
        };
        //GENERAL PANEL
        generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout(0, 0));
        generalPanel.setPreferredSize(new Dimension(300, 300));

        //MAKE IT TABBED
        tabbedPane1 = new JTabbedPane();
        generalPanel.add(tabbedPane1, BorderLayout.CENTER);

        //CONVERSION PANEL
        conversionPanel = new JPanel();
        conversionPanel.setLayout(new GridBagLayout());
        tabbedPane1.addTab("Convert", conversionPanel);

        //CONVERSION PANEL - TOCONVERT
        moneyToConvertTextField = new JTextField();
        moneyToConvertTextField.setColumns(8);
        moneyToConvertTextField.setText("Your Money");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        conversionPanel.add(moneyToConvertTextField, gbc);

        //CONVERSION PANEL - CURRENCIES
        conversionFComboBox = new JComboBox();
        ArrayList<String> currencies = Main.currencies();
        DefaultComboBoxModel<String> comboBoxModelF = new DefaultComboBoxModel<String>(currencies.toArray(new String[currencies.size()]));
        conversionFComboBox.setModel(comboBoxModelF);
        conversionFComboBox.setPreferredSize(new Dimension(70, 27));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        conversionPanel.add(conversionFComboBox, gbc);
        conversionFComboBox.addItemListener(cBListener);

        convertedMoneyTextField = new JTextField();
        convertedMoneyTextField.setColumns(8);
        convertedMoneyTextField.setEditable(false);
        convertedMoneyTextField.setText("Converted");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        conversionPanel.add(convertedMoneyTextField, gbc);

        conversionSComboBox = new JComboBox();
        conversionSComboBox.setPreferredSize(new Dimension(70, 27));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        DefaultComboBoxModel<String> comboBoxModelS = new DefaultComboBoxModel<String>(currencies.toArray(new String[currencies.size()]));
        conversionSComboBox.setModel(comboBoxModelS);
        conversionPanel.add(conversionSComboBox, gbc);
        conversionSComboBox.addItemListener(cBListener);

        final JPanel ratesPanel = new JPanel();
        ratesPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Rates", ratesPanel);

        //TODO00.01
        ItemListener rBListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                /*try {
                    String first =conversionFComboBox.getSelectedItem().toString();
                    String second = conversionSComboBox.getSelectedItem().toString();
                    convertedMoneyTextField.setText(String.valueOf(Math.round((Double.parseDouble(moneyToConvertTextField.getText())*Main.getRate(first,second))*10000.0)/10000.0));
                }
                catch (Exception e1){
                    convertedMoneyTextField.setText("Error!");
                    e1.printStackTrace();
                }*/
            }
        };

        final JPanel rateTopBarPanel = new JPanel();
        rateTopBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        ratesPanel.add(rateTopBarPanel, BorderLayout.NORTH);
        rateTableComboBox = new JComboBox();
        DefaultComboBoxModel<String> comboBoxModelR = new DefaultComboBoxModel<String>(currencies.toArray(new String[currencies.size()]));
        rateTableComboBox.setModel(comboBoxModelR);
        rateTopBarPanel.add(rateTableComboBox);
        rateTableComboBox.addItemListener(rBListener);


        final JPanel rateCenterTablePanel = new JPanel();
        rateCenterTablePanel.setLayout(new BorderLayout(0, 0));
        ratesPanel.add(rateCenterTablePanel, BorderLayout.CENTER);
        conversionTable = new JTable();
        rateCenterTablePanel.add(conversionTable, BorderLayout.CENTER);

        final JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("About", aboutPanel);

        final JLabel aboutLabel = new JLabel();
        aboutLabel.setHorizontalAlignment(0);
        aboutLabel.setHorizontalTextPosition(0);
        aboutLabel.setText("<html>Currency Manager 1.0.0<br>Ozan KARAALİ 2017 - MIT License<br>API: Fixer.io</html>");
        aboutPanel.add(aboutLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();}

        JFrame frame = new JFrame("Currency Manager");
        frame.setContentPane(new GUI().generalPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}