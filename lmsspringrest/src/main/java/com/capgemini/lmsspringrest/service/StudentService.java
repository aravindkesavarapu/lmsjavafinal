package com.capgemini.lmsspringrest.service;

import java.util.List;

import com.capgemini.lmsspringrest.dto.BooksBorrowed;

public interface StudentService {

	boolean request(int bookId, int uid);

	boolean returnBook(int bookId, int uid);

	List<BooksBorrowed> getBorrowedBooks(int uid);

}
