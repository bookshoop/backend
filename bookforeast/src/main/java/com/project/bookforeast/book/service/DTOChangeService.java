package com.project.bookforeast.book.service;

import com.project.bookforeast.book.dto.ApiBookInfosDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;

public interface DTOChangeService {
    
    public BookInfosDTO apiDTOsToBookInfosDTO(int itemSize, String cursor, ApiBookInfosDTO apiBookInfosDTO);
}
