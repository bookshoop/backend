package com.project.bookforeast.library.service;

import com.project.bookforeast.library.dto.LibraryDTO;

import jakarta.validation.Valid;

public interface LibraryService {

    void insLibrary(String accessToken, @Valid LibraryDTO libraryDTO);
    
}
