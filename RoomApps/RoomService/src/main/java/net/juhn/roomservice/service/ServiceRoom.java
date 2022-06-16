package net.juhn.roomservice.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.juhn.roomservice.model.room.Room;
import net.juhn.roomservice.model.room.RoomData;
import net.juhn.roomservice.repository.RoomDataRepository;
import net.juhn.roomservice.repository.RoomRepository;

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
	
	Logger logger = LogManager.getLogger(ServiceRoom.class);
	
	private static int deletedDataRecords = 0;
	private static long lastCleaning = 0;

	/**
	 * Insert room data into the database
	 * 
	 * @param roomName     Room name
	 * @param temperature  Temperature
	 * @param humidity     Humidity
	 * @param dateRecorded Date/Time the data was recorded
	 */
	public void updateRoom(String roomName, Float temperature, Float humidity, Timestamp dateRecorded) {
		if(roomName.isEmpty()) {
			return;
		}
		Room room = getRoom(roomName).orElse(createRoom(roomName));

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
	private Optional<Room> getRoom(String roomName) {
		return roomRepository.findByName(roomName);
	}
	
	private Room createRoom(String roomName) {
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
		deletedDataRecords = 0;
		
		logger.info("Attemting to delete old data.");
		roomData.stream()
		.filter(rd -> rd.getDatecreated()
				.before(Timestamp.from(Instant.now()
						.minus(DAYS_TO_KEEP_DATA, ChronoUnit.DAYS))))
		.forEach(rd -> {
			roomDataRepository.delete(rd);
			deletedDataRecords++;
		});
		lastCleaning = Instant.now().toEpochMilli();
		logger.info("Deleted " + deletedDataRecords + " records.");
	}
	
	/**
	 * Get all available roomData for a given room.
	 * @param roomName Room Name
	 * @return List of all available room data
	 */
	public List<RoomData> getRoomStatusGraphByName(String roomName) {
		List<RoomData> roomData=new ArrayList<RoomData>();
		getRoom(roomName).ifPresent(r -> {
			roomData.addAll(roomDataRepository.findAllByRoom_idOrderByDatecreatedDesc(r.getId()));
		});
		return roomData;
	}
	
	public int getDeletedDataRecords() {
		return deletedDataRecords;
	}
	
	public long getLastCleaning() {
		return lastCleaning;
	}
}