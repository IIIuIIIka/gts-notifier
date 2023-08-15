package com.gts.notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gts.notifier.data.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

}
