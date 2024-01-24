package com.project.bookforeast.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.SimpleBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.AladinBookInfosDTO;
import com.project.bookforeast.book.dto.alagin.ApiBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.DetailAladinBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.SimpleAladinBookInfoDTO;

@Service
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
		SimpleBookInfoDTO.SimpleBookInfoDTOBuilder builder = SimpleBookInfoDTO.builder();
		String title = simpleAladinBookInfoDTO.getTitle();
		String writer = makeWriterFormat(simpleAladinBookInfoDTO.getAuthor());
		String thumbnailLink = simpleAladinBookInfoDTO.getCover();
		String category = categoryService.classifyCatg(simpleAladinBookInfoDTO.getCategoryName());
		String nextCursor = makeNextCursorFormat(cursor, index);

		builder.title(title)
			   .writer(writer)
			   .thumbnailLink(thumbnailLink)
			   .category(category)
			   .cursor(nextCursor);
	
		return builder.build();
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
    public DetailBookInfoDTO apiDTOToDetailBookInfoDTO(ApiBookInfoDTO apiBookInfoDTO) {

		if(apiBookInfoDTO == null || !(apiBookInfoDTO instanceof DetailAladinBookInfoDTO.Item)) {
			return null;
		}

		DetailAladinBookInfoDTO.Item detailAladinBookInfoDTO = (DetailAladinBookInfoDTO.Item) apiBookInfoDTO;
		String title = detailAladinBookInfoDTO.getTitle();
		String thumbnailLink = detailAladinBookInfoDTO.getCover();
		String category = categoryService.classifyCatg(detailAladinBookInfoDTO.getCategoryName());
		String writer = makeWriterFormat(detailAladinBookInfoDTO.getAuthor());
		String pubDate = detailAladinBookInfoDTO.getPubDate();
		String description = detailAladinBookInfoDTO.getDescription();
		String isbn = detailAladinBookInfoDTO.getIsbn();
		String isbn13 = detailAladinBookInfoDTO.getIsbn13();
		int price = detailAladinBookInfoDTO.getPriceStandard();
		String publisher = detailAladinBookInfoDTO.getPublisher();

		DetailBookInfoDTO.DetailBookInfoDTOBuilder builder = DetailBookInfoDTO.builder();
		builder.title(title)
			   .thumbnailLink(thumbnailLink)
			   .category(category)
			   .writer(writer)
			   .pubDate(pubDate)
			   .description(description)
			   .isbn(isbn)
			   .isbn13(isbn13)
			   .price(price)
			   .publisher(publisher);

		return builder.build();
    }




	private String makeWriterFormat(String author) {
			String writer = author.substring(0, author.indexOf("(") -1);
			return writer;
		}
			
			
}
