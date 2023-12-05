package com.project.bookforeast.user.repository;


import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.user.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

	public User findBySocialIdAndSocialProvider(String socialId, String socialProvider);
	
	public User findByNickname(String nickname);

	@Query("SELECT u FROM User u WHERE u.nickname LIKE %:nickname%")
	public List<User> findByNicknameContaining(String nickname);
	
	public User findByMobileAndSocialIdIsNull(String mobile);

	public User findByMobileAndPassword(String mobile, String password);
	
	public User findByRefreshToken(String refreshToken);

	public void deleteById(Long userId);
}
