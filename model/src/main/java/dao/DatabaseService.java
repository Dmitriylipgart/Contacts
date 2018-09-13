package dao;


import com.mysql.jdbc.Driver;
import entity.Contact;

import java.sql.*;

public class DatabaseService {

    private final String URL = "jdbc:mysql://localhost:3306/contact_list";
    private final String USER = "root";
    private final String PASSWORD = "root";


    public Connection getConnection() {

//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


    public void createContact(Contact contact){


        String sql = "Insert into contacts (first_name, last_name, middle_name)"
                + " values (?,?,?)";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getMiddleName());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


