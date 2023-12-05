package com.project.bookforeast.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.common.security.role.UserRole;
import com.project.bookforeast.file.service.FileService;
import com.project.bookforeast.genre.service.GenreService;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.error.UserErrorResult;
import com.project.bookforeast.user.error.UserException;
import com.project.bookforeast.user.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final FileService fileService;
	private final GenreService genreService;
	private final NicknameService nicknameService;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, 
						   FileService fileService, 
						   GenreService genreService, 
						   NicknameService nicknameService
						   ) 
	{
		this.userRepository = userRepository;
		this.fileService = fileService;
		this.genreService = genreService;
		this.nicknameService = nicknameService;
	}
	
	
	public UserDTO socialLogin(UserDTO userDTO) {
		User findUser = findUserBySocialIdAndSocialProvider(userDTO);
		
		if(findUser == null) {
			findUser = socialUserSave(userDTO);
		}
		
		return findUser.toDTO();
	}

	
	public User socialUserSave(UserDTO userDTO) {
		checkSocialUserSaveValid(userDTO);
		
		userDTO.setRole(UserRole.USER.getRole());
		setDefaultNickname(userDTO);
		
		User user = userRepository.save(userDTO.toEntity());
		
		return user;
	}


	private void checkSocialUserSaveValid(UserDTO userDTO) {
		if(userDTO == null) {
			throw new UserException(UserErrorResult.USEROBJECT_NOT_EXIST);
		}
		
		if(!checkSocialLoginNessaryValueExist(userDTO)) {
			throw new UserException(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
		}
		
		
		if(checkSocialUserAlreadyExist(userDTO)) {
			throw new UserException(UserErrorResult.DUPLICATED_USER_REGISTER);
		}
		
	}
	
	
	private boolean checkSocialLoginNessaryValueExist(UserDTO userDTO) {		
		if(userDTO.getSocialId() == null || "".equals(userDTO.getSocialId())) {
			return false;
		}
		
		if(userDTO.getSocialProvider() == null || "".equals(userDTO.getSocialProvider())) {
			return false;
		}
		
		return true;
	}
	
	
	private boolean checkSocialUserAlreadyExist(UserDTO userDTO) {
		String socialId = userDTO.getSocialId();
		String socialProvider = userDTO.getSocialProvider();
		
		User user = userRepository.findBySocialIdAndSocialProvider(socialId, socialProvider); 
		
		if(user != null) {
			return true;
		} else {
			return false;
		}
	}


	private void setDefaultNickname(UserDTO userDTO) {
		String nickname = nicknameService.createNickname();
		userDTO.setNickname(nickname);
	}


	
	public User findUserBySocialIdAndSocialProvider(UserDTO userDTO) {
		String socialId = userDTO.getSocialId();
		String socialProvider = userDTO.getSocialProvider();
		User user = userRepository.findBySocialIdAndSocialProvider(socialId, socialProvider);
		
		if(user != null) {
			return user;
		} else {
			return null;
		}
	}

	
//	public UserDTO signUp(UserDTO userDTO, String contentName, MultipartFile proFile) {
//		checkSignUpVaild(userDTO);
//		
//		userDTO.setRole(UserRole.USER.getRole());
//		setEncodedPassword(userDTO);
//		User user = userRepository.save(userDTO.toEntity()); 
//		
//		// 파일이 있고 컨텐츠 name이 있는 경우 파일 등록서비스 호출
//		if(proFile != null && contentName != null && !"".equals(contentName)) {
//			MultipartFile[] fileArray = makeFileToArray(proFile);
//			fileService.fileUpload(fileArray, contentName);
//		}
//		
//		// userDTO 객체 안에 like genre가 있는 경우 like genre 저장 서비스 호출
//		List<Long> likeGenreCodeIds = userDTO.getLikeGenres();
//		if(likeGenreCodeIds != null && likeGenreCodeIds.size() > 0) {
//			genreService.saveLikeGenres(user, likeGenreCodeIds);
//		}
//		
//		return user.toDTO();
//	}
//
// 
//
//	private void setEncodedPassword(UserDTO userDTO) {
//		String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
//		userDTO.setPassword(encodedPassword);
//	}
//
//
//	private void checkSignUpVaild(UserDTO userDTO) {
//		if(userDTO == null) {
//			throw new UserException(UserErrorResult.USEROBJECT_NOT_EXIST);
//		}		
//		
//		if(!checkSignUpNessaryValueExist(userDTO)) {
//			throw new UserException(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
//		}
//		
//		if(!checkPasswordValid(userDTO.getPassword())) {
//			throw new UserException(UserErrorResult.INVALID_PASSWORD);
//		}
//		
//		if(checkUserAreadyExist(userDTO)) {
//			throw new UserException(UserErrorResult.DUPLICATED_USER_REGISTER);
//		}
//		
//		if(userRepository.findByNickname(userDTO.getNickname()) != null) {
//			throw new UserException(UserErrorResult.ALREADY_USED_NICKNAME);
//		}
//	}
//
//
//
//
//	private boolean checkPasswordValid(String password) {
//		// 비밀번호 길이 확인
//		if(password.length() < 8) {
//			return false;
//		}
//		// 대소문자 포함여부 
//		Boolean isUppserCaseContains = !password.equals(password.toLowerCase());
//		Boolean isLowerCaseContains = !password.equals(password.toUpperCase());
//		
//		if(!isUppserCaseContains || !isLowerCaseContains) {
//			return false;
//		}
//		
//		// 특수문자 포함 여부 확인
//		 Pattern specialCharacterPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]+");
//	     Matcher matcher = specialCharacterPattern.matcher(password);
//	     if(!matcher.find()) {
//	    	 return false;
//	     }
//	     		
//		return true;
//	}
//
//
//	private boolean checkSignUpNessaryValueExist(UserDTO userDTO) {
//		if(userDTO.getNickname() == null || "".equals(userDTO.getNickname())) {
//			return false;
//		}
//		
//		if(userDTO.getMobile() == null || "".equals(userDTO.getMobile())) {
//			return false;
//		}
//		
//		if(userDTO.getPassword() == null || "".equals(userDTO.getPassword())) {
//			return false;
//		}
//		
//		return true;
//	}
//
//	
//	private boolean checkUserAreadyExist(UserDTO userDTO) {
//		String mobile = userDTO.getMobile();
//		String password = userDTO.getPassword();
//		
//		User findUser = userRepository.findByMobileAndSocialIdIsNull(mobile);
//		
//		if(findUser != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//
//	private MultipartFile[] makeFileToArray(MultipartFile proFile) {
//		MultipartFile[] fileArray = new MultipartFile[]{proFile};
//		return fileArray;
//	}
//
//	

//
//	@Override
//	public UserDTO login(UserDTO userDTO) {
//		checkLoginValue(userDTO);
//		
//		User findUser = userRepository.findByMobileAndSocialIdIsNull(userDTO.getMobile());		
//		if(findUser == null) {
//			throw new UserException(UserErrorResult.USER_NOT_EXIST);
//		}
//
//		boolean isPasswordEqual = passwordEncoder.matches(userDTO.getPassword(), findUser.getPassword());
//		if(!isPasswordEqual) {
//			throw new UserException(UserErrorResult.USER_NOT_EXIST);
//		}
//		
//		return findUser.toDTO();
//	}
//
//
//
//	private void checkLoginValue(UserDTO userDTO) {
//		boolean isMobileEmpty = userDTO.getMobile() == null || "".equals(userDTO.getMobile());
//		boolean isPasswordEmpty = userDTO.getPassword() == null || "".equals(userDTO.getPassword());
//		
//		if(isMobileEmpty) {
//			throw new UserException(UserErrorResult.MOBILE_EMPTY);
//		}
//		
//		if(isPasswordEmpty) {
//			throw new UserException(UserErrorResult.PASSWORD_EMPTY);
//		}
//		
//	}
//

}
