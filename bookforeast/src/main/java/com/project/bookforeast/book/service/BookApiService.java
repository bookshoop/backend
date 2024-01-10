package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;

public interface BookApiService {

	public ApiBookInfosDTO getBestSellerList(int itemSize, String cursor);

	public BookInfosDTO apiDTOsToBookInfosDTO(int itemSize, String cursor, ApiBookInfosDTO apiBookInfosDTO);

    public DetailBookInfoDTO getDetailBookInfo();
}
