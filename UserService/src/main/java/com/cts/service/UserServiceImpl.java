package com.cts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.UserDTO;
import com.cts.entity.User;
import com.cts.exception.BadRequestException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
			throw new BadRequestException("Username cannot be empty");
		}
		if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
			throw new BadRequestException("Email cannot be empty");
		}
		if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
			throw new BadRequestException("Password cannot be empty");
		}
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new BadRequestException("Email already exists");
		}
		if (!isValidRole(userDTO.getRole())) {
			throw new BadRequestException("Invalid role: " + userDTO.getRole());
		}

		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());

		User savedUser = userRepository.save(user);
		return mapToDTO(savedUser);
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapToDTO(user);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

		if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
			user.setUsername(userDTO.getUsername());
		}
		if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
			if (!userDTO.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
				throw new BadRequestException("Email already exists");
			}
			user.setEmail(userDTO.getEmail());
		}
		if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
			user.setPassword(userDTO.getPassword());
		}
		if (userDTO.getRole() != null && !isValidRole(userDTO.getRole())) {
			throw new BadRequestException("Invalid role: " + userDTO.getRole());
		}
		if (userDTO.getRole() != null) {
			user.setRole(userDTO.getRole());
		}

		User updatedUser = userRepository.save(user);
		return mapToDTO(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO assignRole(Long userId, String role) {
		if (!isValidRole(role)) {
			throw new BadRequestException("Invalid role: " + role);
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		user.setRole(role);
		User updatedUser = userRepository.save(user);
		return mapToDTO(updatedUser);
	}

	private UserDTO mapToDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getUserId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
	}

	private boolean isValidRole(String role) {
		return role != null && (role.equals("Student") || role.equals("Examiner") || role.equals("Admin"));
	}
}