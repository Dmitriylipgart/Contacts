package services;


import utils.StringTemplateHandler;

import java.io.IOException;

public class EmailService {

    StringTemplateHandler templateHandler = new StringTemplateHandler();

    public String getTemplateText(String templateName) throws IOException {
        return templateHandler.getTemplateByName(templateName);
    }
}
