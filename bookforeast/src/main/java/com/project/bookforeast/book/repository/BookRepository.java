package com.project.bookforeast.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    public Book findByIsbn(String isbn);
    
}
