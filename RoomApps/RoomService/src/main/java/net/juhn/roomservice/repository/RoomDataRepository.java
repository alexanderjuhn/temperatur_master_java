package net.juhn.roomservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.juhn.roomservice.model.room.RoomData;

@Repository
public interface RoomDataRepository extends CrudRepository<RoomData, Long>{
	List<RoomData> findAll();
	
	//List<RoomData> findFirstByOrderByDatecreatedDesc();
	
	Optional<RoomData> findFirstByRoom_idOrderByDatecreatedDesc(long room_id);
}