package com.project.bookforeast.user.service;


import com.project.bookforeast.user.dto.UserDTO;

public interface UserService {
	public UserDTO socialLogin(UserDTO.SocialLoginDTO userCreateDTO);


//	public UserDTO getUserDataInParameter(Map<String, Object> requestParam);
//
//	public UserDTO signUp(UserDTO userDTO, String string, MultipartFile profile);
//
//	public UserDTO login(UserDTO userDTO);


}
