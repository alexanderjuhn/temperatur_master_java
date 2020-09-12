package net.juhn.roomworker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomWorkerController {
	@GetMapping("/")
	public String getStatus() {
		return "RoomWorker up and running!";
	}
}
