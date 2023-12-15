package com.project.bookforeast.readBook.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReadDTO {

	private List<MonthlyReadInfoDTO> myReadedBook;
	private List<MonthlyReadInfoDTO> userAverage;
	private List<MonthlyReadInfoDTO> followingAverage;
	

	@Setter
	@Getter
	static class MonthlyReadInfoDTO {
		@Schema(example = "2023-12", description = "yyyy-MM 형태",  type = "string", format = "yyyy-MM")
		@DateTimeFormat(pattern = "yyyy-MM")
		private Date date;
		
		@Schema(description = "읽은 권수 평균")
		private Double count;
	}
	
}

