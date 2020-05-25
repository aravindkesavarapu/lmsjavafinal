package com.capgemini.lmsspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.lmsspringrest.dao.AdminDAO;
import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;

@Service
public class AdminServiceImplementation implements AdminService{

	@Autowired
	private AdminDAO dao;
	@Override
	public boolean addBook(BookDetails bookDetail) {
		return dao.addBook(bookDetail);
	}

	@Override
	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
	}

	@Override
	public boolean updateBook(BookDetails book) {
		return dao.updateBook(book);
	}

	@Override
	public boolean bookIssue(int id, int bookIid) {
		return dao.bookIssue(id, bookIid);
	}

	@Override
	public List<RequestDetails> showRequests() {
		return dao.showRequests();
	}

	@Override
	public List<BookIssue> showIssuedBooks() {
		return dao.showIssuedBooks();
	}

	@Override
	public List<User> showUsers() {
		return dao.showUsers();
	}

}
