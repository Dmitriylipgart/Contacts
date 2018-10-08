package com.itechart.model.dao;
import com.itechart.model.ds.MySqlDS;
import com.itechart.model.entity.Attachment;
import com.itechart.model.sql.ContactsSql;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
                attachment.setAttachmentId(rs.getLong("attachment_id"));
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

        String params = String.join(", ", Collections.nCopies(contactIdList.size(), "?"));

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
    public void deleteByAttachmentId(Attachment[] attachments){

        String params = String.join(", ", Collections.nCopies(attachments.length, "?"));

        String sql = ContactsSql.DELETE_ATTACHMENT_BY_ATTACHMENT_ID + "(" + params.toString() + ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < attachments.length; i++) {
                statement.setLong(i + 1, attachments[i].getAttachmentId());
            }
            statement.executeUpdate();
        } catch (Exception e) {
            //todo throw
            e.printStackTrace();
        }
    }

}
