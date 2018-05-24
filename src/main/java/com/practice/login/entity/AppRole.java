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
	name = "app_role",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "app_role_role_name_key",
			columnNames = "role_name"
		)
	}
)
@Data
public class AppRole {

	@Id
	@GeneratedValue
	@Column(name = "role_id", nullable = false)
	private Long roleId;
	
	@Column(name = "role_name", length = 100, nullable = false)
	private String roleName;
	
}
