package com.project.bookforeast.library.service;

import org.springframework.stereotype.Service;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.library.repository.LibraryRepository;

import jakarta.validation.Valid;

@Service
public class LibraryServiceImpl implements LibraryService {
    
    private final LibraryRepository libraryRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public void insLibrary(String accessToken, @Valid LibraryDTO libraryDTO) {
        
    }
}
