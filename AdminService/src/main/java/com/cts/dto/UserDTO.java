package com.cts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
	@GeneratedValue
	private Long userId;
	@NotBlank(message = "Username cannot be empty")
	private String username;
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be empty")
	private String email;

	@JsonProperty("password")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")

	private String password;
	@NotBlank(message = "Role cannot be empty")
	private String role;

}