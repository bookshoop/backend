package com.project.bookforeast.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.common.domain.dto.PagingInfoDTO;

@Service
public class BookServiceImpl implements BookService {

	private final BookApiService bookApiService;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService) {
		this.bookApiService = bookApiService;
	}

	@Override
	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue) {
		return null;
	}
	
		

	@Override
	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor) {		
		ApiBookInfosDTO apiBookInfosDTO = bookApiService.getBestSellerList(itemSize, cursor);
		return bookApiService.apiDTOsToBookInfosDTO(itemSize, cursor, apiBookInfosDTO);
	}
	
	
//	public BookInfosDTO getRecommandBookInfos(int itemSize, String cursor) {
//		// 좋아하는 장르, 자주읽는 장르, 생년월일 별 추천 도서 검색
//		
//		// 추천도서가 없는 경우나 개수가 부족한 경우 알라딘에서 편집자 추천 리스트 가져오기
//		
//		
//	}
}
