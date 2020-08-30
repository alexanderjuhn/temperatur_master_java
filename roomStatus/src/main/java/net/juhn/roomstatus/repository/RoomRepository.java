package net.juhn.roomstatus.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.juhn.roomstatus.model.room.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>{
	Optional<Room> findByName(String name);
}
