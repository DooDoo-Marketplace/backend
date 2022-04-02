package space.rebot.micro.schedulerservice.configuration;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import space.rebot.micro.schedulerservice.jobs.CleanCartJob;

@Configuration
public class QuartzSubmitJobs {
    private final String CRON_EVERY_MIDNIGHT = "0 0 0 * * *";
    private final String CRON_EVERY_MINUTE = "0 0/1 * ? * * *";


    @Bean(name = "cleanCart")
    public JobDetailFactoryBean jobMemberClassStats() {
        return QuartzConfig.createJobDetail(CleanCartJob.class,
                "Clean cart table job");
    }

    @Bean(name = "cleanCartTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("cleanCart") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_MINUTE,
                "Clean cart table trigger");
    }
}

