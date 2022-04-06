package space.rebot.micro.schedulerservice.jobs;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.rebot.micro.schedulerservice.service.CleanCartService;

@Component
public class CleanCartJob implements Job {
    @Autowired
    private CleanCartService cleanCartService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        cleanCartService.cleanCart();
    }
}
