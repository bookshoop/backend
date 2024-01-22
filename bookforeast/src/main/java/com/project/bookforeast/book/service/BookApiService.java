package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;

public interface BookApiService {

	public BookInfosDTO getBestSellerList(int itemSize, String cursor);

    public DetailBookInfoDTO findByIsbn(String isbn);
}
