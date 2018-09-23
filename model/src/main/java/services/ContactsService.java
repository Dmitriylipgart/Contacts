package services;


import dao.ContactDao;
import dao.ContactDaoImpl;
import dao.PhoneDao;
import dao.PhoneDaoImpl;
import entity.Contact;
import entity.Phone;

import java.util.List;

public class ContactsService {

    ContactDao contactDao = new ContactDaoImpl();
    PhoneDao phoneDao = new PhoneDaoImpl();

    public void createContact(Contact contact){

        long contactId = contactDao.create(contact);
        phoneDao.createPhones(contact.getPhones(), contactId);
    }

    public void delete(List<Long> contactIdList){
            contactDao.delete(contactIdList);
            phoneDao.delete(contactIdList);

    }

    public Contact getContactById(Long contactId){
        Contact contact = contactDao.getById(contactId);
        List<Phone> phones = phoneDao.getAllByContactId(contactId);
        contact.setPhones(phones);
        return contact;
    }

}
