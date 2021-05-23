package com.vsvdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsvdev.dto.ResponseMessage;
import com.vsvdev.model.User;
import com.vsvdev.service.KeycloakService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	@Autowired
	private KeycloakService keycloakService;

	@PostMapping("/create")
	public ResponseEntity<ResponseMessage> create(@RequestBody User user) {
		Object[] obj = keycloakService.createUser(user);
		int status = (int) obj[0];
		ResponseMessage message = (ResponseMessage) obj[1];
		return ResponseEntity.status(status).body(message);
	}
}
