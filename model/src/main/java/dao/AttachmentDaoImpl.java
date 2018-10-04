package dao;


import ds.MySqlDS;
import entity.Attachment;
import org.apache.tomcat.jdbc.pool.DataSource;
import sql.ContactsSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDaoImpl implements AttachmentDao {

    private DataSource dataSource = new MySqlDS().getDataSource();


    @Override
    public void createAttachments(List<Attachment> attachments, long contactId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.CREATE_ATTACHMENT_SQL)
        ) {
            for (Attachment attachment : attachments) {
                statement.setLong(1, contactId);
                statement.setString(2, attachment.getFileName());
                statement.setString(3, attachment.getComment());
                statement.setString(4, attachment.getDate());
                statement.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    attachment_filename, attachment_comment, attachment_date
    @Override
    public List<Attachment> getAllByContactId(long contactId) {

        List<Attachment> attachments = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ContactsSql.READ_ALL_ATTACHMENTS_BY_CONTACT_ID)) {
            statement.setLong(1, contactId);
            rs = statement.executeQuery();
            while (rs.next()) {
                Attachment attachment = new Attachment();
                attachment.setFileName(rs.getString("attachment_filename"));
                attachment.setComment(rs.getString("attachment_comment"));
                attachment.setDate(rs.getString("attachment_date"));
                attachments.add(attachment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                //todo logger
                e.printStackTrace();
            }
        }
        return attachments;
    }

    @Override
    public void update(List<Attachment> attachments, long contactId) {
        List<Long> contacts = new ArrayList<>();
        contacts.add(contactId);
        delete(contacts);
        createAttachments(attachments, contactId);
    }

    @Override
    public void delete(List<Long> contactIdList) {
        StringBuilder params = new StringBuilder();
        //todo e.g. join
        for (int i = 0; i < contactIdList.size() - 1; i++) {
            params.append("?,");
        }
        params.append("?");

        String sql = ContactsSql.DELETE_ATTACHMENT_BY_CONTACT_ID + "(" + params.toString() + ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < contactIdList.size(); i++) {
                statement.setLong(i + 1, contactIdList.get(i));
            }
            statement.executeUpdate();
        } catch (Exception e) {
            //todo throw
            e.printStackTrace();
        }
    }
}
