package net.juhn.roomworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class RoomWorkerApplication {
	

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomWorkerApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    } 

}