package com.project.bookforeast.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.SimpleBookInfoDTO;
import com.project.bookforeast.book.entity.Book;
import com.project.bookforeast.book.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService {

	private final BookApiService bookApiService;
	private final BookRepository bookRepository;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService, BookRepository bookRepository) {
		this.bookApiService = bookApiService;
		this.bookRepository = bookRepository;
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
	
	
		// 알라딘 api에 요청하고 그 결과가 없을 경우
		// 책 아이디로 책 상세정보 가지고오는 알라딘 api만들기
		

		// 내부 db에 먼저 요청을 하기
		// 책 id를 바탕으로 책 정보 가지고오는 내부 api만들기
	public DetailBookInfoDTO getDetailBookInfo(String id) {
		DetailBookInfoDTO detailBookInfoDTO = bookApiService.getDetailBookInfo();
		if(detailBookInfoDTO == null) {
			detailBookInfoDTO = findByIsbn(id);
		}

		return detailBookInfoDTO;

	}

	public DetailBookInfoDTO findByIsbn(String isbn) {
		Book book = bookRepository.findByIsbn(isbn);
		return book.toDetailBookInfoDTO();
	}


//	public BookInfosDTO getRecommandBookInfos(int itemSize, String cursor) {
//		// 좋아하는 장르, 자주읽는 장르, 생년월일 별 추천 도서 검색
//		
//		// 추천도서가 없는 경우나 개수가 부족한 경우 알라딘에서 편집자 추천 리스트 가져오기
//		
//		
//	}
}
