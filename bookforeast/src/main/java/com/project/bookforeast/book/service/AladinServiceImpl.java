package com.project.bookforeast.book.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.AladinBookInfosDTO;
import com.project.bookforeast.book.dto.alagin.AladinDetailRequestDTO;
import com.project.bookforeast.book.dto.alagin.AladinListRequestDTO;
import com.project.bookforeast.book.dto.alagin.DetailAladinBookInfoDTO;
import com.project.bookforeast.book.error.BookErrorResult;
import com.project.bookforeast.book.error.BookException;


@Service
public class AladinServiceImpl implements BookApiService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final DTOChangeService dtoChangeService;
	
	
	public AladinServiceImpl(CategoryService categoryService, DTOChangeService dtoChangeService) {
		this.dtoChangeService = dtoChangeService;
	}
	
	
	@Value("${aladdin.api-key}")
	private String API_KEY;
	
	private String LIST_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
	
	private String SEARCH_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
	
	private String BOOK_INFO_URL = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
	
	
	public BookInfosDTO getBestSellerList(int itemSize, String cursor) {
		AladinListRequestDTO aladinRequestDTO = new AladinListRequestDTO();
		aladinRequestDTO.setItemSize(String.valueOf(itemSize));
		int pageNum = findPageNumByCursor(itemSize, cursor);
		
		UriComponents uriComponents = UriComponentsBuilder
									.fromHttpUrl(LIST_URL)
									.queryParams(aladinRequestDTO.toBestSellerReqMap(API_KEY, pageNum))
									.build();
		
	AladinBookInfosDTO aladinBookInfosDTO = restTemplate.getForObject(uriComponents.toUri(), AladinBookInfosDTO.class);
	return dtoChangeService.apiDTOsToBookInfosDTO(itemSize, cursor, aladinBookInfosDTO);
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
	

	

	@Override
	public DetailBookInfoDTO findByIsbn(String isbn) {
		AladinDetailRequestDTO aladinDetailRequestDTO = new AladinDetailRequestDTO();
		
		UriComponents uriComponents = UriComponentsBuilder
									.fromHttpUrl(BOOK_INFO_URL)
									.queryParams(aladinDetailRequestDTO.toDetailReqMap(API_KEY, isbn))
									.build();
		
		DetailAladinBookInfoDTO detailAladinBookInfoDTO = restTemplate.getForObject(uriComponents.toUri(), DetailAladinBookInfoDTO.class);
		
		if(detailAladinBookInfoDTO.getItem() != null && detailAladinBookInfoDTO.getItem().get(0) != null) {
			DetailBookInfoDTO detailBookInfoDTO = dtoChangeService.apiDTOToDetailBookInfoDTO(detailAladinBookInfoDTO.getItem().get(0));
			return detailBookInfoDTO;		
		} else {
			throw new BookException(BookErrorResult.NOT_REGISTED_BOOK);
		}
	}
}
