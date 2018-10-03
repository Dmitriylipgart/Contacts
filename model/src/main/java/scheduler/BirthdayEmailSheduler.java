package scheduler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

public class BirthdayEmailSheduler {

    public void startBirthdayShedule() throws SchedulerException
    {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(BirthdayEmailSender.class)
                .withIdentity("Birthday", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("birthdayTrigger", "group1")
                .withSchedule(dailyAtHourAndMinute(10, 0))
                .forJob("Birthday")
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
