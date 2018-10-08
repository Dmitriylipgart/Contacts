package com.itechart.model.utils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


public class StringTemplateHandler {
    private String absoulutePath = this.getClass().getClassLoader().getResource("").getPath();
    private String templateFolder = "static/templates/";

    public String getTemplateByName(String templateName){

        String path = absoulutePath + templateFolder + templateName + ".txt";
        String template = null;
        try {
            template = FileUtils.readFileToString(new File(path), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }
}
