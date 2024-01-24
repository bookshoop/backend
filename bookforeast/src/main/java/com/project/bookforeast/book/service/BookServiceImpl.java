package com.project.bookforeast.book.service;


import java.util.ArrayList;
import java.util.List;
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
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.file.service.FileService;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.service.UserService;



@Service
public class BookServiceImpl implements BookService {
	private final BookApiService bookApiService;
	private final BookRepository bookRepository;
	private final UserService userService;
	private final FileService fileService;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService, 
	BookRepository bookRepository, 
	UserService userService, 
	FileService fileService
	) {
		this.bookApiService = bookApiService;
		this.bookRepository = bookRepository;
		this.userService = userService;
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
	
	
	public DetailBookInfoDTO getDetailBookInfoById(Long id) {
		
		Optional<Book> bookOptional = bookRepository.findById(id);
		
		if(bookOptional.isPresent()) {
			return bookOptional.get().toDetailBookInfoDTO();
		} else {
			return null;
		}
	}
	
	
	@Override
	public DetailBookInfoDTO getDetailBookInfoByIsbn(String isbn) {
		return bookApiService.findByIsbn(isbn);
	}
	
	
	
	@Override
	public void insBookInfoByUser(BookDTO bookDTO, MultipartFile file) {
		User findUser = userService.findUserInSecurityContext();
		
		Book book = bookDTO.toEntity();
		book.setRegistUser(findUser);
		
		if(file != null) {
			File savedFile = fileService.fileUpload(file, null, Content.BOOK.getContentName());
			book.setThumbnailFileGroup(savedFile.getFileGroup());
		}
		
		bookRepository.save(book);
	}
	

			// bookId가 있는 경우에는 우리 db에 저장된 것
			// bookId가 없는 경우에는 알라딘에서 들고온 것

			// 유저가 서재에 등록 요청 -> 서재에 등록할 책 중 bookId가 없는 것에 대해서 우리 db에 저장

			// 알라딘 api에서 들고온 경우 thumbnaillink 무조건 저장, 유저가 직접 등록한 책의 경우 thumnaillink저장 할 것인가

			// 일단 유저는 무조건 직접 책을 등록을 한 다음에서야 그걸 찾아서 서재에 넣을 수 있음
			// 책 한권 유저가 등록할 때 thumbnail을 넣을 것
			// 알라딘 api에서 들고온 책의 경우 리스트로 직어넣을 것 insBookInfos라고 하지말고 insAladinBookInfosToDB로 하자


	public void insAladinBookInfosToDB(List<DetailBookInfoDTO> detailBookInfoDTOs) {
		List<Book> bookList = new ArrayList<>();
		detailBookInfoDTOs.forEach(detailBookInfoDTO -> {
			Book book = detailBookInfoDTO.toEntity();
			bookList.add(book);
		});

		bookRepository.saveAll(bookList);
	}


	@Override
	public void updBookInfo(BookDTO bookDTO, MultipartFile file) {
		User findUser = userService.findUserInSecurityContext();

		Book book = bookRepository.findByIsbnAndRegistUser(bookDTO.getIsbn(), findUser);
		if(book == null) {
			throw new BookException(BookErrorResult.NOT_REGISTED_BOOK);
		} else {
			setUpdBookInfo(bookDTO, book, file);
		}
		
		bookRepository.save(book);
	}
	
	
	private void setUpdBookInfo(BookDTO updBookDTO, Book book, MultipartFile file) {
		if(updBookDTO.getIsbn() != null || updBookDTO.getIsbn().length() > 0) {
			book.setIsbn(updBookDTO.getIsbn());
		}
		
		if(updBookDTO.getTitle() != null || updBookDTO.getTitle().length() > 0) {
			book.setTitle(updBookDTO.getTitle());
		}
		
		if(updBookDTO.getPublisher() != null || updBookDTO.getPublisher().length() > 0) {
			book.setPublisher(updBookDTO.getPublisher());
		}
		
		if(updBookDTO.getWriter() != null || updBookDTO.getWriter().length() > 0) {
			book.setWriter(updBookDTO.getWriter());
		}

		if(updBookDTO.getDescription() != null || updBookDTO.getDescription().length() > 0) {
			book.setDescription(updBookDTO.getDescription());
		}
		
		if(updBookDTO.getPrice() != 0) {
			book.setPrice(updBookDTO.getPrice());
		}
		
		
		if(file != null) {
			File updatedFile = fileService.fileUpdate(file, book.getThumbnailFileGroup(), Content.BOOK.getContentName());
			book.setThumbnailFileGroup(updatedFile.getFileGroup());
		}
	}

	@Override
	public void delBookInfo(String id) {
		User findUser = userService.findUserInSecurityContext();
		Book book = bookRepository.findByIsbnAndRegistUser(id, findUser);
		if(book == null) {
			throw new BookException(BookErrorResult.NOT_REGISTED_BOOK);
		} else {
			FileGroup delFileGroup = book.getThumbnailFileGroup();
			fileService.deleteFiles(delFileGroup);
			bookRepository.delete(book);
		}
	}




	
	

	
//	public BookInfosDTO getRecommandBookInfos(int itemSize, String cursor) {
//		// 좋아하는 장르, 자주읽는 장르, 생년월일 별 추천 도서 검색
//		
//		// 추천도서가 없는 경우나 개수가 부족한 경우 알라딘에서 편집자 추천 리스트 가져오기
//		
//		
//	}
}
