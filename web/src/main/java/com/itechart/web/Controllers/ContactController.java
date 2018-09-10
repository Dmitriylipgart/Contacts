package com.itechart.web.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @RequestMapping
    public ModelAndView getContactPage(){
        return new ModelAndView("contactPage");
    }




}
