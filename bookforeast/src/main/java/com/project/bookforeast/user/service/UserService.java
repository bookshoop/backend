package com.project.bookforeast.user.service;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;

public interface UserService {
	public UserDTO socialLogin(UserDTO userDTO);

	public UserDTO getUserDataInParameter(Map<String, Object> requestParam);

	public UserDTO signUp(UserDTO userDTO, String string, MultipartFile profile);

	public UserDTO login(UserDTO userDTO);

	public UserDTO getUserInfoByUsingRefreshToken(String refreshToken);

}
