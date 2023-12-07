package com.project.bookforeast.wishList.dto;

import java.time.LocalDateTime;

import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.wishList.entity.WishList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListDTO {

	private Long wishListId;
	private String bookId;
	private int userAdded;
	private UserDTO registUserDTO;
	private LocalDateTime registDt;
	
	public WishList toEntity() {
		WishList.WishListBuilder wishListBuilder = WishList.builder()
														.wishListId(wishListId)
														.bookId(bookId)
														.userAdded(userAdded);
		
		if(registUserDTO != null) {
			wishListBuilder.registUser(registUserDTO.toEntity());
		}
		
		return wishListBuilder.build();
	}
}
