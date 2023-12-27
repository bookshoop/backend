package com.project.bookforeast.book.dto;

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
public class SimpleAladinBookInfoDTO {
	private String title; // 도서 제목
	private String author; // 저자
	private String isbn; 
	private String isbn13;
	private String categoryName; // 카테고리명
	private String cover; // 썸네일 사진 링크
	
	
//
//	public static SimpleBookInfoDTO toSimpleBookInfoDTO() {
//		
//	}
}
