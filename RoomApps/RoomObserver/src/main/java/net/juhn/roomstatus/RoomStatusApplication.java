package net.juhn.roomstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class RoomStatusApplication {

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomStatusApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

}