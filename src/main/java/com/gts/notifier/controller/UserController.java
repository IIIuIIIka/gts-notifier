package com.gts.notifier.controller;

import java.util.Optional;

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

import com.gts.notifier.model.User;
import com.gts.notifier.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody User user) {
		user = userService.create(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		 Optional<User> user = userService.findById(id);
		 if( user.isEmpty() ) {
			 return ResponseEntity.notFound().build();
		 }
		 return ResponseEntity.ok(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User user) {
		Optional<User> exist = userService.findById(id);
		if( exist.isEmpty() )
			return ResponseEntity.notFound().build();
		user.setId(exist.get().getId());
		userService.create(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/users/id")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		 Optional<User> user = userService.findById(id);
		 if( user.isEmpty() ) {
			 return ResponseEntity.notFound().build();
		 }
		 userService.delete( user.get() );
		 return ResponseEntity.ok().build();
	}
	
}
