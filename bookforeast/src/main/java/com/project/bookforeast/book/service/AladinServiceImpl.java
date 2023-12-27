package com.project.bookforeast.book.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.bookforeast.book.dto.AladinRequestDTO;


@Service
public class AladinServiceImpl implements BookApiService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	
	@Value("${aladdin.api-key}")
	private String API_KEY;
	
	private String LIST_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
	
	private String SEARCH_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
	
	private String BOOK_INFO_URL = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
	
	
	public void getBestSellerList(int itemSize) {
		AladinRequestDTO aladinRequestDTO = new AladinRequestDTO();
		aladinRequestDTO.setItemSize(String.valueOf(itemSize));
		
		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(LIST_URL)
									.queryParams(aladinRequestDTO.toBestSellerReqMap());
		
	}
	
		
		
		
}
