package net.juhn.roomservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.juhn.roomservice.model.room.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>{
	List<Room> findAll();
    Optional<Room> findByName(String name);
}