package dao;


import ds.MySqlDS;
import entity.Phone;
import org.apache.tomcat.jdbc.pool.DataSource;
import sql.ContactsSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhoneDaoImpl implements PhoneDao {

    DataSource dataSource = new MySqlDS().getDataSource();

    @Override
    public void createPhones(List<Phone> phones, long contactId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.CREATE_PHONE_SQL)
        ) {
            for (Phone phone : phones) {
                statement.setLong(1, contactId);
                statement.setString(2, phone.getPhoneNumber());
                statement.setString(3, phone.getPhoneDescription());
                statement.setString(4, phone.getPhoneComment());
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Phone> getAllByContactId(long contactId) {
        List<Phone> phones = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_ALL_PHONES_BY_CONTACT_ID)) {
            statement.setLong(1, contactId);
            rs = statement.executeQuery();
            while (rs.next()) {
                Phone phone = new Phone();
                phone.setPhoneNumber(rs.getString("phone_number"));
                phone.setPhoneDescription(rs.getString("phone_description"));
                phone.setPhoneComment(rs.getString("phone_comment"));
                phones.add(phone);
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
        return phones;
    }

    @Override
    public void update(List<Phone> phones, long contactId) {
        List<Long> contacts = new ArrayList<>();
        contacts.add(contactId);
        delete(contacts);
        createPhones(phones, contactId);
    }

    @Override
    public void delete(List<Long> contactIdList) {

        String params = String.join(", ", Collections.nCopies(contactIdList.size(), "?"));

        String sql = ContactsSql.DELETE_PHONE_BY_CONTACT_ID + "(" + params.toString() + ")";

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
}
