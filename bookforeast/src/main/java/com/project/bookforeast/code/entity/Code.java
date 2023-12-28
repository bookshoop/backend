package com.project.bookforeast.code.entity;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bookforeast.code.dto.CodeDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeId;
	private String codename;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "upper_code_id")
	@JsonIgnore
	private Code parentCode;
	
	@OneToMany(mappedBy = "parentCode", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Code> childCodes;
	
	public CodeDTO toDTO() {
		Set<Long> convertedCodes = new HashSet<>();
		if (convertedCodes.contains(codeId)) {
	        return null;
	    }
		
		CodeDTO.CodeDTOBuilder builder = CodeDTO.builder();
		
		builder.codeId(codeId)
				.codename(codename);
		
		
	    if (childCodes != null && !childCodes.isEmpty()) {
            List<CodeDTO> childCodeDTOs = childCodes.stream()
                    .map(Code::toDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            builder.childCodesDTO(childCodeDTOs);
        }
		
		return builder.build();
	}
}
