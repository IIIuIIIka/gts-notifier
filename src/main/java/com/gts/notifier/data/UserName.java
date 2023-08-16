package com.gts.notifier.data;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class UserName {
	
	@NonNull
	@Column(name="first_name")
	private String firstName;
	
	@Nullable
	@Column(name="middle_name")
	private String middleName;
	
	@NonNull
	@Column(name="last_name")
	private String lastName;
	
	@Override
	public String toString() {
		if( this.middleName != null ) {
			return String.format( "%s %s %s", this.lastName, this.firstName, this.middleName );
		} else {
			return String.format( "%s %s", this.lastName, this.firstName );
		}
	}
	
}
