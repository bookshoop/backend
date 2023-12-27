package com.project.bookforeast.book.dto;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${aladdin.api-key}")
	private String API_KEY;
	private String queryType;
	private String itemSize;
	private String searchTarget = "Book";
	private String output = "js";
	private String version = "20231101";
	
	
	public MultiValueMap<String, String> toBestSellerReqMap() {
		return toListReqMap("Bestseller");
	}
	
	
	private MultiValueMap<String, String> toListReqMap(String listType) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		map.add("ttbkey", API_KEY);
		map.add("QueryType", listType);
		map.add("MaxResults", itemSize);
		map.add("start", "1");
		map.add("SearchTarget", searchTarget);
		map.add("output", output);
		map.add("Version", version);
		
		return map;
	}
	
}
