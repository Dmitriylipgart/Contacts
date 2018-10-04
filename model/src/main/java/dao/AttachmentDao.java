package dao;


import entity.Attachment;

import java.util.List;

public interface AttachmentDao {
    void createAttachments(List<Attachment> attachments, long contactId);
    List <Attachment> getAllByContactId(long contactId);
    void update(List<Attachment> attachments, long contactId);
    void delete(List<Long> id);
}
