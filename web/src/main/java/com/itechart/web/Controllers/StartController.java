package com.itechart.web.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class StartController {

    @RequestMapping
    public String getContacts(Model model){

        return "contacts";
    }

}
