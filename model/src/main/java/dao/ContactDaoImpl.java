package dao;

import ds.MySqlDS;
import dto.ContactDto;
import entity.Contact;
import org.apache.tomcat.jdbc.pool.DataSource;
import sql.ContactsSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {


    private DataSource dataSource = new MySqlDS().getDataSource();

    @Override
    public int countAll() {

        int result = 0;
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.COUNT_ALL_SQL)
        ) {
            rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public long create(Contact contact) {
        ResultSet rs;
        long contactId = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.CREATE_CONTACT_SQL, Statement.RETURN_GENERATED_KEYS)
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
            rs = statement.getGeneratedKeys();
            while (rs.next()) {
                contactId = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactId;
    }


    @Override
    public Contact getById(long contactId) {
        Contact contact = new Contact();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_CONTACT_BY_ID)) {
            statement.setLong(1, contactId);
            rs = statement.executeQuery();
            while (rs.next()) {
                contact.setContactId(rs.getLong(1));
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMiddleName(rs.getString("middle_name"));
                contact.setBirthDate(rs.getString("birth_date"));
                contact.setSex(rs.getString("sex"));
                contact.setCitizenship(rs.getString("citizenship"));
                contact.setFamilyStatus(rs.getString("family_status"));
                contact.setWebSite(rs.getString("web_site"));
                contact.setEmail(rs.getString("email"));
                contact.setJob(rs.getString("job"));
                contact.setCountry(rs.getString("country"));
                contact.setCity(rs.getString("city"));
                contact.setAddress(rs.getString("address"));
                contact.setZipCode(rs.getInt("zip_code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contact;
    }

    @Override
    public void update(Contact contact) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.UPDATE_CONTACT_SQL)
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
            statement.setLong(15, contact.getContactId());
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(List<Long> contactIdList) {

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < contactIdList.size() - 1; i++) {
            params.append("?,");
        }
        params.append("?");

        String sql = ContactsSql.DELETE_CONTACT_BY_ID + "(" + params.toString() + ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < contactIdList.size(); i++) {
                statement.setLong(i + 1, contactIdList.get(i));
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ContactDto> readAll(int page, int size) {

        List<ContactDto> contacts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_ALL_CONTACTS)) {
            statement.setInt(1, (page - 1) * size);
            statement.setInt(2, size);
            rs = statement.executeQuery();
            while (rs.next()) {
                ContactDto contact = new ContactDto();
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
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }
}
