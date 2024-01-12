package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.dto.alagin.ApiBookInfoDTO;

public interface DTOChangeService {
    
    public BookInfosDTO apiDTOsToBookInfosDTO(int itemSize, String cursor, ApiBookInfosDTO apiBookInfosDTO);

    public DetailBookInfoDTO apiDTOToDetailBookInfoDTO(ApiBookInfoDTO apiBookInfoDTO);
}
