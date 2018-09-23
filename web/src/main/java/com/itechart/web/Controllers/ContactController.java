package com.itechart.web.Controllers;

import entity.Contact;
import org.springframework.web.bind.annotation.*;
import services.ContactsService;

@RestController
@RequestMapping("/contact")
public class ContactController {

    ContactsService service = new ContactsService();

    @PostMapping()
    public void saveContact(@RequestBody Contact contact){
        service.createContact(contact);
    }

    @GetMapping("/{contactId}")
    public Contact getContactById(@PathVariable Long contactId){
        return service.getContactById(contactId);
    }

}
