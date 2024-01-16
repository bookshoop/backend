package com.project.bookforeast.book.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.entity.Book;
import com.project.bookforeast.book.error.BookErrorResult;
import com.project.bookforeast.book.error.BookException;
import com.project.bookforeast.book.repository.BookRepository;
import com.project.bookforeast.common.domain.constant.Content;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.file.dto.FileDTO;
import com.project.bookforeast.file.service.FileService;
import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.repository.UserRepository;


@Service
public class BookServiceImpl implements BookService {
	private final BookApiService bookApiService;
	private final BookRepository bookRepository;
	private final SecurityService securityService;
	private final UserRepository userRepository;
	private final FileService fileService;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService, 
						   BookRepository bookRepository, 
						   SecurityService securityService, 
						   UserRepository userRepository, 
						   FileService fileService) {
		this.bookApiService = bookApiService;
		this.bookRepository = bookRepository;
		this.securityService = securityService;
		this.userRepository = userRepository;
		this.fileService = fileService;
	}

	@Override
	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue) {
		return null;
	}
	
		

	@Override
	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor) {		
		return bookApiService.getBestSellerList(itemSize, cursor);
	}
	

	public DetailBookInfoDTO getDetailBookInfo(String id) {
		DetailBookInfoDTO detailBookInfoDTO = bookApiService.getDetailBookInfo(id);
		if(detailBookInfoDTO == null) {
			detailBookInfoDTO = findByIsbn(id);
		}

		return detailBookInfoDTO;

	}


	private DetailBookInfoDTO findByIsbn(String isbn) {
		Book book = bookRepository.findByIsbn(isbn);
		return book.toDetailBookInfoDTO();
	}


	@Override
	public void insBookInfo(String accessToken, BookDTO bookDTO, MultipartFile file) {
		UserDTO userDTO = securityService.getUserInfoInSecurityContext();
		Optional<User> findUser = userRepository.findById(userDTO.getUserId());

		Book book = bookDTO.toEntity();
		book.setRegistUser(findUser.get());
		
		if(file != null) {
			FileDTO savedFileDTO = fileService.fileUpload(file, null, Content.BOOK.getContentName());
			book.setThumbnailFileGroup(savedFileDTO.getFileGroupDTO().toEntity());
		}

		bookRepository.save(book);
	}

	@Override
	public void updBookInfo(String accessToken, BookDTO bookDTO, MultipartFile file) {
		UserDTO userDTO = securityService.getUserInfoInSecurityContext();
		Optional<User> findUser = userRepository.findById(userDTO.getUserId());

		Book book = bookRepository.findByIsbnAndRegistUser(bookDTO.getIsbn(), findUser.get());
		if(book == null) {
			throw new BookException(BookErrorResult.NOT_REGISTED_BOOK);
		}

		if(file != null) {
			FileDTO updatedFileDTO = fileService.fileUpdate(file, book.getThumbnailFileGroup(), Content.BOOK.getContentName());
			book.setThumbnailFileGroup(updatedFileDTO.getFileGroupDTO().toEntity());
		}

		bookRepository.save(book);
	}



	
//	public BookInfosDTO getRecommandBookInfos(int itemSize, String cursor) {
//		// 좋아하는 장르, 자주읽는 장르, 생년월일 별 추천 도서 검색
//		
//		// 추천도서가 없는 경우나 개수가 부족한 경우 알라딘에서 편집자 추천 리스트 가져오기
//		
//		
//	}
}
