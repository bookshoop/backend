package com.project.bookforeast.book.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AladinBookInfosDTO {
	private List<SimpleAladinBookInfoDTO> item;
}
