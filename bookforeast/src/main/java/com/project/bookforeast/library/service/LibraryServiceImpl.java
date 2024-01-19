package com.project.bookforeast.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.bookforeast.library.dto.LibraryDTO;
import com.project.bookforeast.library.dto.SimpleLibraryDTO;
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
    public void insLibrary(SimpleLibraryDTO simpleLibraryDTO) {
        Library library = simpleLibraryDTOToEntity(simpleLibraryDTO)
        libraryRepository.save(library);
    }
    

    private Library simpleLibraryDTOToEntity(SimpleLibraryDTO simpleLibraryDTO) {
        Library library = new Library();
        User findUser = userService.findUserInSecurityContext();
        library.setRegistUser(findUser);
        
        Long libraryId = simpleLibraryDTO.getLibraryId();
        Long upperLibraryId = simpleLibraryDTO.getUpperLibraryId();
        String libraryName = simpleLibraryDTO.getLibraryName();
        int depth = simpleLibraryDTO.getDepth();

        if(libraryId != null && libraryId != 0) {
            library.setLibraryId(libraryId);
        }

        if(upperLibraryId != null && upperLibraryId != 0) {
            Optional<Library> parentOptionalLibrary = libraryRepository.findById(upperLibraryId);
            if(parentOptionalLibrary.isPresent()) {
                Library parentLibrary = parentOptionalLibrary.get();
                library.setParentLibrary(parentLibrary);
            }
        }

        library.setLibraryName(libraryName);        
        library.setDepth(depth);

        return library;
    }


    @Override
    public LibraryDTO getLibraryInfos() {
        User findUser = userService.findUserInSecurityContext();
        Library library = libraryRepository.findByRegistUser(findUser);

        if(library == null) {
            return null;
        } else {
            return library.toDTO();
        }
    }

    
}
