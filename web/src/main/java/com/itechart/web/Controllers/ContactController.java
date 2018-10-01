package com.itechart.web.Controllers;

import entity.Contact;
import javax.servlet.http.Part;

import org.springframework.context.annotation.Bean;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import services.ContactsService;
import sun.text.resources.FormatData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {


    ContactsService service = new ContactsService();

    @PostMapping
    public void saveFiles(@RequestPart(value = "files") MultipartFile[] files,
                          @RequestParam(value = "contact") String contact) throws IOException {
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
