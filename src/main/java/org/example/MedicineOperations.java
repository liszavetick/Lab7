package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class MedicineOperations extends JFrame {
    private boolean isRunning = true;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        MedicineOperations window = new MedicineOperations();
        window.run();
    }

    public void run() {
        setVisible(true);
        while (isRunning) {

        }
    }

    public MedicineOperations() {
        setTitle("Medicine Operations Window");
        setSize(300, 250);
        setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Medicine");
        JButton editButton = new JButton("Edit Medicine");
        JButton deleteButton = new JButton("Delete Medicine");
        JButton selectButton = new JButton("View Medicines");
        JButton searchNameButton = new JButton("Search by Medicine Name");
        JButton searchDiseaseButton = new JButton("Search by Disease");
        JButton exitButton = new JButton("Exit");

        addButton.addActionListener(add);
        editButton.addActionListener(change);
        deleteButton.addActionListener(delete);
        selectButton.addActionListener(select);
        searchNameButton.addActionListener(searchName);
        searchDiseaseButton.addActionListener(searchDisease);
        exitButton.addActionListener(exit);

        add(addButton);
        add(editButton);
        add(deleteButton);
        add(selectButton);
        add(searchNameButton);
        add(searchDiseaseButton);
        add(exitButton);

        Color backgroundColor = new Color(253, 188, 180);
        getContentPane().setBackground(backgroundColor);
    }

    ActionListener add = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            boolean isChange = false;
            try {
                c.addMedicineForm(isChange);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    };

    ActionListener change = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            boolean isChange = true;
            c.deleteMedicineForm(isChange);
        }
    };

    ActionListener delete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            boolean isChange = false;
            c.deleteMedicineForm(isChange);
        }
    };

    ActionListener select = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            c.displayMedicineData(null, null);
        }
    };

    ActionListener searchName = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            c.searchForm(true);
        }
    };

    ActionListener searchDisease = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemWindows c = new SystemWindows();
            c.searchForm(false);
        }
    };

    ActionListener exit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            isRunning = false;
            System.exit(0);
        }
    };
}
