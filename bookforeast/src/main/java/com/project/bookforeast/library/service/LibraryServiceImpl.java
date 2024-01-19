package com.project.bookforeast.library.service;

import org.springframework.stereotype.Service;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.library.entity.Library;
import com.project.bookforeast.library.repository.LibraryRepository;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.service.UserService;

import jakarta.validation.Valid;

@Service
public class LibraryServiceImpl implements LibraryService {
    
    private final LibraryRepository libraryRepository;
    private final UserService userService;

    public LibraryServiceImpl(LibraryRepository libraryRepository, UserService userService) {
        this.libraryRepository = libraryRepository;
        this.userService = userService;
    }

    @Override
    public void insLibrary(@Valid LibraryDTO libraryDTO) {
        User findUser = userService.findUserInSecurityContext();

        Library library = libraryDTO.toEntity();
        library.setUser(findUser);

        libraryRepository.save(library);
    }

    
}
