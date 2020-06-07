package com.capgemini.lmsspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.lmsspringrest.dao.UserDAO;
import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.User;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserDAO dao;
	@Override
	public boolean addUser(User user) {
		return dao.registerUser(user);
	}

	@Override
	public User authUser(String email, String password) {
		return dao.authUser(email, password);
	}

	@Override
	public List<BookDetails> searchBookByTitle(String bookName) {
		return dao.searchBookByTitle(bookName);
	}

	@Override
	public List<BookDetails> searchBookByAuthor(String authorName) {
		return dao.searchBookByAuthor(authorName);
	}

	@Override
	public List<BookDetails> searchBookById(int bookId) {
		return dao.searchBookById(bookId);
	}

	@Override
	public List<BookDetails> getBooksInfo() {
		return dao.getBooksInfo();
	}

	@Override
	public boolean updatePassword(int id, String password, String newPassword, String role) {
		return dao.updatePassword(id, password, newPassword, role);
	}

}
