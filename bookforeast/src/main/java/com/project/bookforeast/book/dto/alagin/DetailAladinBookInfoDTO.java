package com.project.bookforeast.book.dto.alagin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailAladinBookInfoDTO {
    private String title; // 도서 제목
	private String author; // 저자
	private String isbn; 
	private String isbn13;
	private String categoryName; // 카테고리명
	private String cover; // 썸네일 사진 링크
    private String description;
    private int priceStandard;
    private String publisher;
}
