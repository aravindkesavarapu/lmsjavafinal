package com.capgemini.lmsspringrest.dao;

import java.util.List;

import com.capgemini.lmsspringrest.dto.BooksBorrowed;

public interface StudentDAO {

	boolean returnBook(int bookId, int uId);

	boolean request(int bookId, int uId);

	
	List<BooksBorrowed> getBorrowedBooks(int uId);

}
