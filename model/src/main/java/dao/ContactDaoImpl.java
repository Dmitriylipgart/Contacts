package dao;

import ds.MySqlDS;
import dto.ContactDto;
import entity.Contact;
import org.apache.tomcat.jdbc.pool.DataSource;
import sql.ContactsSql;

import java.sql.*;
import java.util.*;

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
            statement.setString(4, contact.getBirthDate().equals("")? "0000-00-00" : contact.getBirthDate());
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
            statement.setString(15, contact.getAvatar());
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
                contact.setAvatar(rs.getString("avatar"));
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
            statement.setString(4, contact.getBirthDate().equals("")? "0000-00-00" : contact.getBirthDate());
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
            statement.setString(15, contact.getAvatar());
            statement.setLong(16, contact.getContactId());
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
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_ALL_CONTACTS_BY_LIMIT)) {
            statement.setInt(1, (page - 1) * size);
            statement.setInt(2, size);
            rs = statement.executeQuery();
            contacts = fillContactDto(rs);
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

    public List<ContactDto> readAll() {

        List<ContactDto> contacts = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_ALL_CONTACTS)) {
            rs = statement.executeQuery();
            contacts = fillContactDto(rs);
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



    public List<ContactDto> readAllByParams(HashMap<String, String> params, int page, int size){
        String sql = "Select * from contacts WHERE ";

        sql = getSqlForParams(sql, params);
        ResultSet rs = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            for(String value: params.values()){
                statement.setString(i++, value);
            }
            statement.setInt(i++, (page - 1) * size);
            statement.setInt(i, size);
            rs = statement.executeQuery();
            return fillContactDto(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public int countAllByParams(HashMap<String, String> params) {
        String sql = "SELECT count(*) FROM contacts WHERE ";
        sql = getSqlForParams(sql, params);
        int result = 0;
        ResultSet rs = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            int i = 1;
            for(String value: params.values()){
                statement.setString(i++, value);
            }
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

    private String getSqlForParams(String sql, HashMap<String, String> params){

        List<String> paramsList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String> pair = iterator.next();

            if(!pair.getValue().equals("")){
                if(pair.getKey().equals("birth_date_start")){
                    paramsList.add("birth_date > ?");
                }else if(pair.getKey().equals("birth_date_end")){
                    paramsList.add("birth_date < ?");
                }else{
                    paramsList.add(pair.getKey() + " = ?");
                }
            }else{
                iterator.remove();
            }
        }

        if(sql.equals("Select * from contacts WHERE ")){
            if(paramsList.size() > 0){
                sql += String.join(" AND ", paramsList) +
                        " AND deleted IS NULL ORDER by contact_id LIMIT ?,? ";
            } else {
                sql += " deleted IS NULL ORDER by contact_id LIMIT ?,? ";
            }
        } else{
            if(paramsList.size() > 0){
                sql += String.join(" AND ", paramsList) +
                        " AND deleted IS NULL";
            } else {
                sql += " deleted IS NULL";
            }
        }

        return sql;
    }

    public List<ContactDto> getContactsById(List<Long> contactIdList){
        List<ContactDto> contacts = new ArrayList<>();
        ResultSet rs = null;

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < contactIdList.size() - 1; i++) {
            params.append("?,");
        }
        params.append("?");

        String sql = ContactsSql.READ_CONTACTS_BY_ID + "(" + params.toString() + ") AND deleted IS NULL"  ;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < contactIdList.size(); i++) {
                statement.setLong(i + 1, contactIdList.get(i));
            }
            rs = statement.executeQuery();
            contacts = fillContactDto(rs);
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


    private List<ContactDto> fillContactDto(ResultSet rs){
        List<ContactDto> contacts = new ArrayList<>();

        try {
            while (rs.next()) {
                ContactDto contact = new ContactDto();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMiddleName(rs.getString("middle_name"));
                contact.setBirthDate(rs.getString("birth_date"));
                contact.setJob(rs.getString("job"));
                contact.setEmail(rs.getString("email"));
                contact.setCountry(rs.getString("country"));
                contact.setCity(rs.getString("city"));
                contact.setAddress(rs.getString("address"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }


}

