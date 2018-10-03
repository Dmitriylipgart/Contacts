package services;

import dao.*;
import dto.ContactDto;
import entity.Attachment;
import entity.Contact;
import entity.Phone;
import org.springframework.web.multipart.MultipartFile;
import utils.MyFileUtils;

import java.util.List;

public class ContactsService {

    private  ContactDao contactDao = new ContactDaoImpl();
    private PhoneDao phoneDao = new PhoneDaoImpl();
    private AttachmentDao attachmentDao = new AttachmentDaoImpl();
    MyFileUtils myFileUtils = new MyFileUtils();

    public void createContact(Contact contact, MultipartFile[] files, MultipartFile avatar) {

        long contactId = contactDao.create(contact);
        phoneDao.createPhones(contact.getPhones(), contactId);
        attachmentDao.createAttachments(contact.getAttachments(), contactId);
        myFileUtils.saveFiles(files, contactId);
        myFileUtils.saveAvatar(avatar, contactId);

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

    public List<ContactDto> getContactsById(List<Long> contactIdList){
        return contactDao.getContactsById(contactIdList);
    }

    public void updateContact(Contact contact, MultipartFile[] files, MultipartFile avatar) {
        contactDao.update(contact);
        phoneDao.update(contact.getPhones(), contact.getContactId());
        attachmentDao.update(contact.getAttachments(), contact.getContactId());
        myFileUtils.saveFiles(files, contact.getContactId());
        myFileUtils.saveAvatar(avatar, contact.getContactId());
    }

}
