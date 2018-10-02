package services;

import dao.*;
import entity.Attachment;
import entity.Contact;
import entity.Phone;

import java.util.List;

public class ContactsService {

    private  ContactDao contactDao = new ContactDaoImpl();
    private PhoneDao phoneDao = new PhoneDaoImpl();
    private AttachmentDao attachmentDao = new AttachmentDaoImpl();

    public void createContact(Contact contact) {

        long contactId = contactDao.create(contact);
        phoneDao.createPhones(contact.getPhones(), contactId);
        attachmentDao.createAttachments(contact.getAttachments(), contactId);
    }

    public void delete(List<Long> contactIdList) {
        contactDao.delete(contactIdList);
        phoneDao.delete(contactIdList);
        attachmentDao.delete(contactIdList);
    }

    public Contact getContactById(Long contactId) {
        Contact contact = contactDao.getById(contactId);
        List<Phone> phones = phoneDao.getAllByContactId(contactId);
        List<Attachment> attachments = attachmentDao.getAllByContactId(contactId);
        contact.setPhones(phones);
        contact.setAttachments(attachments);
        return contact;
    }

    public void updateContact(Contact contact) {
        contactDao.update(contact);
        phoneDao.update(contact.getPhones(), contact.getContactId());
        attachmentDao.update(contact.getAttachments(), contact.getContactId());
    }

}
