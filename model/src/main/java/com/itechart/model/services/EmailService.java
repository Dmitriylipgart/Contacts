package com.itechart.model.services;
import com.itechart.model.dto.EmailDto;
import com.itechart.model.utils.EmailHandler;
import com.itechart.model.utils.StringTemplateHandler;

import java.io.IOException;

public class EmailService {

    StringTemplateHandler templateHandler = new StringTemplateHandler();
    EmailHandler emailHandler = new EmailHandler();

    public String getTemplateText(String templateName) throws IOException {
        return templateHandler.getTemplateByName(templateName);
    }
    public void send(EmailDto email){
        emailHandler.sendEmail(email);
    }
}
