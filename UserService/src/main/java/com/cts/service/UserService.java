package com.cts.service;

import java.util.List;

import com.cts.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(Long id);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	List<UserDTO> getAllUsers();

	UserDTO assignRole(Long userId, String role);
}