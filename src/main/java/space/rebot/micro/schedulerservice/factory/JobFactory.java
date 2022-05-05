package space.rebot.micro.schedulerservice.factory;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public class JobFactory {
    public static JobDetail createJob(Class job, String name,
                                      String group, String description) {
        return JobBuilder.newJob().ofType(job)
                .storeDurably(false)
                .withIdentity(name, group)
                .withDescription(description)
                .build();
    }
}
