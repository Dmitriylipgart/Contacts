package dao;

import entity.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DatabaseService {

    private final String URL = "jdbc:mysql://localhost:3306/contact_list?useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "root";

    public void createContact(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, middle_name, birth_date, sex, citizenship, family_status, "
                + "web_site, email, job, country, city, address, zip_code )"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getMiddleName());
            statement.setString(4, contact.getBirthDate());
            statement.setString(5, contact.getSex());
            statement.setString(6, contact.getCitizenship());
            statement.setString(7, contact.getFamilyStatus());
            statement.setString(8, contact.getWebSite());
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getJob());
            statement.setString(11, contact.getCountry());
            statement.setString(12, contact.getCity());
            statement.setString(13, contact.getAddress());
            statement.setInt(14, contact.getZipCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRecordsCount() {
        String sql = "SELECT count(*) FROM contacts WHERE deleted IS NULL";
        int result = 0;
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public List<Contact> getContacts(int page, int size){
        String sql ="SELECT * FROM contacts WHERE deleted IS NULL ORDER BY contact_id LIMIT ?, ? ";
        List <Contact> contacts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, (page - 1) * size);
            statement.setInt(2, size);
            rs = statement.executeQuery();
            while (rs.next()){
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMiddleName(rs.getString("middle_name"));
                contact.setBirthDate(rs.getString("birth_date"));
                contact.setJob(rs.getString("job"));
                contact.setCountry(rs.getString("country"));
                contact.setCity(rs.getString("city"));
                contact.setAddress(rs.getString("address"));
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void deleteContacts(List<Integer> contactIdList){

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < contactIdList.size() - 1; i++) {
            params.append("?,");
            }
         params.append("?");



        String sql = "UPDATE contacts SET deleted = 1 WHERE contact_id IN (" + params.toString() +")";


        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql)){
            for (int i = 0; i < contactIdList.size(); i++) {
                statement.setInt(i + 1, contactIdList.get(i));
            }
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}


