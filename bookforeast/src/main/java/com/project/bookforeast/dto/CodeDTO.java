package com.project.bookforeast.dto;

import com.project.bookforeast.entity.Code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDTO {

	private Long codeId;
	private String codename;
	private int upperCodeId;
	
	public Code toEntity() {
		return Code.builder()
				.codeId(codeId)
				.codename(codename)
				.upperCodeId(upperCodeId)
				.build();
	}
}
