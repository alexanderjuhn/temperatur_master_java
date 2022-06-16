package net.juhn.roomworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class RoomWorkerApplication {
	

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomWorkerApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    } 
    
}