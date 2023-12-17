package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MySQLService {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "root";
    private static final String password = "panchenko_50";
    private static List<String> searchParams = new ArrayList<>();;
    public static void selectMedicines(JTable table, String medicineName, String disease) {
        String sql = "SELECT * FROM medicine";
        if (medicineName != null) {
            sql += " WHERE Name = ?";
        }
        if (disease != null) {
            if (medicineName != null) {
                sql += " AND";
            } else {
                sql += " WHERE";
            }
            sql += " Disease = ?";
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            if (medicineName != null) {
                statement.setString(1, medicineName);
            }
            if (disease != null) {
                statement.setString(medicineName != null ? 2 : 1, disease);
            }

            ResultSet resultSet = statement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                tableModel.addRow(rowData);
            }
            table.setModel(tableModel);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addMedicine(List<String> params) {
        String sql = "INSERT INTO medicine(Name, Year_of_Manufacture, Expiry_Date, Price, Disease) " +
                "VALUES (?, ?, ?, ?, ?)";
        DecimalFormat priceFormat = new DecimalFormat("0.00");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                Object value = param;

                if (i == 1) {
                    if (!param.isEmpty()) {
                        try {
                            System.out.println("Year string: " + param);
                            value = Integer.parseInt(param);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            value = null;
                        }
                    } else {
                        value = null;
                    }
                } else if (i == 2) {
                    if (!param.isEmpty()) {
                        try {
                            System.out.println("Date string: " + param);
                            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(param);
                            System.out.println("Parsed date: " + utilDate);
                            value = new java.sql.Date(utilDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            value = null;
                        }
                    } else {
                        value = null;
                    }
                } else if (i == 3) {
                    if (!param.isEmpty()) {
                        try {
                            System.out.println("Setting price: " + param);
                            value = priceFormat.parse(param).doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                            value = null;
                        }
                    } else {
                        value = null;
                    }
                }

                System.out.println("Setting value: " + value);
                statement.setObject(i + 1, value);
            }

            int rows = statement.executeUpdate();
            System.out.printf("Added %d rows", rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteMedicine(List<String> params) {
        String sql = "DELETE FROM medicine WHERE Name = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, params.get(0));
            int rows = statement.executeUpdate();
            System.out.printf("Deleted %d rows", rows);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkMedicine(List<String> params) {
        boolean medicineExists = false;
        String sql = "SELECT * FROM medicine WHERE Name = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, params.get(0));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                medicineExists = true;
                for (String element : params) {
                    searchParams.add(element);
                }
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicineExists;
    }

    public static void changeMedicine(List<String> params) {
        int parameterCount = 0;
        String sql = "UPDATE medicine SET Name = ?, Year_of_Manufacture = ?, Expiry_Date = ?, " +
                "Price = ?, Disease = ? WHERE Name = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                Object value = param;
                if (i == 2 || i == 3) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    value = dateFormat.parse(param);
                }
                statement.setObject(i + 1, value);
                parameterCount++;
            }
            statement.setString(parameterCount + 1, searchParams.get(0));
            int rows = statement.executeUpdate();
            System.out.printf("Updated %d rows", rows);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
