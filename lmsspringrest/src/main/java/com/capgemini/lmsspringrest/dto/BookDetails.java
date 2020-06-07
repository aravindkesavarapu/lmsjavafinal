package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "BookDetails")
@JsonRootName("Book_Details")
@SequenceGenerator(name = "book_seq", initialValue = 10001, allocationSize = 100)
public class BookDetails implements Serializable {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
	private int bookId;
	@Column
	private String bookName;
	@Column
	private String authorName;
	@Column
	private String publisherName;
	@Column
	private int copies;
	@Column
	private String bookCategory;

}
