package com.project.bookforeast.library.service;

import java.util.List;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.library.dto.SimpleLibraryDTO;


public interface LibraryService {

    public void insLibrary(SimpleLibraryDTO simpleLibraryDTO);

    public LibraryDTO getLibraryInfos();
    
}
