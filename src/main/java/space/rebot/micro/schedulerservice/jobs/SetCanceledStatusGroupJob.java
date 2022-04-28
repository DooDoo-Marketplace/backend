package space.rebot.micro.schedulerservice.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import space.rebot.micro.schedulerservice.service.SetCanceledStatusGroupService;

import java.util.UUID;


public class SetCanceledStatusGroupJob implements Job {
    @Autowired
    private SetCanceledStatusGroupService service;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String groupId = (String) context.getJobDetail().getJobDataMap().get("group");
        service.setCanceledStatusToGroup(UUID.fromString(groupId));
    }

}
