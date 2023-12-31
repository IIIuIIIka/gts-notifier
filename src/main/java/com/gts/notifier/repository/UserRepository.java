package com.gts.notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gts.notifier.model.User;

/**
 * @author gorbachevov
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
