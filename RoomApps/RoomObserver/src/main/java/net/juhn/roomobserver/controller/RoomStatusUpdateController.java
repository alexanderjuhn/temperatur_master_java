package net.juhn.roomobserver.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.juhn.roomservice.EnumRoomData;
import net.juhn.roomservice.service.ServiceRoom;


@RestController
public class RoomStatusUpdateController {
    
    @Autowired
    private ServiceRoom serviceRoom;
    
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
    @Value("${spring.datasource.url}")
    String datasource;
    
    Logger logger = LogManager.getLogger(RoomStatusUpdateController.class);
    
    /**
     * Entry point to report room status data
     * @param body Request body
     * @return empty String
     */
    @PostMapping(path="/reportRoom")
    public String reportRoom(@RequestBody Map<String, String> body) {

        String roomName = body.get(EnumRoomData.room.name());
        Float temperature = ((float)(int)(Float.parseFloat(body.get(EnumRoomData.temperatur.name()))*10))/10;
        Float humidity = ((float)(int)(Float.parseFloat(body.get(EnumRoomData.humidity.toString()))*10))/10;
        Timestamp dateRecorded = formatDate(body.get(EnumRoomData.recordDate.toString()),body.get(EnumRoomData.recordTime.toString()));
        
        logger.log(Level.INFO, "report: "+roomName + " : "+ temperature + " : "+  humidity + " : "+  dateRecorded);
        System.out.println(roomName + " : "+ temperature + " : "+  humidity + " : "+  dateRecorded);
        serviceRoom.updateRoom(roomName, temperature, humidity, dateRecorded,0);
        
        return "";
    }
    
    private Timestamp formatDate(String recordDate, String recordTime) {
        try {
            return new Timestamp(formatter.parse(recordDate+" "+recordTime).getTime());
        } catch (ParseException e) {
            return new Timestamp(System.currentTimeMillis());
        }
    }
    
	@GetMapping("/")
	public String getStatus() {
		System.out.println("Status requested");
		logger.log(Level.INFO, "I'm a logger");
		
		return "RoomObserver up and running!  Reporting to "+datasource;
	}
}