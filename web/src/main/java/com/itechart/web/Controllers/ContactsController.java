package com.itechart.web.Controllers;


import dao.ContactDaoImpl;
import entity.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    ContactDaoImpl contactDao = new ContactDaoImpl();


    @GetMapping("/count")
    public int getRecordsCount(){
        return contactDao.countAll();
    }

    @GetMapping(params = {"page", "size" })
    public List<Contact> getContacts(@RequestParam( "page" ) int page,
                                     @RequestParam( "size" ) int size){

        return contactDao.readAll(page, size);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestBody List<Integer> contactIdList){
        contactDao.delete(contactIdList);
    }

}
