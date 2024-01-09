package com.project.bookforeast.code.service;

import com.project.bookforeast.code.dto.CodeDTO;

public interface CodeService {


	public CodeDTO findAllByCodename(String codename);

	public CodeDTO findByCodename(String codename);
	
	public void save(CodeDTO codeDTO);
	
}
