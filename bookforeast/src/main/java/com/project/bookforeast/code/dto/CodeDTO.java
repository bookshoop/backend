package com.project.bookforeast.code.dto;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
	private List<CodeDTO> childCodesDTO;
	
	
	public Code toEntity() {
		Set<Long> convertedCodes = new HashSet<>();
		if (convertedCodes.contains(codeId)) {
	        return null;
	    }
		
		Code.CodeBuilder builder = Code.builder();
		
		builder.codeId(codeId)
				.codename(codename);
		
		
	    if (childCodesDTO != null && !childCodesDTO.isEmpty()) {
            List<Code> childCodes = childCodesDTO.stream()
                    .map(CodeDTO::toEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            builder.childCodes(childCodes);
        }
	    
	    
		return builder.build();
	}
}
