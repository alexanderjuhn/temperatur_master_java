package net.juhn.roomstatus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.juhn.roomstatus.model.room.RoomData;

@Repository
public interface RoomDataRepository extends CrudRepository<RoomData, Long>{

}
