package com.project.bookforeast.readBook.dto;

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
public class RangeReadDTO {
	private String genre;
	private int count;
	private double percentage;
	
}
