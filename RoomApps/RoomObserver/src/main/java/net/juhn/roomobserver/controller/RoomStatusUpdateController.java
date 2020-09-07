package net.juhn.roomobserver.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.juhn.roomstatus.EnumRoomData;
import net.juhn.roomstatus.service.ServiceRoom;


@RestController
public class RoomStatusUpdateController {
    
    @Autowired
    private ServiceRoom serviceRoom;
    
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
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

        serviceRoom.updateRoom(roomName, temperature, humidity, dateRecorded);
        
        return "";
    }
    
    private Timestamp formatDate(String recordDate, String recordTime) {
        try {
            return new Timestamp(formatter.parse(recordDate+" "+recordTime).getTime());
        } catch (ParseException e) {
            return new Timestamp(System.currentTimeMillis());
        }
    }
}