package dao;


import entity.Phone;

import java.util.List;

public interface PhoneDao {

    void createPhones(List<Phone> phones, long contactId);
    List <Phone> getAllByContactId(long contactId);
    void update(Phone phone);
    void delete(List<Long> id);

}
