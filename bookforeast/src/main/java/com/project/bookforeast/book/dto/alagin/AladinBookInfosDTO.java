package com.project.bookforeast.book.dto.alagin;

import java.util.List;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;

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
public class AladinBookInfosDTO extends ApiBookInfosDTO {
	private List<SimpleAladinBookInfoDTO> item;
	private int totalResults;
}
