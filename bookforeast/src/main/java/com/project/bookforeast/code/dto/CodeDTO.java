package com.project.bookforeast.code.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.project.bookforeast.code.entity.Code;

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
	private CodeDTO parentCodeDTO;
	private List<CodeDTO> childCodeDTOList;
	
	
	public Code toEntity() {
		Code.CodeBuilder builder = Code.builder();
		
		builder.codeId(codeId)
				.codename(codename);
		
		if(parentCodeDTO != null) {
			builder.parentCode(parentCodeDTO.toEntity());
		}
		
		if(childCodeDTOList != null && childCodeDTOList.size() > 0) {
			List<Code> codeList = childCodeDTOList.stream().map(CodeDTO::toEntity).collect(Collectors.toList());
			builder.childCodeList(codeList);
		}
		
		return builder.build();
	}
}
