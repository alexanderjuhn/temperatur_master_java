package net.juhn.roomstatus.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.juhn.roomstatus.model.room.Room;
import net.juhn.roomstatus.model.room.RoomData;
import net.juhn.roomstatus.repository.RoomDataRepository;
import net.juhn.roomstatus.repository.RoomRepository;

/**
 * Service to handle everything related to room status.
 * 
 * @author Sascha
 *
 */
@Component
public class ServiceRoom {
	
	final int DAYS_TO_KEEP_DATA = 1;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomDataRepository roomDataRepository;

	/**
	 * Insert room data into the database
	 * 
	 * @param roomName     Room name
	 * @param temperature  Temperature
	 * @param humidity     Humidity
	 * @param dateRecorded Date/Time the data was recorded
	 */
	public void updateRoom(String roomName, Float temperature, Float humidity, Timestamp dateRecorded) {
		Room room = getRoom(roomName);

		RoomData roomData = new RoomData();
		roomData.setTemperature(temperature);
		roomData.setHumidity(humidity);
		roomData.setDatecreated(dateRecorded);

		roomData.setRoom(room);
		roomDataRepository.save(roomData);
	}

	/**
	 * Return the room with the name provided. Creates the room if it does not exist
	 * yet
	 * 
	 * @param roomName Room name
	 * @return Room
	 */
	private Room getRoom(String roomName) {
		Optional<Room> room = roomRepository.findByName(roomName);
		if (room.isPresent()) {
			return room.get();
		} else {
			return room.orElse(roomRepository.save(new Room(roomName)));
		}
	}

	/**
	 * Delete room data that is older then a certain amount of days.
	 */
	public void cleanRoom() {
		List<RoomData> roomData = roomDataRepository.findAll();

		roomData.stream()
		.filter(rd -> rd.getDatecreated()
				.before(Timestamp.from(Instant.now()
						.minus(DAYS_TO_KEEP_DATA, ChronoUnit.DAYS))))
		.forEach(rd -> roomDataRepository.delete(rd));
	}
	
	public String getRoomStatuses() {
		//List<RoomData> latestRoomData = new ArrayList<RoomData>();
		//roomRepository.findAll().forEach(room -> roomDataRepository.fin);
		//roomRepository.findAll().stream().forEach(r -> roomDataRepository.findTopByOrderByRoom_id());
		return "";
	}
}