package net.juhn.roomworker.controller;

import java.util.Enumeration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.CollectorRegistry;
import net.juhn.roomservice.service.ServiceRoom;
import net.juhn.roomworker.metrics.MetricsCollector;

@RestController
public class RoomWorkerController {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
	
	@GetMapping("/")
	public String getStatus() {
		return "RoomWorker up and running!";
	}
	
}
