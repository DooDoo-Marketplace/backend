package space.rebot.micro.schedulerservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import space.rebot.micro.orderservice.enums.GroupStatusEnum;
import space.rebot.micro.schedulerservice.factory.JobFactory;
import space.rebot.micro.schedulerservice.factory.SimpleTriggerFactory;
import space.rebot.micro.schedulerservice.jobs.SetStatusGroupJob;
import space.rebot.micro.userservice.service.DateService;

import java.util.UUID;

@Service
public class StartJobsService {
    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private DateService dateService;

    @Value("${scheduler.time.extra}")
    private int EXTRA_HOUR;

    @Value("${scheduler.time.existing}")
    private int EXISTING_DAY;

    public void setCanceledStatusToGroup(UUID id) {
        JobDetail job = JobFactory.createJob(SetStatusGroupJob.class,
                "SetCanceledStatus " + id, "SetCanceledStatusAfterDay",
                "Set group status = CANCELED if group isn't full");
        job.getJobDataMap().put("group", id.toString());
        job.getJobDataMap().put("newStatus", GroupStatusEnum.CANCELED.getName());
        job.getJobDataMap().put("comparedStatus", GroupStatusEnum.ACTIVE.getName());

        SimpleTrigger trigger = SimpleTriggerFactory.createTrigger("SetCanceledToGroupTrigger " + id,
                "SetCanceledAfterDay", dateService.addTimeForCurrent(EXISTING_DAY), job);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getStackTrace());
        }
    }

    public void setCompletedStatusToGroup(UUID id) {
        JobDetail job = JobFactory.createJob(SetStatusGroupJob.class,
                "SetCompletedStatus " + id, "SetCompletedStatusAfterHour",
                "Set group status = COMPLETED after 1 hour being EXTRA");
        job.getJobDataMap().put("group", id.toString());
        job.getJobDataMap().put("newStatus", GroupStatusEnum.COMPLETED.getName());
        job.getJobDataMap().put("comparedStatus", GroupStatusEnum.EXTRA.getName());

        SimpleTrigger trigger = SimpleTriggerFactory.createTrigger("SetCompletedToGroupTrigger " + id,
                "SetCompletedAfterHour", dateService.addTimeForCurrent(EXTRA_HOUR), job);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getStackTrace());
        }
    }
}
