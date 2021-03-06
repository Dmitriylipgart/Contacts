package com.itechart.model.dao;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.entity.Contact;

import java.util.List;


public interface ContactDao{
    int countAll();
    long create(Contact contact);
    List<ContactDto> readAll(int page, int size);
    Contact getById(long id);
    void update(Contact contact);
    void delete(List<Long> id);
    List<ContactDto> getContactsById(List<Long> contactIdList);
}