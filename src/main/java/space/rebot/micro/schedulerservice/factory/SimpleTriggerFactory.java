package space.rebot.micro.schedulerservice.factory;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

public class SimpleTriggerFactory {
    public static SimpleTrigger createTrigger(String name, String group,
                                              Date date, JobDetail job) {
        return (SimpleTrigger) newTrigger()
                .withIdentity(name, group)
                .startAt(date)
                .forJob(job)
                .build();
    }
}
