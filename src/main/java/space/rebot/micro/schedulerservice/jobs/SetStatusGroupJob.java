package space.rebot.micro.schedulerservice.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import space.rebot.micro.orderservice.enums.GroupStatusEnum;
import space.rebot.micro.schedulerservice.service.SetStatusGroupService;

import java.util.UUID;

public class SetStatusGroupJob implements Job {
    @Autowired
    private SetStatusGroupService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        UUID groupId = UUID.fromString((String) context.getJobDetail().getJobDataMap().get("group"));
        GroupStatusEnum newStatus = GroupStatusEnum.valueOf((String) context.getJobDetail().getJobDataMap().get("newStatus"));
        GroupStatusEnum comparedStatus = GroupStatusEnum.valueOf((String) context.getJobDetail().getJobDataMap().get("comparedStatus"));
        service.setStatusToGroup(groupId, newStatus, comparedStatus);
    }
}
