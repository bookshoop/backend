package com.project.bookforeast.book.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.code.service.CodeService;

@Service
public class AladinCategoryServiceImpl implements CategoryService {

	
	private final CodeService codeService; 
	
	@Autowired
	public AladinCategoryServiceImpl(CodeService codeService) {
		this.codeService = codeService;
	}
	
	
	public String classifyCatg(String categoryStr) {
		String[] categories = categoryStr.split(">");
		return findOrSaveCategory(categories);
	}


	private String findOrSaveCategory(String[] categories) {
		String codename;
		
		if(categories.length <= 1) {
			codename = categories[0];
		} else {
			codename = categories[1];
		}
		
		CodeDTO findCategory = codeService.findByCodename(codename);
		if(findCategory == null) {
			saveCategory(codename);
		}
		
		return codename;
	}


	private void saveCategory(String codename) {
		CodeDTO parentCodeDTO = codeService.findByCodename("장르");
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setCodename(codename);
		codeDTO.setParentCodeDTO(parentCodeDTO);
		codeService.save(codeDTO);
	}

}
