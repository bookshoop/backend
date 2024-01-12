package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;

public interface BookService {

	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue);

	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor);

	public DetailBookInfoDTO getDetailBookInfo(String id);
}
