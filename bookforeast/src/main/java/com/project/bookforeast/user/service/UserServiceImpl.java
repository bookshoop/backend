package com.project.bookforeast.user.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.common.security.error.TokenErrorResult;
import com.project.bookforeast.common.security.error.TokenException;
import com.project.bookforeast.common.security.role.UserRole;
import com.project.bookforeast.common.security.service.JwtUtil;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.file.service.FileService;
import com.project.bookforeast.genre.dto.LikeGenreDTO;
import com.project.bookforeast.genre.entity.LikeGenre;
import com.project.bookforeast.genre.service.GenreService;
import com.project.bookforeast.readBook.dto.MonthlyReadDTO;
import com.project.bookforeast.user.dto.DetailUserInfoDTO;
import com.project.bookforeast.user.dto.SimpleUserInfoDTO;
import com.project.bookforeast.user.dto.SimpleUserInfoInterface;
import com.project.bookforeast.user.dto.SocialLoginDTO;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.dto.UserInfosDTO;
import com.project.bookforeast.user.dto.UserUpdDTO;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.error.UserErrorResult;
import com.project.bookforeast.user.error.UserException;
import com.project.bookforeast.user.repository.UserRepository;

import io.jsonwebtoken.Claims;


@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final SecurityService securityService;
	private final FileService fileService;
	private final NicknameService nicknameService;
	private final JwtUtil jwtUtil;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, 
						   SecurityService securityService,
						   FileService fileService, 
						   GenreService genreService, 
						   NicknameService nicknameService,
						   JwtUtil jwtUtil
						   ) 
	{
		this.userRepository = userRepository;
		this.securityService = securityService;
		this.fileService = fileService;
		this.nicknameService = nicknameService;
		this.jwtUtil = jwtUtil;
	}
	
	
	public UserDTO socialLogin(SocialLoginDTO userDTO) {
		User findUser = findUserBySocialIdAndSocialProvider(userDTO);
		
		if(findUser == null) {
			findUser = socialUserSave(userDTO);
		}
		
		return findUser.toDTO();
	}



	public User socialUserSave(SocialLoginDTO userDTO) {
		userDTO.setRole(UserRole.USER.getRole());
		setDefaultNickname(userDTO);
		
		User user = userRepository.save(userDTO.toEntity());
		
		return user;
	}



	private void setDefaultNickname(SocialLoginDTO userDTO) {
		String nickname = nicknameService.createNickname();
		userDTO.setNickname(nickname);
	}


	
	public User findUserBySocialIdAndSocialProvider(SocialLoginDTO userDTO) {
		String socialId = userDTO.getSocialId();
		String socialProvider = userDTO.getSocialProvider();
		User user = userRepository.findBySocialIdAndSocialProvider(socialId, socialProvider);
		
		if(user != null) {
			return user;
		} else {
			return null;
		}
	}

	
	public DetailUserInfoDTO getUserDetailInfo(String accessToken) {
		checkTokenExpired(accessToken);
		User user = findBySocialIdAndSocialProvider(accessToken);
		return user.toDetailUserInfoDTO();
	}

	
	public User getUserInfo(String accessToken) {
		checkTokenExpired(accessToken);
		User user = findBySocialIdAndSocialProvider(accessToken);
		return user;
	}


	public UserDTO getUserInfoByUsingRefreshToken(String refreshToken) {
		User user = userRepository.findByRefreshToken(refreshToken);	
		return user.toDTO();
	}


	@Override
	public MonthlyReadDTO getMonthlyReadInfo(String accessToken) {
		checkTokenExpired(accessToken);
		User user = findBySocialIdAndSocialProvider(accessToken);
		return null;
	}

	
	private void checkTokenExpired(String accessToken) {
		if(jwtUtil.checkTokenExpired(accessToken)) {
			throw new TokenException(TokenErrorResult.TOKEN_EXPIRED);
		}
	}
	


	@Override
	public void updUserInfo(String accessToken ,UserUpdDTO userUpdDTO) {
		User user = findBySocialIdAndSocialProvider(accessToken);
		
		MultipartFile profile = userUpdDTO.getProfile();
		String nickname = userUpdDTO.getNickname();
		Date birthday = userUpdDTO.getBirthday();
		List<Integer> likeGenreIdList = userUpdDTO.getLikeGenre();
		String mobile = userUpdDTO.getMobile();
		
		if(profile != null && profile.getSize() > 0) {
			FileGroup fileGroup = user.getFileGroup();
			fileService.deleteFiles(fileGroup);
			fileService.fileUpload(profile, fileGroup, "userprofile");
		}
		
		
		if(nickname != null) {
			user.setNickname(nickname);
		}
		
		
		if(birthday != null) {
			user.setBirthday(birthday);
		}
		
		if(likeGenreIdList != null && likeGenreIdList.size() > 0) {
			List<LikeGenre> likeGenreList = new ArrayList<>();
			for(int likeGenre : likeGenreIdList) {
				LikeGenreDTO likeGenreDTO = new LikeGenreDTO();
				likeGenreDTO.setRegistUserDTO(user.toDTO());

				CodeDTO codeDTO = new CodeDTO();
				codeDTO.setCodeId((long) likeGenre);
				likeGenreDTO.setCodeDTO(codeDTO);
				likeGenreList.add(likeGenreDTO.toEntity());
			}
			
			user.setLikeGenreList(likeGenreList);
		}
		
		if(mobile != null) {
			user.setMobile(mobile);
		}
		
		userRepository.save(user);
	}


	public User findBySocialIdAndSocialProvider(String accessToken) {
		final String socialIdInToken = jwtUtil.extractClaim(accessToken, Claims::getSubject);
		final String socialProviderInToken = jwtUtil.extractClaim(accessToken, Claims::getIssuer);
		User user = userRepository.findBySocialIdAndSocialProvider(socialIdInToken, socialProviderInToken);
		
		if(user == null) {
			throw new TokenException(TokenErrorResult.TOKEN_EXPIRED);
		}
		
		return user;
	}
	
	
	@Override
	public UserInfosDTO getUserInfos(String accessToken, int itemSize, String cursor, String searchValue) {
		User user = findBySocialIdAndSocialProvider(accessToken);
		UserInfosDTO userInfosDTO = new UserInfosDTO();
		Pageable page = PageRequest.of(0, itemSize);
		Page<SimpleUserInfoInterface> pagingUsers = null;
		List<SimpleUserInfoDTO> users = null;
		Boolean isCursorNotEmpty = cursor != null && !cursor.equals("");
		Boolean isSearchValueNotEmpty = searchValue != null && !searchValue.equals("");
		

		if(isCursorNotEmpty) {
			if(isSearchValueNotEmpty) {
				pagingUsers = userRepository.findEntitiesByCursorAndSearchValue(cursor, searchValue, user.getUserId(), page);
			} else {
				pagingUsers = userRepository.findEntitiesByCursor(cursor, user.getUserId(), page);
			}
		} else {
			if(isSearchValueNotEmpty) {
				pagingUsers = userRepository.findEntitiesBySearchValue(searchValue, user.getUserId(), page);
			} else {
				pagingUsers = userRepository.findEntities(user.getUserId(), page);
			}
		}
		
		users = pagingUsers.getContent().stream().map(SimpleUserInfoInterface::toSimpleUserInfoDTO).collect(Collectors.toList());

		userInfosDTO.setContent(users);
		userInfosDTO.setHasMore(pagingUsers.hasNext());
		return userInfosDTO;
	}


	@Override
	public User findUserInSecurityContext() {
		UserDTO userDTO = securityService.getUserInfoInSecurityContext();
		Optional<User> findUser = userRepository.findById(userDTO.getUserId());

		try {
			return findUser.get();
		} catch(NoSuchElementException e) {
			throw new UserException(UserErrorResult.USER_NOT_EXIST);
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
