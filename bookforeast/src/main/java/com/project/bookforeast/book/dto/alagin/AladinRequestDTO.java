package com.project.bookforeast.book.dto.alagin;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
public class AladinRequestDTO {

	private String queryType;
	private String itemSize;
	private String searchTarget = "Book";
	private String output = "js";
	private String version = "20131101";
	
	
	public MultiValueMap<String, String> toBestSellerReqMap(String API_KEY, int start) {
		return toListReqMap(API_KEY, "Bestseller", start);
	}
	
	
	private MultiValueMap<String, String> toListReqMap(String API_KEY, String listType, int start) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		map.add("ttbkey", API_KEY);
		map.add("QueryType", listType);
		map.add("MaxResults", itemSize);
		map.add("SearchTarget", searchTarget);
		map.add("output", output);
		map.add("Version", version);
		map.add("start", String.valueOf(start));
		
		return map;
	}
	
}
