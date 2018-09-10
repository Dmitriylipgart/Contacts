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

        Map<String, String> contacts = new HashMap<>();
        contacts.put("id", "1");
        contacts.put("name", "John Doe");
        contacts.put("date", "01.01.2001");
        contacts.put("address", "Gomel");
        contacts.put("job", "itechart");
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

}
