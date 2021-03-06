package com.itechart.model.utils;

import com.itechart.model.dao.ContactDao;
import com.itechart.model.dao.ContactDaoImpl;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.dto.EmailDto;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.stringtemplate.v4.ST;

import java.util.List;


public class EmailHandler {

    ContactDao contactDao = new ContactDaoImpl();

    public void sendEmail(EmailDto emailDto){

        List<ContactDto> contacts = contactDao.getContactsById(emailDto.getContactIdList());
        String template = emailDto.getText();
        String header =emailDto.getHeader();
        sendEmailToContacts(contacts, template, header);

    }
    public void sendSheduelEmail(List<ContactDto> contacts, String template, String header){
            sendEmailToContacts(contacts, template, header);
    }

    private void sendEmailToContacts(List<ContactDto> contacts, String template, String header){


        for (ContactDto contact: contacts) {
            try {
                ST message = new ST(template);
                message.add("firstName", contact.getFirstName());
                message.add("lastName", contact.getLastName());
                message.add("middleName", contact.getMiddleName());
                String emailText = message.render();
                Email email = new SimpleEmail();
                email.setCharset("utf-8");
                email.setHostName("smtp.mail.ru");
                email.setSmtpPort(465);
                email.setAuthentication("itechertlab@mail.ru", "12345678Itl");
                email.setSSLOnConnect(true);
                email.setFrom("itechertlab@mail.ru");
                email.setSubject(header);
                email.setMsg(emailText);
                email.addTo(contact.getEmail());
                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
    }


}
