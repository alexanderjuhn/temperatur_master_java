package net.juhn.roomobserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class RoomObserverApplication {

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomObserverApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    } 

}