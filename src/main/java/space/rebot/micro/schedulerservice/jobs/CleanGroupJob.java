package space.rebot.micro.schedulerservice.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.rebot.micro.schedulerservice.service.CleanGroupService;

@Component
public class CleanGroupJob implements Job {
    @Autowired
    private CleanGroupService cleanGroupService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        cleanGroupService.cleanGroup();
    }
}
