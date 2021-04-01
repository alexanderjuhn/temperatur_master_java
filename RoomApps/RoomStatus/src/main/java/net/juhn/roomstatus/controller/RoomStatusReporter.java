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
import org.springframework.web.bind.annotation.RequestHeader;
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



	@CrossOrigin
	@GetMapping("/getRoomStatusesTotal")
	public String getRoomStatusesTotal() {
		try {
			List<RoomData> roomData = new ArrayList<RoomData>();
			roomDataRepository.findAll().forEach(roomData::add);
			roomData.sort(Comparator.comparing(RoomData::toString));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(roomData);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@GetMapping(path = "/getRoomStatusGraphByName")
	public String getRoomStatusGraph(@RequestHeader Map<String, String> body) {
		String json = "";
		try {
			String roomName = body.get(EnumRoomData.room.name());
			List<RoomData> roomData = serviceRoom.getRoomStatusGraphByName(roomName);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(roomData);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
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
			roomRepository.findAll().forEach(rooms::add);
			rooms.sort(Comparator.comparing(Room::getName));
			rooms.forEach(r -> roomDataRepository.findFirstByRoom_idOrderByDatecreatedDesc(r.getId())
					.ifPresent(roomData::add));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(roomData);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}