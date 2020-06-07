package com.capgemini.lmsspringrest.service;

import java.util.List;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;


public interface AdminService {

	boolean addBook(BookDetails bookDetail);

	boolean removeBook(int bookId);

	boolean updateBook(BookDetails book);

	boolean bookIssue(int id, int bookId);
	
	List<RequestDetails> getAllRequestBooks();

	List<BookIssue> getAllIssuedBooks();
	
	 List<User> getAllUsersInfo();
}
