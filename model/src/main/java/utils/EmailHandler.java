package utils;

import dao.ContactDao;
import dao.ContactDaoImpl;
import dto.ContactDto;
import dto.EmailDto;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.stringtemplate.v4.ST;

import java.util.List;


public class EmailHandler {

    ContactDao contactDao = new ContactDaoImpl();

    public void sendEmail(EmailDto emailDto){

        List<ContactDto> contacts = contactDao.getContactsById(emailDto.getContactIdList());

        for (ContactDto contact: contacts) {
            try {
                ST message = new ST(emailDto.getText());
                message.add("firstName", contact.getFirstName());
                message.add("lastName", contact.getLastName());
                message.add("middleName", contact.getMiddleName());
                String emailText = message.render();
                Email email = new SimpleEmail();
                email.setHostName("smtp.mail.ru");
                email.setSmtpPort(465);
//                email.setAuthenticator(new DefaultAuthenticator("itechertlab@mail.ru", "12345678Itl"));
                email.setAuthentication("itechertlab@mail.ru", "12345678Itl");
                email.setSSLOnConnect(true);
                email.setFrom("itechertlab@mail.ru");
                email.setSubject(emailDto.getHeader());
                email.setMsg(emailText);
                email.addTo(contact.getEmail());
                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
    }
}
