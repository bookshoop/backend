package com.project.bookforeast.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.code.entity.Code;
import com.project.bookforeast.common.domain.error.ParsingErrorResult;
import com.project.bookforeast.common.domain.error.ParsingException;
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
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, FileService fileService, GenreService genreService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.fileService = fileService;
		this.genreService = genreService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public UserDTO socialLogin(UserDTO userDTO) {
		User findUser = findUserBySocialIdAndSocialProvider(userDTO);
		
		if(findUser == null) {
			findUser = socialUserSave(userDTO);
		}
		
		return findUser.toDTO();
	}
//	
//	public User socialLogin(UserDTO userDTO) {
//		User findUser = findUserBySocialIdAndSocialProvider(userDTO);
//		
//		if(findUser == null) {
//			checkSocialUserSaveValid(userDTO);
//			userDTO.setRole(UserRole.USER.getRole());
//			findUser = userRepository.save(userDTO.toEntity()); 
//		}
//		
//		return findUser;
//	}
	
	public User socialUserSave(UserDTO userDTO) {
		checkSocialUserSaveValid(userDTO);
		
		userDTO.setRole(UserRole.USER.getRole());
		setDefaultNickname(userDTO);
		
		User user = userRepository.save(userDTO.toEntity()); 
		
		// default프로필 사진 등록
		
		return user;
	}



	private void setDefaultNickname(UserDTO userDTO) {
		
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

	
	
	public UserDTO signUp(UserDTO userDTO, String contentName, MultipartFile proFile) {
		checkSignUpVaild(userDTO);
		
		userDTO.setRole(UserRole.USER.getRole());
		setEncodedPassword(userDTO);
		User user = userRepository.save(userDTO.toEntity()); 
		
		// 파일이 있고 컨텐츠 name이 있는 경우 파일 등록서비스 호출
		if(proFile != null && contentName != null && !"".equals(contentName)) {
			MultipartFile[] fileArray = makeFileToArray(proFile);
			fileService.fileUpload(fileArray, contentName);
		}
		
		// userDTO 객체 안에 like genre가 있는 경우 like genre 저장 서비스 호출
		List<Long> likeGenreCodeIds = userDTO.getLikeGenres();
		if(likeGenreCodeIds != null && likeGenreCodeIds.size() > 0) {
			genreService.saveLikeGenres(user, likeGenreCodeIds);
		}
		
		return user.toDTO();
	}

 

	private void setEncodedPassword(UserDTO userDTO) {
		String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(encodedPassword);
	}


	private void checkSignUpVaild(UserDTO userDTO) {
		if(userDTO == null) {
			throw new UserException(UserErrorResult.USEROBJECT_NOT_EXIST);
		}		
		
		if(!checkSignUpNessaryValueExist(userDTO)) {
			throw new UserException(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
		}
		
		if(!checkPasswordValid(userDTO.getPassword())) {
			throw new UserException(UserErrorResult.INVALID_PASSWORD);
		}
		
		if(checkUserAreadyExist(userDTO)) {
			throw new UserException(UserErrorResult.DUPLICATED_USER_REGISTER);
		}
		
		if(userRepository.findByNickname(userDTO.getNickname()) != null) {
			throw new UserException(UserErrorResult.ALREADY_USED_NICKNAME);
		}
	}




	private boolean checkPasswordValid(String password) {
		// 비밀번호 길이 확인
		if(password.length() < 8) {
			return false;
		}
		// 대소문자 포함여부 
		Boolean isUppserCaseContains = !password.equals(password.toLowerCase());
		Boolean isLowerCaseContains = !password.equals(password.toUpperCase());
		
		if(!isUppserCaseContains || !isLowerCaseContains) {
			return false;
		}
		
		// 특수문자 포함 여부 확인
		 Pattern specialCharacterPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]+");
	     Matcher matcher = specialCharacterPattern.matcher(password);
	     if(!matcher.find()) {
	    	 return false;
	     }
	     		
		return true;
	}


	private boolean checkSignUpNessaryValueExist(UserDTO userDTO) {
		if(userDTO.getNickname() == null || "".equals(userDTO.getNickname())) {
			return false;
		}
		
		if(userDTO.getMobile() == null || "".equals(userDTO.getMobile())) {
			return false;
		}
		
		if(userDTO.getPassword() == null || "".equals(userDTO.getPassword())) {
			return false;
		}
		
		return true;
	}

	
	private boolean checkUserAreadyExist(UserDTO userDTO) {
		String mobile = userDTO.getMobile();
		String password = userDTO.getPassword();
		
		User findUser = userRepository.findByMobileAndSocialIdIsNull(mobile);
		
		if(findUser != null) {
			return true;
		} else {
			return false;
		}
	}


	private MultipartFile[] makeFileToArray(MultipartFile proFile) {
		MultipartFile[] fileArray = new MultipartFile[]{proFile};
		return fileArray;
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


	@Override
	public UserDTO getUserDataInParameter(Map<String, Object> requestParam) {
		UserDTO userDTO = new UserDTO();
		
		
		if(requestParam.get("nickName") != null) {
			userDTO.setNickname((String)requestParam.get("nickname"));
		}
		
		if(requestParam.get("mobile") != null) {
			userDTO.setMobile((String) requestParam.get("mobile"));
		}
		
		if(requestParam.get("password") != null) {
			userDTO.setPassword((String) requestParam.get("password"));
		}
		
		if(requestParam.get("birthday") != null) {
			Date birthday = parsingObjectToDate(requestParam.get("birthday"));
			userDTO.setBirthday(birthday);
		}
		
		if(requestParam.get("accessRoute") != null) {
			userDTO.setAccessRoute((String) requestParam.get("accessRoute"));
		}
		
		if(requestParam.get("accessRouteId") != null) {
			Long accessRouteId = (Long) requestParam.get("accessRouteId");
			CodeDTO codeDTO = new CodeDTO();
			codeDTO.setCodeId(accessRouteId);
			
			userDTO.setCodeDTO(codeDTO);
		}
		
		if(requestParam.get("socialProvider") != null) {
			userDTO.setSocialProvider((String) requestParam.get("socialProvider"));
		}
		
		if(requestParam.get("socialId") != null) {
			userDTO.setSocialId((String) requestParam.get("socialId"));
		}
		
		if(requestParam.get("likeGenres") != null) {
			String likeGenreCodes = (String) requestParam.get("likeGenres");
			
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				List<Long> likeGenreCodeList = objectMapper.readValue(likeGenreCodes, new TypeReference<List<Long>>(){});
				userDTO.setLikeGenres(likeGenreCodeList);
			} catch (JsonProcessingException e) {
				new ParsingException(ParsingErrorResult.PARSING_FAIL);
			}
		}
		
		if(requestParam.get("pushToken") != null ) {
			userDTO.setPushToken((String) requestParam.get("pushToken"));
		}
		
		if(requestParam.get("refreshToken") != null ) {
			userDTO.setPushToken((String) requestParam.get("refreshToken"));
		}
		
		return userDTO;
	}



	private Date parsingObjectToDate(Object birthdayObject) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String birthdayStr = (String) birthdayObject;
		Date birthday = null;
		try {
			birthday = dateFormat.parse(birthdayStr);
		} catch (ParseException e) {
			e.printStackTrace();
			new RuntimeException("날짜 형식이 올바르지 않습니다.");
		}
		return birthday;
	}


	@Override
	public UserDTO login(UserDTO userDTO) {
		checkLoginValue(userDTO);
		
		User findUser = userRepository.findByMobileAndSocialIdIsNull(userDTO.getMobile());		
		if(findUser == null) {
			throw new UserException(UserErrorResult.USER_NOT_EXIST);
		}

		boolean isPasswordEqual = passwordEncoder.matches(userDTO.getPassword(), findUser.getPassword());
		if(!isPasswordEqual) {
			throw new UserException(UserErrorResult.USER_NOT_EXIST);
		}
		
		return findUser.toDTO();
	}



	private void checkLoginValue(UserDTO userDTO) {
		boolean isMobileEmpty = userDTO.getMobile() == null || "".equals(userDTO.getMobile());
		boolean isPasswordEmpty = userDTO.getPassword() == null || "".equals(userDTO.getPassword());
		
		if(isMobileEmpty) {
			throw new UserException(UserErrorResult.MOBILE_EMPTY);
		}
		
		if(isPasswordEmpty) {
			throw new UserException(UserErrorResult.PASSWORD_EMPTY);
		}
		
	}


	@Override
	public UserDTO getUserInfoByUsingRefreshToken(String refreshToken) {
		User user = userRepository.findByRefreshToken(refreshToken);	
		return user.toDTO();
	}


}
