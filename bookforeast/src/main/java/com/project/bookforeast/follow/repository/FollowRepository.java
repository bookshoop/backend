package com.project.bookforeast.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.follow.entity.Follow;
import com.project.bookforeast.user.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByFollowingUser(User followingUser);

}
