package net.juhn.roomservice.model.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "ROOM" database table.
 * 
 */
@Entity
@Table(name="room",schema="statusdata")
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="room_seq", initialValue=1, allocationSize=1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="room_seq")
    @Column(name="ID", unique=true, nullable=false)
    private Long id;

    @Column(name="NAME", nullable=false, length=32)
    private String name;

    public Room() {
    }
    
    public Room(String name) {
        this.name=name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}