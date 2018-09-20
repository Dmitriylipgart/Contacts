package dao;
import ds.MySqlDS;
import entity.Contact;
import entity.Entity;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao{


    DataSource dataSource = new MySqlDS().getDataSource();

    @Override
    public int countAll() {
        String sql = "SELECT count(*) FROM contacts WHERE deleted IS NULL";
        int result = 0;
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
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

    @Override
    public void create(Contact contact) {

        String sql = "INSERT INTO contacts (first_name, last_name, middle_name, birth_date, sex, citizenship, family_status, "
                + "web_site, email, job, country, city, address, zip_code )"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
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


    @Override
    public Contact getById(int id) {
        return null;
    }

    @Override
    public void update(Contact contact) {

    }


    @Override
    public void delete(List<Integer> contactIdList) {

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < contactIdList.size() - 1; i++) {
            params.append("?,");
        }
        params.append("?");

        String sql = "UPDATE contacts SET deleted = 1 WHERE contact_id IN (" + params.toString() +")";


        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            for (int i = 0; i < contactIdList.size(); i++) {
                statement.setInt(i + 1, contactIdList.get(i));
            }
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> readAll(int page, int size) {
        String sql ="SELECT * FROM contacts WHERE deleted IS NULL ORDER BY contact_id LIMIT ?, ? ";
        List <Contact> contacts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
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
}
