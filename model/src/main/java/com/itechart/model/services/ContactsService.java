package com.itechart.model.services;
import com.itechart.model.dao.*;
import com.itechart.model.dto.AttachmentDto;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.entity.Attachment;
import com.itechart.model.entity.Contact;
import com.itechart.model.entity.Phone;
import com.itechart.model.utils.MyFileUtils;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public class ContactsService {

    private ContactDao contactDao = new ContactDaoImpl();
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

    public void updateContact(Contact contact, MultipartFile[] files, MultipartFile avatar, AttachmentDto[] attachmentToDeleteList) {
        contactDao.update(contact);
        phoneDao.update(contact.getPhones(), contact.getContactId());
        attachmentDao.update(contact.getAttachments(), contact.getContactId());
        myFileUtils.deleteFiles(attachmentToDeleteList, contact.getContactId());
        myFileUtils.saveFiles(files, contact.getContactId());
        myFileUtils.saveAvatar(avatar, contact.getContactId());
    }

}
