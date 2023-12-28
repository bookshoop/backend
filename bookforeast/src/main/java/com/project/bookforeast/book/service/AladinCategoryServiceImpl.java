package com.project.bookforeast.book.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	
	public CodeDTO classifyCatg(String categoryStr) {
		String[] inputCatg = categoryStr.split(">");
		return classifyFirstCatg(inputCatg);
	}


	private CodeDTO classifyFirstCatg(String[] inputCatg) {
		CodeDTO codeDTO = codeService.findAllByCodename(inputCatg[0]);
		
		if(inputCatg.length <= 1) {
			return codeDTO;
		} else {
			return classifySecondCatg(inputCatg, codeDTO);
		}
	}


	private CodeDTO classifySecondCatg(String[] inputCatg, CodeDTO codeDTO) {
		List<CodeDTO> firstCodeDTOs = codeDTO.getChildCodesDTO();
		String secondCatg = inputCatg[1];
		
		// 소설을 포함하는 경우로 해버리면
		// 소설 + 시 + 희곡이 전부다 소설로 들어가버림
		// 해결하려면 ) 1. 소설, 시, 희곡을 나눌 수 있도록 3번째 카테고리까지 봐야함
	    
		// 분류를 한번만 하면 될 것, 분류를 한 번 더 해야할 것, 분류 기준이 더 이상 없는 것
		
		List<Map<String, String>> matchGenreList = createMatchGenreList();
		
		
		if(!checkInputCatgMatchWithCodename(secondCatg)) {
			return classifySecondCatg(inputCatg, firstCodeDTOs);
		}
		
		if(secondCatg.contains("인문")) {
		    List<CodeDTO> secondCodeDTOs = filterCodeDTOsWithCodename("인문", firstCodeDTOs);
			classifyThirdCatg(inputCatg, secondCodeDTOs);
		} else if(secondCatg.contains("경제경영")) {
			List<CodeDTO> secondCodeDTOs = filterCodeDTOsWithCodename("경제/경영", firstCodeDTOs);
			classifyThirdCatg(inputCatg, secondCodeDTOs);
		}
		
		else {
			return codeDTO;
		}
		
		
		
	}

	private List<Map<String, String>> createMatchGenreList() {
		List<Map<String, String>> matchGenreList = new ArrayList<>();
		Map<String, String> matchGenre = new HashMap<>();
		
		matchGenre.put("경제경영", "경제/경영");
		matchGenre.put("인문학", "인문");
		matchGenre.put("과학", "과학");
		matchGenre.put("역사", "역사");
		matchGenre.put("유아", "가정/육아/어린이");
		
		
		return null;
	}


	private boolean checkInputCatgMatchWithCodename(String secondCatg) {
		String[] notMatchGenres = {"소설/시/희곡", "가정/요리/뷰티", };
		
		if(Arrays.stream(notMatchGenres).anyMatch(secondCatg::equals)) {
			return false;
		} else {
			return true;
		}
	}


	private List<CodeDTO> filterCodeDTOsWithCodename(String codename, List<CodeDTO> codeDTOs) {
		 List<CodeDTO> secondCodeDTOs = codeDTOs.stream()
	                .filter(CodeDTO -> codename.equals(CodeDTO.getCodename()))
	                .collect(Collectors.toList());
		 return secondCodeDTOs;
	}
	
	
	private CodeDTO classifySecondCatg(String[] inputCatg, List<CodeDTO> firstCodeDTOs) {
		// TODO Auto-generated method stub
		return null;
	}
	

	private CodeDTO classifyThirdCatg(String[] inputCatg, List<CodeDTO> firstCodeDTOs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
