package com.itechart.web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Contact;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.ContactsService;
import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;


@RestController
@RequestMapping("/contact")
@MultipartConfig
public class ContactController {

    ContactsService service = new ContactsService();


    @PostMapping
    public void saveContact(@RequestParam(value = "files", required = false) MultipartFile[] files,
                            @RequestParam(value = "contact") String contactJson,
                            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Contact contact = mapper.readValue(contactJson, Contact.class);
        service.createContact(contact, files, avatar);
    }

    @GetMapping("/{contactId}")
    public Contact getContactById(@PathVariable Long contactId){
        return service.getContactById(contactId);
    }



    @PostMapping("/update")
    public void updateContactById(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                  @RequestParam(value = "contact") String contactJson,
                                  @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Contact contact = mapper.readValue(contactJson, Contact.class);
        service.updateContact(contact, files, avatar);
    }

}
