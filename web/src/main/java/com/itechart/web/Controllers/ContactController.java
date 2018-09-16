package com.itechart.web.Controllers;



import dao.DatabaseService;
import entity.Contact;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    DatabaseService ds = new DatabaseService();

    @PostMapping()
    public void saveContact(@RequestBody Contact contact){
        ds.createContact(contact);
    }

    @GetMapping("/count")
    public int getRecordsCount(){
        return ds.getRecordsCount();
    }



}
