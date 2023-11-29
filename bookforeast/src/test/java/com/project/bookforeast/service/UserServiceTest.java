package com.project.bookforeast.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.dto.UserDTO;
import com.project.bookforeast.entity.User;
import com.project.bookforeast.error.UserException;
import com.project.bookforeast.error.result.UserErrorResult;
import com.project.bookforeast.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private FileService fileService;
	
	@Mock
	private GenreService genreService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	

	

	@Test
	@DisplayName("소셜 유저 등록 및 로그인 성공")
	public void userSaveSuccess_Social() {
		// given
		UserDTO userDTO = userDTOMaker();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		
		// repository.save실행됐을 때 userDTO return하도록 설정(repository메소드는 mock이라서 실행안됨)
		doReturn(userDTO.toEntity()).when(userRepository).save(any(User.class));
		doReturn(true).when(genreService).saveLikeGenres(any(User.class), any(List.class));
		doReturn(true).when(fileService).fileUpload(any(MultipartFile[].class), eq("testContentName"));
		
		// when
		UserDTO savedUser = userService.socialLogin(userDTO, "testContentName", mockFile);
		
		// then
		assertEquals(savedUser.getNickname(), userDTO.getNickname());
		
	}
	
	@Test
	@DisplayName("소셜 유저 등록 성공")
	public void userSaveSuccess() {
		// given
		UserDTO userDTO = userDTOMaker();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		List<Long> likeGenres = makeMockLikeGenres();
		userDTO.setLikeGenres(likeGenres);
		
		doReturn(userDTO.toEntity()).when(userRepository).findByNickname(userDTO.getNickname());
		doReturn(null).when(userRepository).findBySocialIdAndSocialProvider(userDTO.getSocialId(), userDTO.getSocialProvider());
		doReturn(null).when(userRepository).findByNickname(userDTO.getNickname());		
		doReturn(userDTO.toEntity()).when(userRepository).save(any(User.class));
		doReturn(true).when(genreService).saveLikeGenres(any(User.class), any(List.class));
		doReturn(true).when(fileService).fileUpload(any(MultipartFile[].class), eq("testContentName"));
		
		
		// when
 		final User result = userService.socialUserSave(userDTO, "testContentName", mockFile);
		
		// then
		assertThat(result.getSocialId()).isEqualTo(userDTO.getSocialId());
		assertThat(result.getSocialProvider()).isEqualTo(userDTO.getSocialProvider());
	}	

	
	
	
	@Test
	@DisplayName("유저 등록 실패 - 유저 객체가 없는 경우")
	public void userSaveFailUserObjectNotExist() {
		// given
		UserDTO userDTO = null;
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		
		// when
		final UserException result1 = assertThrows(UserException.class, () -> userService.socialUserSave(userDTO, "testContent", mockFile));
		final UserException result2 = assertThrows(UserException.class, () -> userService.signUp(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result1.getUserErrorResult()).isEqualTo(UserErrorResult.USEROBJECT_NOT_EXIST);
		assertThat(result2.getUserErrorResult()).isEqualTo(UserErrorResult.USEROBJECT_NOT_EXIST);
	}

	
	
	@Test
	@DisplayName("소셜 유저 등록 실패 - 필수값이 존재하지 않는 경우")
	public void userSaveFailNessaryValueNotExust() {
		// given
		UserDTO userDTO = new UserDTO();
		MultipartFile mockFile = null;
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.socialUserSave(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
	}
	
	
	@Test
	@DisplayName("소셜 유저 등록 실패 - 로그인 시 필수값이 없는 경우")
	public void userSaveFail_SocialValueNotExist() {
		// given
		UserDTO userDTO = new UserDTO();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		
		userDTO.setNickname("testNickName");
		userDTO.setMobile("01012345678");
		
		// when 
		final UserException result = assertThrows(UserException.class, () -> userService.socialUserSave(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
	}
	
	
	@Test
	@DisplayName("소셜 유저 등록 실패 - 이미 소설회원가입 한 경우")
	public void userSaveFail_socialAlreadyExist() {
		// given
		UserDTO userDTO = userDTOMaker();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		doReturn(userDTO.toEntity()).when(userRepository).findBySocialIdAndSocialProvider(userDTO.getSocialId(), userDTO.getSocialProvider());
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.socialUserSave(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.DUPLICATED_USER_REGISTER);
	}
	

	@Test
	@DisplayName("소셜 유저 등록 실패 - 이미 있는 닉네임의 경우")
	public void userSaveFail_nickNameAlreadyExist() {
		// given
		UserDTO userDTO = userDTOMaker();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		doReturn(User.builder().build()).when(userRepository).findByNickname(userDTO.getNickname());

		// when
		final UserException result = assertThrows(UserException.class, () -> userService.socialUserSave(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.ALREADY_USED_NICKNAME);
	}
	
	
	@Test
	@DisplayName("소셜 아이디로 유저 찾기 - 유저 있는 경우")
	public void findSocialUserSuccess() {
		// given
		UserDTO userDTO = userDTOMaker();
		String socialId = userDTO.getSocialId();
		String socialProvider = userDTO.getSocialProvider();
		doReturn(userDTO.toEntity()).when(userRepository).findBySocialIdAndSocialProvider(socialId, socialProvider);
		
		// when
		final User result = userService.findUserBySocialIdAndSocialProvider(userDTO);
		
		// then
		assertEquals(userDTO.getSocialId(), result.getSocialId());
	}

	
	@Test
	@DisplayName("소셜 아이디로 유저 착기 - 유저 없는 경우")
	public void findSocialUserNotExist() {
		// given
		UserDTO userDTO = userDTOMaker();
		String socialId = userDTO.getSocialId();
		String socialProvider = userDTO.getSocialProvider();
		doReturn(null).when(userRepository).findBySocialIdAndSocialProvider(socialId, socialProvider);		

		// when
		final User result = userService.findUserBySocialIdAndSocialProvider(userDTO);
		
		// then
		assertEquals(result, null);
	}
	                         
	
	
	@Test
	@DisplayName("유저 등록 실패 - 필수값이 없는 경우")
	public void userSaveFailNecessaryValueNotExist() {
		// given
		UserDTO userDTO = new UserDTO();
		MultipartFile mockFile = new MockMultipartFile("file1", "test1.txt", "text/plain", "testFile".getBytes());
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.signUp(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.NECESSARY_VALUE_NOTEXIST);
	}
	
	
	
	@Test
	@DisplayName("유저 등록 실패 - 이미 유저가 존재하는 경우")
	public void userSaveFailAlreadyExist() {
		// given
		UserDTO userDTO = userDTOMakerExceptSocialValues();
		String mobile = userDTO.getMobile();
		String password = userDTO.getPassword();
		MultipartFile mockFile = null;
		doReturn(userDTO.toEntity()).when(userRepository).findByMobileAndSocialIdIsNull(mobile);
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.signUp(userDTO, "testContent", mockFile));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.DUPLICATED_USER_REGISTER);
	}
	
	
	
	@Test
	@DisplayName("유저 등록 실패 - 적절하지 않은 패스워드인 경우")
	public void userSaveFailInvalidPassword() {
		// given
		UserDTO userDTO = userDTOMakerExceptSocialValues();
		String password = "invalidPassword";
		userDTO.setPassword(password);
		doReturn(userDTO.toEntity()).when(userRepository).save(userDTO.toEntity());
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.signUp(userDTO, "testContent", null));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.INVALID_PASSWORD);
	}
	
	@Test
	@DisplayName("유저 로그인 실패 - mobile존재하지 않은 경우")
	public void userLoginFailMobileNotExist() {
		// given
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("testpassword");
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.login(userDTO));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.MOBILE_EMPTY);
	}
	
	
	@Test
	@DisplayName("유저 로그인 실패 - password 존재하지 않은 경우")
	public void userLoginFailPasswordNotExist() {
		// given
		UserDTO userDTO = new UserDTO();
		userDTO.setMobile("01012345678");
		userDTO.setPassword("");
		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.login(userDTO));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.PASSWORD_EMPTY);
	}
	
	
	@Test
	@DisplayName("유저 로그인 실패 - 유저 존재하지 않은 경우")
	public void userLogiFailUserNotExist() {
		// given
		UserDTO userDTO = userDTOMakerExceptSocialValues();
		doReturn(null).when(userRepository).findByMobileAndPassword(userDTO.getMobile(), userDTO.getPassword());		

		
		// when
		final UserException result = assertThrows(UserException.class, () -> userService.login(userDTO));
		
		// then
		assertThat(result.getUserErrorResult()).isEqualTo(UserErrorResult.USER_NOT_EXIST);
	}
	

	@Test
	@DisplayName("유저 로그인 성공")
	public void userLoginSuccess() {
		// given
		UserDTO userDTO = userDTOMakerExceptSocialValues();
		doReturn(userDTO.toEntity()).when(userRepository).findByMobileAndSocialIdIsNull(userDTO.getMobile());
		doReturn(true).when(passwordEncoder).matches(userDTO.getPassword(), userDTO.getPassword());
		
		// when
		UserDTO findUser = userService.login(userDTO);
		
		// then
		assertThat(userDTO.getMobile()).isEqualTo(findUser.getMobile());
	}
	
	
	private UserDTO userDTOMaker() {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setNickname("testNickName");
		userDTO.setMobile("01012345678");
		userDTO.setSocialId("testSocialId");
		userDTO.setSocialProvider("KAKAO");
		
		return userDTO;
	}

	
	private UserDTO userDTOMakerExceptSocialValues() {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setNickname("testNickName");
		userDTO.setPassword("testPassword123&");
		userDTO.setMobile("01012345678");
		
		return userDTO;
	}
	
	private List<Long> makeMockLikeGenres() {
		List<Long> likeGenres = new ArrayList<>();
		likeGenres.add(1L);
		likeGenres.add(2L);
		likeGenres.add(3L);
		
		return likeGenres;
	}
}
