package net.juhn.roomstatus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.juhn.roomstatus.model.room.Room;
import net.juhn.roomstatus.model.room.RoomData;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>{
	List<Room> findAll();
    Optional<Room> findByName(String name);
}