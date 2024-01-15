package com.project.bookforeast.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.bookforeast.bookTree.entity.BookTree;
import com.project.bookforeast.code.entity.Code;
import com.project.bookforeast.file.dto.ProfileDTO;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.follow.entity.Follow;
import com.project.bookforeast.genre.dto.SimpleLikeGenreDTO;
import com.project.bookforeast.genre.entity.LikeGenre;
import com.project.bookforeast.readBook.entity.ReadBook;
import com.project.bookforeast.user.dto.DetailUserInfoDTO;
import com.project.bookforeast.user.dto.SimpleUserInfoInterface;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.wishList.entity.WishList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String nickname;
	private String mobile;
	private String password;
	private Date birthday;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "access_route_id")
	private Code code;
	
	private String accessRoute;
	private String socialProvider;
	private String socialId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private LocalDateTime updateDt;
	
	private LocalDateTime deleteDt;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private FileGroup fileGroup;
	
	private String role;
	private String pushToken;
	private String refreshToken;
	
	@OneToMany(mappedBy = "followingUser")
	private List<Follow> followingUserList;
	
	@OneToMany(mappedBy = "followerUser")
	private List<Follow> followerUserList;
	
	@OneToMany(mappedBy = "registUser")
	private List<WishList> wishLists;
	
	@OneToMany(mappedBy = "registUser")
	private List<ReadBook> readBookList;
	
	@OneToMany(mappedBy = "registUser")
	private List<BookTree> bookTreeList;
	
	@OneToMany(mappedBy = "registUser")
	private List<LikeGenre> likeGenreList;
	
	public UserDTO toDTO() {
		
		UserDTO.UserDTOBuilder builder = UserDTO.builder()
												.userId(userId)
												.nickname(nickname)
												.mobile(mobile)
												.password(password)
												.birthday(birthday)													
												.socialProvider(socialProvider)
												.socialId(socialId)
												.registDt(registDt)
												.updateDt(updateDt)
												.deleteDt(deleteDt)
												.role(role)
												.pushToken(pushToken)
												.refreshToken(refreshToken)
												; 
	
		if(code != null) {
			builder.codeDTO(code.toDTO());
		}
		
		if(fileGroup != null) {
			builder.fileGroupDTO(fileGroup.toDTO());
		}
		
		return builder.build();				
	}
	
	
	public DetailUserInfoDTO toDetailUserInfoDTO() {
		DetailUserInfoDTO.DetailUserInfoDTOBuilder builder = DetailUserInfoDTO.builder();
		
		builder.id(userId)
						  .nickname(nickname)
						  .mobile(mobile)
						  .birthday(birthday);
		
		if(fileGroup != null) {
			File file = fileGroup.getFileList().get(0);
			ProfileDTO profile = new ProfileDTO();
			profile.setFileId(file.getFileId());
			profile.setExtension(file.getExtension());
			profile.setPath(file.getPath() + "/" + file.getName());
			builder.profile(profile);
		}
		
		if(followerUserList != null && followerUserList.size() > 0) {
			builder.followerCount(followerUserList.size());
		}
		
		
		if(followingUserList != null && followingUserList.size() > 0) {
			builder.followingCount(followingUserList.size());
		}
		
		if(wishLists != null && wishLists.size() > 0) {
			builder.wishListCount(wishLists.size());
		}
		
		if(readBookList != null && readBookList.size() > 0) {
			builder.readedBooksCount(readBookList.size());
		}
		
		if(bookTreeList != null && bookTreeList.size() > 0) {
			builder.bookTreesCount(bookTreeList.size());
		}
		
		if(likeGenreList != null && likeGenreList.size() > 0) {
			List<SimpleLikeGenreDTO> likeGenreDTOList =  new ArrayList<>();
			
			likeGenreList.forEach((likeGenre) -> {
				likeGenreDTOList.add(likeGenre.toSimpleLikeGenreDTO());
			});
			builder.likeGenre(likeGenreDTOList);
		}
		
		return builder.build();
	}
	

}
