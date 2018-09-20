package dao;


import entity.Contact;
import entity.Entity;

public interface ContactDao extends EntityDao<Contact>{
    int countAll();
}
