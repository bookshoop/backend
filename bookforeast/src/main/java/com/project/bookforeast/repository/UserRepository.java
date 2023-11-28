package com.project.bookforeast.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	public User findBySocialIdAndSocialProvider(String socialId, String socialProvider);
	
	public User findByNickname(String nickname);

	public User findByMobileAndSocialIdIsNull(String mobile);

	public User findByMobileAndPassword(String mobile, String password);
}
