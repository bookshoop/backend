package com.project.bookforeast.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.SimpleBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.AladinBookInfosDTO;
import com.project.bookforeast.book.dto.alagin.AladinRequestDTO;
import com.project.bookforeast.book.dto.alagin.SimpleAladinBookInfoDTO;


@Service
public class AladinServiceImpl implements BookApiService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final CategoryService categoryService;
	
	
	public AladinServiceImpl(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	
	@Value("${aladdin.api-key}")
	private String API_KEY;
	
	private String LIST_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
	
	private String SEARCH_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
	
	private String BOOK_INFO_URL = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
	
	
	public ApiBookInfosDTO getBestSellerList(int itemSize, String cursor) {
		AladinRequestDTO aladinRequestDTO = new AladinRequestDTO();
		aladinRequestDTO.setItemSize(String.valueOf(itemSize));
		int pageNum = findPageNumByCursor(itemSize, cursor);
		
		UriComponents uriComponents = UriComponentsBuilder
									.fromHttpUrl(LIST_URL)
									.queryParams(aladinRequestDTO.toBestSellerReqMap(API_KEY, pageNum))
									.build();
		
	AladinBookInfosDTO aladinBookInfosDTO = restTemplate.getForObject(uriComponents.toUri(), AladinBookInfosDTO.class);
	return aladinBookInfosDTO;
	}
	


	private int findPageNumByCursor(int itemSize, String cursor) {
		// 커서가 없는 경우
		if(cursor == null) {
			return 1;
		}
		
		// 현재 페이지가 첫페이지이자 마지막 페이지인 경우 
		int itemOrder = Integer.parseInt(cursor.substring(3)); 
		if(itemOrder / itemSize <= 0) {
			return 1;
		// 다음페이지를 로드해야 하는 경우
		} else {
			return ( itemOrder / itemSize ) + 1;
		}
		
	}

	
	
	public BookInfosDTO apiDTOsToBookInfosDTO(int itemSize, String cursor, ApiBookInfosDTO apiBookInfosDTO) {
		if(!(apiBookInfosDTO instanceof AladinBookInfosDTO)) {
			return null;
		};

		// 책 목록 정보가 없는 경우
		AladinBookInfosDTO aladinBookInfosDTO = (AladinBookInfosDTO) apiBookInfosDTO;
		if(apiBookInfosDTO == null || aladinBookInfosDTO.getTotalResults() == 0) {
			return null;
		}

		List<SimpleAladinBookInfoDTO> item = aladinBookInfosDTO.getItem();
		int total = aladinBookInfosDTO.getTotalResults();
		
		// list를 변환하기
		BookInfosDTO bookInfosDTO = new BookInfosDTO();	
		List<SimpleBookInfoDTO> content = new ArrayList<>();
		bookInfosDTO.setContent(content);
		
		for(int i = 0; i < item.size(); i++) {
			SimpleBookInfoDTO simpleBookInfoDTO = apiDTOToBookInfoDTO(item.get(i), i, cursor);
			bookInfosDTO.getContent().add(simpleBookInfoDTO);
		}

		// hasMore설정하기
		// 지금 현재 cursor + itemSize보다 total이 큰 경우
		boolean hasMore = (total > itemSize + Integer.parseInt(cursor)) ? true : false;
		bookInfosDTO.setHasMore(hasMore);
		
		return bookInfosDTO;
	}
	
	

	private SimpleBookInfoDTO apiDTOToBookInfoDTO(SimpleAladinBookInfoDTO simpleAladinBookInfoDTO, int index, String cursor) {
		SimpleBookInfoDTO simpleBookInfoDTO = new SimpleBookInfoDTO();
		simpleBookInfoDTO.setTitle(simpleAladinBookInfoDTO.getTitle());
		simpleBookInfoDTO.setWriter(makeWriterFormat(simpleAladinBookInfoDTO.getAuthor()));
		simpleBookInfoDTO.setId(makeIdForamt(simpleAladinBookInfoDTO));
		simpleBookInfoDTO.setThumbnail(simpleAladinBookInfoDTO.getCover());
		simpleBookInfoDTO.setCategory(categoryService.classifyCatg(simpleAladinBookInfoDTO.getCategoryName()));
		simpleBookInfoDTO.setCursor(makeCursorFormat(cursor, index));
	
		return simpleBookInfoDTO;
	}



	private String makeWriterFormat(String author) {
		String writer = author.substring(0, author.indexOf("(") -1);
		return writer;
	}
		
		


	private String makeIdForamt(SimpleAladinBookInfoDTO simpleAladinBookInfoDTO) {
		String isbn = simpleAladinBookInfoDTO.getIsbn();
		String isbn13 = simpleAladinBookInfoDTO.getIsbn();
		
		if(isbn13 == null || isbn13.length() == 0) {
			return isbn;
		}
		return isbn13;
	}

	

	private String makeCursorFormat(String cursor, int index) {
		String currentCursor = "0000";
		
		if(cursor == null) {
			currentCursor += (index+ 1);
		} else {
			currentCursor += (Integer.parseInt(cursor) + index + 1);
		}
		
		return currentCursor;
	}
}
