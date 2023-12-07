package com.project.bookforeast.wishList.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.wishList.dto.WishListDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishListId;
	
	private String bookId;
	private int userAdded;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User registUser;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public WishListDTO toDTO() {
		WishListDTO.WishListDTOBuilder wishListDTOBuilder = WishListDTO.builder()
																.wishListId(wishListId)
																.bookId(bookId)
																.userAdded(userAdded)
																.registDt(registDt);
		
		if(registUser != null) {
			wishListDTOBuilder.registUserDTO(registUser.toDTO());
		}
		
		return wishListDTOBuilder.build();
	}
}
