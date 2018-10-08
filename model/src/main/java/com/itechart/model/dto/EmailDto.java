package com.itechart.model.dto;


import java.util.List;

public class EmailDto {
    private List<Long> contactIdList;
    private String header;
    private String text;

    public EmailDto() {
    }

    public List<Long> getContactIdList() {
        return contactIdList;
    }

    public void setContactIdList(List<Long> contactIdList) {
        this.contactIdList = contactIdList;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
