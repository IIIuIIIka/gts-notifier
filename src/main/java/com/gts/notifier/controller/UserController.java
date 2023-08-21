package com.gts.notifier.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gts.notifier.dto.UserDTO;
import com.gts.notifier.model.User;
import com.gts.notifier.service.UserService;

/**
 * @author gorbachevov 
 */

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private UserService userService;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService service, ModelMapper mapper) {
		this.userService = service;
		this.modelMapper = mapper;
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody UserDTO user) {
		User request = convertFromDTO(user);
		request = userService.create(request);
		return ResponseEntity.ok(convertFromEntity(request));
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		 Optional<User> user = userService.findById(id);
		 if( user.isEmpty() ) {
			 return ResponseEntity.notFound().build();
		 }
		 return ResponseEntity.ok(convertFromEntity(user.get()));
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserDTO user) {
		Optional<User> exist = userService.findById(id);
		if( exist.isEmpty() )
			return ResponseEntity.notFound().build();
		User updated = convertFromDTO(user);
		updated.setId( exist.get().getId() );
		userService.update(updated);
		return ResponseEntity.ok(convertFromEntity(updated));
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		 Optional<User> user = userService.findById(id);
		 if( user.isEmpty() ) {
			 return ResponseEntity.notFound().build();
		 }
		 userService.delete( user.get() );
		 return ResponseEntity.ok().build();
	}
	
	private User convertFromDTO(UserDTO user) {
		return modelMapper.map(user, User.class);
	}
	
	private UserDTO convertFromEntity(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	
}
