package com.project.bookforeast.book.dto.alagin;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AladinRequestDTO {   
    private String output = "js";
    private String version = "20131101";


    protected MultiValueMap<String, String> toReqMap(String API_KEY) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("ttbkey", API_KEY);
        map.add("output", output);
        map.add("Version", version);

        return map;
    }
}
