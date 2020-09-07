package net.juhn.roomworker.cleaner;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.juhn.roomstatus.service.ServiceRoom;

/**
 * Task to delete all room data that surpass a certain age.
 * @author Sascha
 *
 */
public class TaskRoomCleaner implements Job {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ServiceRoom serviceRoom = ctx.getBean(ServiceRoom.class);
		serviceRoom.cleanRoom();
	}

}
