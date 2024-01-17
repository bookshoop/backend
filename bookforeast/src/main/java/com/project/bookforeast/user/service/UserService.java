package com.project.bookforeast.user.service;


import com.project.bookforeast.readBook.dto.MonthlyReadDTO;
import com.project.bookforeast.user.dto.DetailUserInfoDTO;
import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.dto.UserInfosDTO;
import com.project.bookforeast.user.dto.UserUpdDTO;
import com.project.bookforeast.user.entity.User;



public interface UserService {
	public UserDTO socialLogin(SocialLoginDTO userCreateDTO);

	public DetailUserInfoDTO getUserDetailInfo(String accessToken);

	public User getUserInfo(String accessToken);

	public UserDTO getUserInfoByUsingRefreshToken(String refreshToken);

	public MonthlyReadDTO getMonthlyReadInfo(String accessToken);

	public void updUserInfo(String accessToken, UserUpdDTO userUpdDTO);

	public UserInfosDTO getUserInfos(String accessToken, int itemSize, String cursor, String searchValue);

	public User findBySocialIdAndSocialProvider(String accessToken);

	public User findUserInSecurityContext();
//	public UserDTO getUserDataInParameter(Map<String, Object> requestParam);
//
//	public UserDTO signUp(UserDTO userDTO, String string, MultipartFile profile);
//
//	public UserDTO login(UserDTO userDTO);


}
