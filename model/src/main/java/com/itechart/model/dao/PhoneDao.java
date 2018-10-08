package com.itechart.model.dao;

import com.itechart.model.entity.Phone;

import java.util.List;

public interface PhoneDao {

    void createPhones(List<Phone> phones, long contactId);
    List <Phone> getAllByContactId(long contactId);
    void update(List<Phone> phones, long contactId);
    void delete(List<Long> id);

}
