package com.itechart.web.Controllers;


import dao.DatabaseService;
import entity.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    DatabaseService ds = new DatabaseService();

    @GetMapping()
    public int getRecordsCount(){
        return ds.getRecordsCount();
    }

    @GetMapping(value = "/contacts", params = { "page", "size" })
    public List<Contact> getContacts(@RequestParam( "page" ) int page,
                                     @RequestParam( "size" ) int size){

        return ds.getContacts(page, size);
    }



}
