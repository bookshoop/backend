package com.project.bookforeast.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;

public interface BookService {
    
    public BookInfosDTO getBookInfos(int itemSize, String cursor, String searchValue);

	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor);

	public void insBookInfoByUser(BookDTO bookDTO, MultipartFile file);
    
    public void insAladinBookInfosToDB(List<DetailBookInfoDTO> detailBookInfoDTOs);

    public void updBookInfo(BookDTO bookDTO, MultipartFile file);

    public void delBookInfo(String id);

    public DetailBookInfoDTO getDetailBookInfoById(Long id);

    public DetailBookInfoDTO getDetailBookInfoByIsbn(String isbn);


}
