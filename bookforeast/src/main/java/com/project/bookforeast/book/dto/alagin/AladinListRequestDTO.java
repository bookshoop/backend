package com.project.bookforeast.book.dto.alagin;

import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AladinListRequestDTO extends AladinRequestDTO {

	private String queryType;
	private String itemSize;
	private String searchTarget = "Book";
	
	
	public MultiValueMap<String, String> toBestSellerReqMap(String API_KEY, int start) {
		return toListReqMap(API_KEY, "Bestseller", start);
	}
	
	
	private MultiValueMap<String, String> toListReqMap(String API_KEY, String listType, int start) {
		MultiValueMap<String, String> map = toReqMap(API_KEY);
		
		map.add("QueryType", listType);
		map.add("MaxResults", itemSize);
		map.add("SearchTarget", searchTarget);
		map.add("start", String.valueOf(start));
		
		return map;
	}
	
}
