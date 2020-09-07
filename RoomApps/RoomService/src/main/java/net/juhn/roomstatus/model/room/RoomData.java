package net.juhn.roomstatus.model.room;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the "ROOMDATA" database table.
 * 
 */
@Entity
@Table(name = "roomdata", schema = "statusdata")
@NamedQuery(name = "RoomData.findAll", query = "SELECT r FROM RoomData r")
public class RoomData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "roomdata_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomdata_seq")
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "DATECREATED", nullable = false)
	private Timestamp datecreated;

	@Column(name = "HUMIDITY")
	private double humidity;

	@Column(name = "TEMPERATURE")
	private double temperature;

	@ManyToOne
	@JoinColumn(name = "ROOM_ID")
	private Room room;

	public RoomData() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public double getHumidity() {
		return this.humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String toString() {
		return this.getDatecreated() + ":" + this.getRoom()
				.getName();
	}

}