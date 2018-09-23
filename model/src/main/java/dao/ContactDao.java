package dao;


import dto.ContactDto;
import entity.Contact;

import java.util.List;


public interface ContactDao{
    int countAll();
    long create(Contact contact);
    List<ContactDto> readAll(int page, int size);
    Contact getById(long id);
    void update(Contact contact);
    void delete(List<Long> id);

}
