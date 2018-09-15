package com.itechart.web.Controllers;



import dao.DatabaseService;
import entity.Contact;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @PostMapping()
    public void saveContact(@RequestBody Contact contact){
        DatabaseService ds = new DatabaseService();
        System.out.println(contact);
    }



}
