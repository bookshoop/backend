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
public class ReadStatsDTO {
	private int totalReadedBooks;
	private int monthlyAverage;
	private int totalRank;
	private int totalPercentage;
	private int followingRank;
	private int followingPercentage;
}
