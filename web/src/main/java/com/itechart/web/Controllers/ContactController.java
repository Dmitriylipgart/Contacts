package com.itechart.web.Controllers;



import dao.DatabaseService;
import entity.Contact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @RequestMapping
    public ModelAndView getContactPage(){
        return new ModelAndView("/html/contactPage.html");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView saveContact(HttpServletRequest req){

        Contact contact = new Contact(req.getParameter("firstName"),
                req.getParameter("lastName"), req.getParameter("middleName"));
        DatabaseService databaseService = new DatabaseService();

        databaseService.createContact(contact);
        return new ModelAndView("contacts");
    }



}
