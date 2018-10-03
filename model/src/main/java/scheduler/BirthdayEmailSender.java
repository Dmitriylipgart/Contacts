package scheduler;

import dao.ContactDaoImpl;
import dto.ContactDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.EmailHandler;
import utils.StringTemplateHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BirthdayEmailSender implements Job {


    StringTemplateHandler stringTemplateHandler = new StringTemplateHandler();
    ContactDaoImpl contactDao = new ContactDaoImpl();
    EmailHandler emailHandler = new EmailHandler();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("job");
        String header = "Happy BirthDay!!!";
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        DateFormat dateFormatForContact = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);

        String message = stringTemplateHandler.getTemplateByName("birthday");
        List<ContactDto> contacts = contactDao.readAll();
        List<ContactDto> contactsToSend = new ArrayList<>();
        for (ContactDto contact : contacts) {

            try {
                Date birthdate = dateFormatForContact.parse(contact.getBirthDate());
                if (today.equals(dateFormat.format(birthdate))) {
                    contactsToSend.add(contact);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (contactsToSend.size() > 0) {
            emailHandler.sendSheduelEmail(contactsToSend, message, header);
        }

    }
}

