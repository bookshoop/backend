package com.project.bookforeast.book.dto.alagin;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleAladinBookInfoDTO extends ApiBookInfoDTO {
	private String title; // 도서 제목
	private String author; // 저자
	private String categoryName; // 카테고리명
	private String cover; // 썸네일 사진 링크
}
