package net.juhn.roomstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"net.juhn.roomservice","net.juhn.roomstatus"})
@EnableJpaRepositories({"net.juhn.roomservice","net.juhn.roomstatus"})
@EntityScan({"net.juhn.roomservice","net.juhn.roomstatus"})
public class RoomStatusApplication {

    public static void main(String[] args) {
    	SpringApplication springApplication = new SpringApplication(RoomStatusApplication.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    } 

}