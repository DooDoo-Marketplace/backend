package space.rebot.micro.schedulerservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import space.rebot.micro.schedulerservice.factory.JobFactory;
import space.rebot.micro.schedulerservice.factory.SimpleTriggerFactory;
import space.rebot.micro.schedulerservice.jobs.SetCanceledStatusGroupJob;
import space.rebot.micro.schedulerservice.jobs.SetCompletedStatusGroupJob;
import space.rebot.micro.userservice.service.DateService;

import java.util.UUID;

@Service
public class StartJobsService {
    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    @Qualifier("scheduler")
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private DateService dateService;

    public void setCanceledStatusToGroup(UUID id) {
        JobDetail job = JobFactory.createJob(SetCanceledStatusGroupJob.class,
                "SetCanceledStatus " + id, "SetCanceledStatusAfterDay",
                "Set group status = CANCELED if group isn't full");
        job.getJobDataMap().put("group", id.toString());

        SimpleTrigger trigger = SimpleTriggerFactory.createTrigger("SetCanceledToGroupTrigger " + id,
                "SetCanceledAfterDay", dateService.addTimeForCurrent(24), job);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCompletedStatusToGroup(UUID id) {
        JobDetail job = JobFactory.createJob(SetCompletedStatusGroupJob.class,
                "SetCompletedStatus " + id, "SetCompletedStatusAfterHour",
                "Set group status = COMPLETED after 1 hour being EXTRA");
        job.getJobDataMap().put("group", id.toString());

        SimpleTrigger trigger = SimpleTriggerFactory.createTrigger("SetCompletedToGroupTrigger " + id,
                "SetCompletedAfterHour", dateService.addTimeForCurrent(1), job);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }
}
