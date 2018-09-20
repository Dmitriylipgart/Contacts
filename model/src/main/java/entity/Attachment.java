package entity;


import java.util.Date;

public class Attachment implements Entity{
    private String fileName;
    private Date fileDate;
    private String comment;

    public Attachment(String fileName, Date fileDate, String comment) {
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.comment = comment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
