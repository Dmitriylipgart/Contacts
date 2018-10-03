package scheduler;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.cronSchedule;


public class TestShedule {

    public void startBirthdayShedule() throws SchedulerException
    {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(BirthdayEmailSender.class)
                .withIdentity("Birthday", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Test", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(20)
                        .repeatForever())
                .forJob("Birthday", "group1")
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
