package com.itechart.web.Controllers;

import dao.ContactDaoImpl;
import dto.ContactDto;
import org.springframework.web.bind.annotation.*;
import services.ContactsService;
import services.EmailService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    ContactDaoImpl contactDao = new ContactDaoImpl();
    ContactsService service = new ContactsService();
    EmailService emailService = new EmailService();

    @PostMapping
    public List<ContactDto> deleteContacts(@RequestBody List<Long> contactIdList){
        return service.getContactsById(contactIdList);
    }

    @GetMapping(params = {"template"})
    @ResponseBody
    public String getContacts(@RequestParam("template") String templateName) throws IOException {
        return emailService.getTemplateText(templateName);
    }
}
