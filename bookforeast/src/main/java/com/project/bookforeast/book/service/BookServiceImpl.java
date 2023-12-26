package com.project.bookforeast.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.book.dto.BookInfosDTO;

@Service
public class BookServiceImpl implements BookService {

	private final BookApiService bookApiService;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService) {
		this.bookApiService = bookApiService;
	}

	@Override
	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue) {
		return null;
	}
	
	
}
