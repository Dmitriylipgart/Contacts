package com.itechart.model.scheduler;


import com.itechart.model.dao.ContactDaoImpl;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.utils.EmailHandler;
import com.itechart.model.utils.StringTemplateHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


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
        Date date = new Date();
        String today = dateFormat.format(date);

        String message = stringTemplateHandler.getTemplateByName("birthday");
        List<ContactDto> contactsToSend = contactDao.readAllByDate(today);

        if (contactsToSend.size() > 0) {
            emailHandler.sendSheduelEmail(contactsToSend, message, header);
        }

    }
}

