package com.project.bookforeast.wishList.dto;

import java.util.List;

import com.project.bookforeast.book.dto.SimpleBookInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class WishListInfoDTO {
	
	private List<SimpleBookInfoDTO> content;
	
	@Schema(description = "다음데이터 있는지")
	private int hasMore;
}
