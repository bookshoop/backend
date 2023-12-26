package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.BookInfosDTO;

public interface BookService {

	BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue);

}
