package com.itechart.web.Controllers;


import dao.ContactDaoImpl;
import dto.ContactDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.ContactsService;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    ContactDaoImpl contactDao = new ContactDaoImpl();
    ContactsService service = new ContactsService();


    @GetMapping("/count")
    public int getRecordsCount(){
        return contactDao.countAll();
    }

    @GetMapping(params = {"page", "size" })
    public List<ContactDto> getContacts(@RequestParam( "page" ) int page,
                                        @RequestParam( "size" ) int size){

        return contactDao.readAll(page, size);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestBody List<Long> contactIdList){
        service.delete(contactIdList);
    }

}
