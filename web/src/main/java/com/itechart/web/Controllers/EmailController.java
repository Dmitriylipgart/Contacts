package com.itechart.web.Controllers;
import com.itechart.model.dao.ContactDaoImpl;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.dto.EmailDto;
import com.itechart.model.services.ContactsService;
import com.itechart.model.services.EmailService;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    ContactDaoImpl contactDao = new ContactDaoImpl();
    ContactsService service = new ContactsService();
    EmailService emailService = new EmailService();

    @PostMapping("/address")
    public List<ContactDto> deleteContacts(@RequestBody List<Long> contactIdList){
        return service.getContactsById(contactIdList);
    }


    @GetMapping(params = {"template"})
    @ResponseBody
    public String getContacts(@RequestParam("template") String templateName) throws IOException {
        return emailService.getTemplateText(templateName);
    }

    @PostMapping
    public void sendEmail(@RequestBody EmailDto email){
        emailService.send(email);
    }

}
