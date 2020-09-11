package net.juhn.roomworker;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import net.juhn.roomworker.cleaner.TaskRoomCleaner;

/**
 * Scheduler for room tasks.
 * @author Sascha
 *
 */
@Component
public class RoomScheduler {

	private Scheduler scheduler;

	@PersistenceContext
	EntityManager entityManager;

	@PostConstruct
	public void init() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			buildJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	private void buildJobs() {

		try {

			JobDetail job = JobBuilder.newJob(TaskRoomCleaner.class)
					.withIdentity("Room Cleaner")
					.build();

			Trigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInHours(1)
							.repeatForever())
					.build();

			scheduler.start();
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
