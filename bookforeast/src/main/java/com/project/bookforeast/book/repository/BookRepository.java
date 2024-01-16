package com.project.bookforeast.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.book.entity.Book;
import com.project.bookforeast.user.entity.User;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    public Book findByIsbn(String isbn);

    public Book findByIsbnAndRegistUser(String isbn, User findUser);
    
}
