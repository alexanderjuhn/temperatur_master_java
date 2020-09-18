package net.juhn.roomstatus.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.juhn.roomservice.EnumRoomData;
import net.juhn.roomservice.model.room.Room;
import net.juhn.roomservice.model.room.RoomData;
import net.juhn.roomservice.repository.RoomDataRepository;
import net.juhn.roomservice.repository.RoomRepository;
import net.juhn.roomservice.service.ServiceRoom;

@RestController
public class RoomStatusReporter {

	@Autowired
	private RoomDataRepository roomDataRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ServiceRoom serviceRoom;

	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	/**
	 * Entry point to report room status data
	 * 
	 * @param body Request body
	 * @return empty String
	 */
	@PostMapping(path = "/reportRoom")
	public String reportRoom(@RequestBody Map<String, String> body) {

		String roomName = body.get(EnumRoomData.room.name());
		Float temperature = ((float) (int) (Float.parseFloat(body.get(EnumRoomData.temperatur.name())) * 10)) / 10;
		Float humidity = ((float) (int) (Float.parseFloat(body.get(EnumRoomData.humidity.toString())) * 10)) / 10;
		Timestamp dateRecorded = formatDate(body.get(EnumRoomData.recordDate.toString()),
				body.get(EnumRoomData.recordTime.toString()));

		serviceRoom.updateRoom(roomName, temperature, humidity, dateRecorded);

		return "";
	}

	@CrossOrigin
	@GetMapping("/getRoomStatusesTotal")
	public String getRoomStatusesTotal() {
		try {
			List<RoomData> roomData = new ArrayList<RoomData>();
			roomDataRepository.findAll()
					.forEach(roomData::add);
			roomData.sort(Comparator.comparing(RoomData::toString));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(roomData);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@GetMapping("/")
	public String getStatus() {
		return "RoomStatus up and running!";
	}
	
	@CrossOrigin
	@GetMapping("/getRoomStatusesLatest")
	public String getRoomStatusesLatest() {
		try {
			List<Room> rooms = new ArrayList<Room>();
			List<RoomData> roomData = new ArrayList<RoomData>();
			roomRepository.findAll()
					.forEach(rooms::add);
			rooms.sort(Comparator.comparing(Room::getName));
			rooms.forEach(r -> roomDataRepository.findFirstByRoom_idOrderByDatecreatedDesc(r.getId()).ifPresent(roomData::add));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(roomData);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	private Timestamp formatDate(String recordDate, String recordTime) {
		try {
			return new Timestamp(formatter.parse(recordDate + " " + recordTime)
					.getTime());
		} catch (ParseException e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}
}