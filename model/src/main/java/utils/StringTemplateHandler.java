package utils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


public class StringTemplateHandler {
    private String absoulutePath = this.getClass().getClassLoader().getResource("").getPath();
    private String templateFolder = "static/templates/";

    public String getTemplateByName(String templateName) throws IOException {

        String path = absoulutePath + templateFolder + templateName + ".txt";
        String contents = FileUtils.readFileToString(new File(path), "UTF-8");
        return contents;
    }
}
