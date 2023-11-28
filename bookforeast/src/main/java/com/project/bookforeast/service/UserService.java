package com.project.bookforeast.service;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.project.bookforeast.dto.UserDTO;

public interface UserService {
	public UserDTO socialLogin(UserDTO userDTO, String string, MultipartFile proFile);

	public UserDTO getUserDataInParameter(Map<String, Object> requestParam);

	public UserDTO signUp(UserDTO userDTO, String string, MultipartFile profile);

	public UserDTO login(UserDTO userDTO);
}
