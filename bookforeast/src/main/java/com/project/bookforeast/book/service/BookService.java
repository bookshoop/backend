package com.project.bookforeast.book.service;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;

public interface BookService {

	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue);

	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor);

	public DetailBookInfoDTO getDetailBookInfo(String id);

	public void insBookInfo(String accessToken, BookDTO bookDTO, MultipartFile file);
}
