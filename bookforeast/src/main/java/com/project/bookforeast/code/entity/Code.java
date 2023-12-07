package com.project.bookforeast.code.entity;


import com.project.bookforeast.code.dto.CodeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Column(name = "upper_code_id")
	private int upperCodeId;
	
	public CodeDTO toDTO() {
		return CodeDTO.builder()
				.codeId(codeId)
				.codename(codename)
				.upperCodeId(upperCodeId)
				.build();
	}
}
