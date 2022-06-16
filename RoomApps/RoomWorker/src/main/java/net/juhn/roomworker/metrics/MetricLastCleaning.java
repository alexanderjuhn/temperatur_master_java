package net.juhn.roomworker.metrics;

import java.time.Instant;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.juhn.roomservice.service.ServiceRoom;

public class MetricLastCleaning implements Supplier{

	ServiceRoom serviceRoom;
	
	public MetricLastCleaning() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
        serviceRoom = ctx.getBean(ServiceRoom.class);
	}
	
	@Override
	public Object get() {
		System.out.println(Instant.now());
		System.out.println(serviceRoom.getLastCleaning());
		System.out.println(Instant.now().toEpochMilli()/1000-serviceRoom.getLastCleaning());
		return (Instant.now().toEpochMilli()-serviceRoom.getLastCleaning());
	}

}
