package com.itechart.web.Controllers;



import dao.ContactDaoImpl;
import entity.Contact;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    ContactDaoImpl contactDao = new ContactDaoImpl();

    @PostMapping()
    public void saveContact(@RequestBody Contact contact){
        contactDao.create(contact);
    }



}
