package com.project.bookforeast.book.service;

import java.util.ArrayList;
import java.util.List;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.SimpleBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.AladinBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.AladinBookInfosDTO;
import com.project.bookforeast.book.dto.alagin.SimpleAladinBookInfoDTO;

public class AladinDTOChangeServiceImpl implements DTOChangeService {
    

    private CategoryService categoryService;


    public AladinDTOChangeServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public BookInfosDTO apiDTOsToBookInfosDTO(int itemSize, String cursor, ApiBookInfosDTO apiBookInfosDTO) {
		if(!(apiBookInfosDTO instanceof AladinBookInfosDTO)) {
			return null;
		};

		// 책 목록 정보가 없는 경우
		AladinBookInfosDTO aladinBookInfosDTO = (AladinBookInfosDTO) apiBookInfosDTO;
		if(apiBookInfosDTO == null || aladinBookInfosDTO.getTotalResults() == 0) {
			return null;
		}

		List<SimpleAladinBookInfoDTO> item = aladinBookInfosDTO.getItem();
		int total = aladinBookInfosDTO.getTotalResults();
		
		// list를 변환하기
		BookInfosDTO bookInfosDTO = new BookInfosDTO();	
		List<SimpleBookInfoDTO> content = new ArrayList<>();
		bookInfosDTO.setContent(content);
		
		for(int i = 0; i < item.size(); i++) {
			SimpleBookInfoDTO simpleBookInfoDTO = apiDTOToSimpleBookInfoDTO(item.get(i), i, cursor);
			bookInfosDTO.getContent().add(simpleBookInfoDTO);
		}

		// hasMore설정하기
		// 지금 현재 cursor + itemSize보다 total이 큰 경우
		bookInfosDTO.setHasMore(checkHasMore(total, cursor, itemSize));
		
		return bookInfosDTO;
	}


    private SimpleBookInfoDTO apiDTOToSimpleBookInfoDTO(SimpleAladinBookInfoDTO simpleAladinBookInfoDTO, int index, String cursor) {
		SimpleBookInfoDTO simpleBookInfoDTO = new SimpleBookInfoDTO();
		simpleBookInfoDTO.setTitle(simpleAladinBookInfoDTO.getTitle());
		simpleBookInfoDTO.setWriter(makeWriterFormat(simpleAladinBookInfoDTO.getAuthor()));
		simpleBookInfoDTO.setId(makeIdForamt(simpleAladinBookInfoDTO));
		simpleBookInfoDTO.setThumbnail(simpleAladinBookInfoDTO.getCover());
		simpleBookInfoDTO.setCategory(categoryService.classifyCatg(simpleAladinBookInfoDTO.getCategoryName()));
		simpleBookInfoDTO.setCursor(makeNextCursorFormat(cursor, index));
	
		return simpleBookInfoDTO;
	}


    
	
	private boolean checkHasMore(int total, String cursor, int itemSize) {
		boolean hasMore = false;
		if(cursor == null) {
			hasMore = (total > itemSize);
		} else {
			hasMore = (total > itemSize + Integer.parseInt(cursor));
		}

		return hasMore;
	}





	private String makeWriterFormat(String author) {
		String writer = author.substring(0, author.indexOf("(") -1);
		return writer;
	}
		
		


	private String makeIdForamt(SimpleAladinBookInfoDTO simpleAladinBookInfoDTO) {
		String isbn = simpleAladinBookInfoDTO.getIsbn();
		String isbn13 = simpleAladinBookInfoDTO.getIsbn();
		
		if(isbn13 == null || isbn13.length() == 0) {
			return isbn;
		}
		return isbn13;
	}

	

	private String makeNextCursorFormat(String currentCursor, int index) {
		String nextCursor = "0000";
		
		if(currentCursor == null) {
			nextCursor += (index+ 1);
		} else {
			nextCursor += (Integer.parseInt(currentCursor) + index + 1);
		}
		
		return nextCursor;
	}


    @Override
    public DetailBookInfoDTO apiDTOToDetailBookInfoDTO(AladinBookInfoDTO aladinBookInfoDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'apiDTOToDetailBookInfoDTO'");
    }



}
