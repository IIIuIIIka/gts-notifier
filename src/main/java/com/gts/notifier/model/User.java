package com.gts.notifier.model;

import java.util.List;

import org.springframework.lang.Nullable;

import com.gts.notifier.data.UserName;
import com.gts.notifier.data.UserTimeSlot;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gorbachevov
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private UserName name;
	
	@Nullable
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	private List<UserTimeSlot> timeSlots;
}
