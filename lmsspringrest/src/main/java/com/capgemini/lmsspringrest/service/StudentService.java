package com.capgemini.lmsspringrest.service;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.lmsspringrest.dto.BooksBorrowed;

public interface StudentService {

	boolean request(int bookId, int id);

	boolean returnBook(int bookId, int id);

	LinkedList<Integer> bookHistoryDetails(int id);

	List<BooksBorrowed> borrowedBook(int id);

}
