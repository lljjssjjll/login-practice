package com.practice.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(
	name = "app_user",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "app_user_user_name_key",
			columnNames = "user_name"
		)
	}
)
@Data
public class AppUser {

	@Id
	@GeneratedValue
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "user_name", length = 100, nullable = false)
	private String userName;
	
	@Column(name = "enc_password", length = 200, nullable = false)
	private String encPassword;
	
	@Column(name = "enabled", length = 1, nullable = false)
	private boolean enabled;
	
}
