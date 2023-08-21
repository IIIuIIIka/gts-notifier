package com.gts.notifier.service;

import java.util.List;
import java.util.Optional;

import com.gts.notifier.model.User;

/**
 * @author gorbachevov
 */

public interface UserService {

	Optional<User> findById( Long id );
	List<User> findAll();
	
	User create(User user);
	User update(User user);
	void delete(Long id);
	
}
