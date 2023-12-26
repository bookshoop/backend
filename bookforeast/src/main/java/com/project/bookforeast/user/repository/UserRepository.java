package com.project.bookforeast.user.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bookforeast.user.dto.SimpleUserInfoInterface;
import com.project.bookforeast.user.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {


	public User findBySocialIdAndSocialProvider(String socialId, String socialProvider);
	
	public User findByNickname(String nickname);

	public List<User> findByNicknameContaining(String nickname);
	
	public User findByMobileAndSocialIdIsNull(String mobile);

	public User findByMobileAndPassword(String mobile, String password);
	
	public User findByRefreshToken(String refreshToken);

	public void deleteById(Long userId);


	

	@Query(value =
		    "SELECT " +
		        "U.USER_ID AS ID, " +
		        "U.NICKNAME, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWING_ID = U.USER_ID), 0) AS FOLLOWER_COUNT, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWER_ID = U.USER_ID), 0) AS FOLLOWING_COUNT, " +
		        "F.FILE_ID, " +
		        "CONCAT(F.PATH, '/', F.NAME) AS PATH, " +
		        "F.EXTENSION, " +
		        "CASE WHEN (SELECT COUNT(1) FROM USER U1 JOIN FOLLOW F1 ON U1.USER_ID = F1.FOLLOWING_ID WHERE :userId = F1.FOLLOWER_ID) = 1 THEN true ELSE false END AS IS_FOLLOW_CNT, " +
		        "CONCAT(LPAD(DATE_FORMAT(U.REGIST_DT, '%Y%m%d%H%i%s'), 10, '0'), LPAD(U.USER_ID, 10, '0')) AS CS " + 
		    "FROM USER U " +
		    "LEFT OUTER JOIN FILE_GROUP FG ON U.PROFILE_ID = FG.FILEGROUP_ID " +
		    "LEFT OUTER JOIN FILE F ON FG.FILEGROUP_ID = F.FILEGROUP_ID",
		    nativeQuery = true)
	public Page<SimpleUserInfoInterface> findEntities(Long userId, Pageable page);

	
	@Query(value =
		    "SELECT " +
		        "U.USER_ID AS ID, " +
		        "U.NICKNAME, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWING_ID = U.USER_ID), 0) AS FOLLOWER_COUNT, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWER_ID = U.USER_ID), 0) AS FOLLOWING_COUNT, " +
		        "F.FILE_ID, " +
		        "CONCAT(F.PATH, '/', F.NAME) AS PATH, " +
		        "F.EXTENSION, " +
		        "CASE WHEN (SELECT COUNT(1) FROM USER U1 JOIN FOLLOW F1 ON U1.USER_ID = F1.FOLLOWING_ID WHERE :userId = F1.FOLLOWER_ID) = 1 THEN true ELSE false END AS IS_FOLLOW_CNT, " +
		        "CONCAT(LPAD(DATE_FORMAT(U.REGIST_DT, '%Y%m%d%H%i%s'), 10, '0'), LPAD(U.USER_ID, 10, '0')) AS CS " + 
		    "FROM USER U " +
		    "LEFT OUTER JOIN FILE_GROUP FG ON U.PROFILE_ID = FG.FILEGROUP_ID " +
		    "LEFT OUTER JOIN FILE F ON FG.FILEGROUP_ID = F.FILEGROUP_ID" +
		    "WHERE CONCAT(LPAD(DATE_FORMAT(U.REGIST_DT, '%Y%m%d%H%i%s'), 10, '0'), LPAD(U.USER_ID, 10, '0')) >= :cursor",
		    nativeQuery = true)
	public Page<SimpleUserInfoInterface> findEntitiesByCursor(String cursor, Long userId, Pageable page);

	

	@Query(value =
		    "SELECT " +
		        "U.USER_ID AS ID, " +
		        "U.NICKNAME, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWING_ID = U.USER_ID), 0) AS FOLLOWER_COUNT, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWER_ID = U.USER_ID), 0) AS FOLLOWING_COUNT, " +
		        "F.FILE_ID, " +
		        "CONCAT(F.PATH, '/', F.NAME) AS PATH, " +
		        "F.EXTENSION, " +
		        "CASE WHEN (SELECT COUNT(1) FROM USER U1 JOIN FOLLOW F1 ON U1.USER_ID = F1.FOLLOWING_ID WHERE :userId = F1.FOLLOWER_ID) = 1 THEN true ELSE false END AS IS_FOLLOW_CNT, " +
		        "CONCAT(LPAD(LENGTH(U.NICKNAME), 10, '0'), LPAD(U.USER_ID, 10, '0')) AS CS " + 
		    "FROM USER U " +
		    "LEFT OUTER JOIN FILE_GROUP FG ON U.PROFILE_ID = FG.FILEGROUP_ID " +
		    "LEFT OUTER JOIN FILE F ON FG.FILEGROUP_ID = F.FILEGROUP_ID " +
		    "WHERE U.NICKNAME LIKE '%' || :searchValue || '%' ",
		    nativeQuery = true)
	public Page<SimpleUserInfoInterface> findEntitiesBySearchValue(String searchValue, Long userId, Pageable page);
	
	
	@Query(value =
		    "SELECT " +
		        "U.USER_ID AS ID, " +
		        "U.NICKNAME, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWING_ID = U.USER_ID), 0) AS FOLLOWER_COUNT, " +
		        "IFNULL((SELECT COUNT(*) FROM FOLLOW WHERE FOLLOWER_ID = U.USER_ID), 0) AS FOLLOWING_COUNT, " +
		        "F.FILE_ID, " +
		        "CONCAT(F.PATH, '/', F.NAME) AS PATH, " +
		        "F.EXTENSION, " +
		        "CASE WHEN (SELECT COUNT(1) FROM USER U1 JOIN FOLLOW F1 ON U1.USER_ID = F1.FOLLOWING_ID WHERE :userId = F1.FOLLOWER_ID) = 1 THEN true ELSE false END AS IS_FOLLOW_CNT, " +
		        "CONCAT(LPAD(LENGTH(U.NICKNAME), 10, '0'), LPAD(U.USER_ID, 10, '0')) AS CS " + 
		    "FROM USER U " +
		    "LEFT OUTER JOIN FILE_GROUP FG ON U.PROFILE_ID = FG.FILEGROUP_ID " +
		    "LEFT OUTER JOIN FILE F ON FG.FILEGROUP_ID = F.FILEGROUP_ID" + 
		    "WHERE U.NICKNAME LIKE '%' || :searchValue || '%' " +
		    "  AND CONCAT(LPAD(LENGTH(U.NICKNAME), 10, '0'), LPAD(U.USER_ID, 10, '0')) > :cursor",
		    nativeQuery = true)
	public Page<SimpleUserInfoInterface> findEntitiesByCursorAndSearchValue(String cursor, String searchValue, Long userId, Pageable page);
	

	
	
	
	
}
