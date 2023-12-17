package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.WindowAdapter;
import javax.swing.*;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import static org.example.MySQLService.selectMedicines;

public class SystemWindows extends JFrame implements ActionListener {

    private JTextField textFieldMedicineName, textFieldPrice, textFieldDisease;
    private JFormattedTextField formattedTextFieldYear, formattedTextFieldExpiryDate;
    private JButton buttonSubmit, buttonDelete, buttonSearch;
    private java.util.List<String> searchParams;
    private java.util.List<String> addParams;
    private java.util.List<String> deleteParams;
    private static JFrame frame;
    private static JTable table;
    private JTextField textFieldSearch;

    public SystemWindows() {
    }

    public void addMedicineForm(boolean isChange) throws ParseException {
        setTitle("Form for Adding Medicine Data");
        setLayout(null);

        textFieldMedicineName = new JTextField();
        formattedTextFieldYear = new JFormattedTextField(createFormatter("####"));
        formattedTextFieldExpiryDate = new JFormattedTextField(createFormatter("####-##-##"));
        textFieldPrice = new JTextField();
        textFieldDisease = new JTextField();

        formattedTextFieldYear.setBounds(320, 100, 60, 25);
        formattedTextFieldExpiryDate.setBounds(450, 100, 100, 25);

        JLabel labelMedicineName = new JLabel("Medicine Name:");
        JLabel labelYearOfManufacture = new JLabel("Year of Manufacture:");
        JLabel labelExpiryDate = new JLabel("Expiry Date:");
        JLabel labelPrice = new JLabel("Price:");
        JLabel labelDisease = new JLabel("Disease:");

        labelMedicineName.setBounds(50, 50, 100, 25);
        textFieldMedicineName.setBounds(160, 50, 200, 25);
        labelYearOfManufacture.setBounds(50, 100, 120, 25);
        formattedTextFieldYear.setBounds(180, 100, 60, 25);
        labelExpiryDate.setBounds(320, 100, 100, 25);
        formattedTextFieldExpiryDate.setBounds(450, 100, 100, 25);
        labelPrice.setBounds(50, 150, 60, 25);
        textFieldPrice.setBounds(160, 150, 100, 25);
        labelDisease.setBounds(320, 150, 60, 25);
        textFieldDisease.setBounds(400, 150, 200, 25);

        add(labelMedicineName);
        add(textFieldMedicineName);
        add(labelYearOfManufacture);
        add(formattedTextFieldYear);
        add(labelExpiryDate);
        add(formattedTextFieldExpiryDate);
        add(labelPrice);
        add(textFieldPrice);
        add(labelDisease);
        add(textFieldDisease);

        buttonSubmit = new JButton("Submit");
        buttonSubmit.setBounds(300, 200, 100, 30);
        add(buttonSubmit);
        if (!isChange) buttonSubmit.addActionListener(this);
        else buttonSubmit.addActionListener(change);

        setSize(700, 300);
        Color backgroundColor = new Color(253, 188, 180);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void deleteMedicineForm(boolean isChange) {
        setTitle("Form for Medicine Operations");
        setLayout(null);

        textFieldMedicineName = new JTextField();
        JLabel labelMedicineName = new JLabel("Medicine Name:");
        labelMedicineName.setBounds(50, 50, 100, 25);
        textFieldMedicineName.setBounds(160, 50, 200, 25);

        add(labelMedicineName);
        add(textFieldMedicineName);

        buttonDelete = new JButton("Search");
        buttonDelete.setBounds(300, 150, 100, 30);
        add(buttonDelete);
        if(!isChange) buttonDelete.addActionListener(delete);
        else buttonDelete.addActionListener(check);

        setSize(700, 200);
        Color backgroundColor = new Color(253, 188, 180);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void searchForm(boolean isName) {
        setTitle("Search Form");
        setLayout(null);

        textFieldSearch = new JTextField();
        JLabel labelSearch = new JLabel("Search Keyword:");
        labelSearch.setBounds(50, 50, 110, 25);
        textFieldSearch.setBounds(170, 50, 100, 25);
        add(labelSearch);
        add(textFieldSearch);

        buttonSearch = new JButton("Search");
        buttonSearch.setBounds(100, 100, 100, 30);
        add(buttonSearch);
        if(isName) buttonSearch.addActionListener(searchName);
        else buttonSearch.addActionListener(searchDisease);
        setSize(300, 200);
        Color backgroundColor = new Color(253, 188, 180);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void displayMedicineData(String medicineName, String disease) {
        setTitle("Medicine Data");
        setLayout(new BorderLayout());
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        selectMedicines(table, medicineName, disease);

        add(scrollPane, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color backgroundColor = new Color(253, 188, 180);
        getContentPane().setBackground(backgroundColor);

        setVisible(true);
    }

    public void windowClosing(WindowEvent we) {this.dispose();}
    public void windowActivated(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowOpened(WindowEvent we) {}

    public void actionPerformed(ActionEvent e) {
        try {
            addParams = new ArrayList<>();
            addParams.add(textFieldMedicineName.getText());
            addParams.add(formattedTextFieldYear.getText());
            addParams.add(formattedTextFieldExpiryDate.getText());
            addParams.add(textFieldPrice.getText());
            addParams.add(textFieldDisease.getText());
            System.out.println("Year of Manufacture: " + formattedTextFieldYear.getText());
            System.out.println("Expiry Date: " + formattedTextFieldExpiryDate.getText());
            System.out.println(addParams);
            MySQLService.addMedicine(addParams);
        } catch (Exception ex) {
            Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ActionListener delete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try{
                deleteParams = new ArrayList<>();
                deleteParams.add(textFieldMedicineName.getText());
                MySQLService.deleteMedicine(deleteParams);
            } catch (Exception ex) {
                Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    ActionListener check = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                searchParams = new ArrayList<>();
                searchParams.add(textFieldMedicineName.getText());
                boolean medicineExists = false;
                if (!searchParams.isEmpty()) {
                    medicineExists = MySQLService.checkMedicine(searchParams);
                }
                if (medicineExists) {
                    boolean isChange = true;
                    addMedicineForm(isChange);
                } else {
                    ErrorWindow();
                }
            } catch (Exception ex) {
                Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    ActionListener change = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try{
                addParams = new ArrayList<>();
                addParams.add(textFieldMedicineName.getText());
                addParams.add(formattedTextFieldYear.getText());
                addParams.add(formattedTextFieldExpiryDate.getText());
                addParams.add(textFieldPrice.getText());
                addParams.add(textFieldDisease.getText());
                MySQLService.changeMedicine(addParams);
            } catch (Exception ex) {
                Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    ActionListener searchName = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try{
                String param = textFieldSearch.getText();
                displayMedicineData(param, null);
            } catch (Exception ex) {
                Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    ActionListener searchDisease = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try{
                String param = textFieldSearch.getText();
                displayMedicineData(null, param);
            } catch (Exception ex) {
                Logger.getLogger(SystemWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    public void ErrorWindow() {
        JOptionPane.showMessageDialog(this, "Error: Data not found", "Error", JOptionPane.ERROR_MESSAGE);
    }
    private MaskFormatter createFormatter(String pattern) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }
}

