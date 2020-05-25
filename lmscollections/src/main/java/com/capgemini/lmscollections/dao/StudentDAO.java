package com.capgemini.lmscollections.dao;

import java.util.List;

import com.capgemini.lmscollections.dto.BookPrimaryInfo;

public interface StudentDAO {

	List<BookPrimaryInfo> getAllBooks();

	boolean bookRequest(int userId, int bookId);

	boolean bookReturn(int userId, int bookId);

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo);

}
