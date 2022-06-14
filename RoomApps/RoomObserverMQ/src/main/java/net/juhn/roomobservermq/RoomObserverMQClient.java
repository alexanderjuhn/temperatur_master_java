package net.juhn.roomobservermq;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import net.juhn.roomservice.service.ServiceRoom;

@SpringBootApplication
@ComponentScan({ "net.juhn.roomservice", "net.juhn.roomobservermq" })
@EnableJpaRepositories({ "net.juhn.roomservice", "net.juhn.roomobservermq" })
@EntityScan({ "net.juhn.roomservice", "net.juhn.roomobservermq" })
public class RoomObserverMQClient implements CommandLineRunner{

	@Autowired
	private ServiceRoom serviceRoom;
	
	@Value("${rabbitmq.queue_name}")
    String queue_name;
	
	@Value("${rabbitmq.host_name}")
    String host_name;
	
	@Value("${rabbitmq.port}")
    int port;
	
	@Value("${rabbitmq.username}")
    String username;
	
	@Value("${rabbitmq.password}")
    String password;

	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	Logger logger = LogManager.getLogger(RoomObserverMQClient.class);

	public static void main(String args[]) throws Exception {
		
		SpringApplication springApplication = new SpringApplication(RoomObserverMQClient.class);
    	springApplication.addListeners(new ApplicationPidFileWriter());
    	// Webserver has to stay enabled to access Prometheus endpoint
    	//springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);

	}
	
	public void run(String... args) throws Exception{
		receive();
	}

	private void receive() throws Exception {

		Channel channel =  buildChannel(queue_name);
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			try {
				JSONObject jsonObject = new JSONObject(message);
				String room=jsonObject.getString("room");
				float temperatur=(float)jsonObject.getDouble("temperatur");
				float humidity=(float)jsonObject.getDouble("humidity");
				Timestamp dateRecorded = formatDate(jsonObject.getString("recordDate"),jsonObject.getString("recordTime"));
				serviceRoom.updateRoom(room, temperatur, humidity, dateRecorded);
				logger.info(" [x] Received '" + message + "'");
			} catch (JSONException e) {
				e.printStackTrace();
			}

		};
		channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> {
		});

	}

	/**
	 * Build Channel Object for connection to RabbitMQ node
	 */
	private Channel buildChannel(String queue_name) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost(this.host_name);
		factory.setPort(this.port);
		factory.setUsername(this.username);
		factory.setPassword(this.password);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(this.queue_name, true, false, false, null);
		
		return channel;
	}
	
    private Timestamp formatDate(String recordDate, String recordTime) {
        try {
            return new Timestamp(formatter.parse(recordDate+" "+recordTime).getTime());
        } catch (ParseException e) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

}
