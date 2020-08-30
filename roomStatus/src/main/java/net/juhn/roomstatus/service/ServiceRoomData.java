package net.juhn.roomstatus.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.juhn.roomstatus.model.room.Room;
import net.juhn.roomstatus.model.room.RoomData;
import net.juhn.roomstatus.repository.RoomDataRepository;
import net.juhn.roomstatus.repository.RoomRepository;

/**
 * Service to handle everything related to room status.
 * @author Sascha
 *
 */
@Component
public class ServiceRoomData {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomDataRepository roomDataRepository;
	
	/**
	 * Insert room data into the database
	 * @param roomName Room name
	 * @param temperature Temperature
	 * @param humidity Humidity
	 * @param dateRecorded Date/Time the data was recorded
	 */
	public void updateRoom(String roomName, Float temperature, Float humidity, Timestamp dateRecorded) {
		Room room=getRoom(roomName);
		
		RoomData roomData = new RoomData();
		roomData.setTemperature(temperature);
		roomData.setHumidity(humidity);
		roomData.setDatecreated(dateRecorded);
		
		roomData.setRoom(room);
		roomDataRepository.save(roomData);
	}
	
	/**
	 * Return the room with the name provided.
	 * Creates the room if it does not exist yet
	 * @param roomName Room name
	 * @return Room
	 */
	private Room getRoom(String roomName) {
		Optional<Room> room = roomRepository.findByName(roomName);
		if(room.isPresent()) {
			return room.get();
		} else {
			return room.orElse(roomRepository.save(new Room(roomName)));
		}
	}
}
