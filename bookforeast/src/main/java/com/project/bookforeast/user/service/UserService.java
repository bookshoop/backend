package com.project.bookforeast.user.service;


import com.project.bookforeast.readBook.dto.MonthlyReadDTO;
import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.dto.UserInfoDTO;
import com.project.bookforeast.user.dto.UserUpdDTO;



public interface UserService {
	public UserDTO socialLogin(SocialLoginDTO userCreateDTO);

	public UserInfoDTO getUserInfo(String accessToken);

	public UserDTO getUserInfoByUsingRefreshToken(String refreshToken);

	public MonthlyReadDTO getMonthlyReadInfo(String accessToken);

	public void updUserInfo(String accessToken, UserUpdDTO userUpdDTO);


//	public UserDTO getUserDataInParameter(Map<String, Object> requestParam);
//
//	public UserDTO signUp(UserDTO userDTO, String string, MultipartFile profile);
//
//	public UserDTO login(UserDTO userDTO);


}
