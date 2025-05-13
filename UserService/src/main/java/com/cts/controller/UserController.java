package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.LoginRequest;
import com.cts.dto.RoleUpdateRequest;
import com.cts.dto.UserDTO;
import com.cts.entity.User;
import com.cts.exception.BadRequestException;
import com.cts.repository.UserRepository;
import com.cts.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	
	@Autowired
	private final UserService userService;
	@Autowired
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
		return userService.createUser(userDTO);
	}

	@PostMapping("/login")
	public UserDTO login(@RequestBody LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new BadRequestException("Invalid email or password"));
		if (!loginRequest.getPassword().equals(user.getPassword())) {
			throw new BadRequestException("Invalid email or password");
		}
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getUserId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
	}

	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
		return userService.updateUser(id, userDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@GetMapping("/getAll")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@PutMapping("/{id}/role")
	public UserDTO assignRole(@PathVariable Long id, @RequestBody RoleUpdateRequest roleUpdateRequest) {
		return userService.assignRole(id, roleUpdateRequest.getRole());
	}

	@GetMapping("/email/{email}")
	public UserDTO getUserByEmail(@PathVariable String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BadRequestException("User not found with email: " + email));
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getUserId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
	}
}