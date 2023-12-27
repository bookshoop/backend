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
	
	
	public void classifyCatg(String categoryStr) {
		String[] inputCatg = categoryStr.split(">");
		CodeDTO catg = codeService.findAllByCodename(inputCatg[0]);
		System.out.println("werwrwer");
		
	}

	

	
}
