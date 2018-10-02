package com.itechart.web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Contact;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.ContactsService;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/contact")
public class ContactController {


    ContactsService service = new ContactsService();

    @PostMapping
    public void saveFiles(@RequestPart(value = "files") MultipartFile[] files,
                          @RequestParam(value = "contact") String contactJson) throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        Contact contact = mapper.readValue(contactJson, Contact.class);
        service.createContact(contact);


        File uploadDir = new File("D:\\file");
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        for (MultipartFile file: files) {
            file.transferTo(new File(uploadDir + File.separator + file.getOriginalFilename()));
        }
    }

    @GetMapping("/{contactId}")
    public Contact getContactById(@PathVariable Long contactId){
        return service.getContactById(contactId);
    }

    @PutMapping()
    public void updateContactById(@RequestBody Contact contact){
        service.updateContact(contact);
    }

}
