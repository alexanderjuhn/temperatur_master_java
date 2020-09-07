package net.juhn.roomworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RoomStatusApplication {
	

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomStatusApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    } 

}