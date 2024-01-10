package com.project.bookforeast.book.dto.alagin;

import org.springframework.util.MultiValueMap;


public class AladinDetailRequestDTO extends AladinRequestDTO {
    
    private String itemIdType = "ISBN";

    public MultiValueMap<String, String> toDetailReqMap(String API_KEY, String isbn) {
        MultiValueMap<String, String> map = toReqMap(API_KEY);

        map.add("itemIdType", itemIdType);
        map.add("ItemId", isbn);
        map.add(API_KEY, isbn);

        return map;
    }
}
