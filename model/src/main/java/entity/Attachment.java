package entity;


import org.springframework.http.codec.multipart.Part;

import java.util.Date;

public class Attachment {
    private String fileName;
    private String comment;
    private Part file;

    public Attachment() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
