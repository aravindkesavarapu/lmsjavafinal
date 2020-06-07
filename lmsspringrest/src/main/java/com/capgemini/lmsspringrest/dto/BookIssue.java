package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "BookIssue")
public class BookIssue implements Serializable {

	@Id
	@Column
	@GeneratedValue
	private int id;
	@Column
	private int uId;
	@Column
	private int bookId;
	@Column
	private String bookName;
	@Column
	private String userMailId;
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
	private Date issueDate;
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
	private Date returnDate;

}
