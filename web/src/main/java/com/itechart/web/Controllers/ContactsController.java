package com.itechart.web.Controllers;


import dao.DatabaseService;
import entity.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    DatabaseService ds = new DatabaseService();

    @GetMapping("/count")
    public int getRecordsCount(){
        return ds.getRecordsCount();
    }

    @GetMapping(params = {"page", "size" })
    public List<Contact> getContacts(@RequestParam( "page" ) int page,
                                     @RequestParam( "size" ) int size){

        return ds.getContacts(page, size);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestBody List<Integer> contactIdList){
        ds.deleteContacts(contactIdList);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactsPost(@RequestBody List<Integer> contactIdList){
        ds.deleteContacts(contactIdList);
    }

}
