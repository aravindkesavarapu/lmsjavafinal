package com.capgemini.lmsspringrest.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.exception.LMSException;

@Repository
public class AdminDAOImplementation implements AdminDAO {

	@PersistenceUnit
	EntityManagerFactory factory;

	@Override
	public boolean addBook(BookDetails bookInfo) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(bookInfo);
			transaction.commit();
			return true;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("Book Not Added to database");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Book Not Added to database!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("Book Not Added to DataBase!! Please Try Again ");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean removeBook(int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails bookInfoRecord = manager.find(BookDetails.class, bookId);
			if (bookInfoRecord != null) {
				manager.remove(bookInfoRecord);
				transaction.commit();
				return true;
			} else {
				throw new LMSException("No Book Found");
			}
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this BookId!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this BookId!! Please Try Again ");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean updateBook(BookDetails bookInfo) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails record = manager.find(BookDetails.class, bookInfo.getBookId());
			record.setBookName(bookInfo.getBookName());
			record.setBookCategory(bookInfo.getBookCategory());
			record.setCopies(bookInfo.getCopies());
			record.setPublisherName(bookInfo.getPublisherName());
			record.setAuthorName(bookInfo.getAuthorName());
			transaction.commit();
			return true;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this bookId !! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this BookId!! Please Try Again ");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean bookIssue(int uId, int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		boolean status = false;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpqlIssue = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> bookDetailsQuery = manager.createQuery(jpqlIssue, BookDetails.class);
			bookDetailsQuery.setParameter("bookId", bookId);
			List<BookDetails> bookDetailsList = bookDetailsQuery.getResultList();
			User userInfo = manager.find(User.class, uId);
			BookDetails bookInfo = manager.find(BookDetails.class, bookId);
			int noOfBooks = bookInfo.getCopies();
			if (bookDetailsList != null) {
				String jpqlReq = "select r from RequestDetails r where r.uId=:uId and r.bookId=:bookId";
				TypedQuery<RequestDetails> query1 = manager.createQuery(jpqlReq, RequestDetails.class);
				query1.setParameter("uId", uId);
				query1.setParameter("bookId", bookId);
				RequestDetails requestInfo = query1.getSingleResult();

				if (requestInfo != null) {
					transaction.begin();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					String issueDate = sdf.format(cal.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 7);
					String returnDate = sdf.format(cal.getTime());
					System.out.println(returnDate);
					BookIssue issueBook = new BookIssue();
					issueBook.setUId(uId);
					issueBook.setUserMailId(userInfo.getEmail());
					issueBook.setBookId(bookId);
					issueBook.setBookName(bookInfo.getBookName());
					issueBook.setIssueDate(java.sql.Date.valueOf(issueDate));
					issueBook.setReturnDate(java.sql.Date.valueOf(returnDate));
					noOfBooks--;
					manager.persist(issueBook);
					transaction.commit();
					transaction.begin();
					bookInfo.setCopies(noOfBooks);
					transaction.commit();

					transaction.begin();
					manager.remove(requestInfo);
					transaction.commit();

					transaction.begin();
					BookDetails book = manager.find(BookDetails.class, bookId);
					BooksBorrowed borrowedBooks = new BooksBorrowed();
					borrowedBooks.setBookId(bookId);
					borrowedBooks.setBookName(book.getBookName());
					borrowedBooks.setUId(uId);
					manager.persist(borrowedBooks);
					transaction.commit();
					status = true;
				} else {
					throw new LMSException("The respective user have not placed any request");
				}

			} else {
				throw new LMSException("There is no book exist with bookId" + bookId);
			}
			if (status) {
				return true;
			} else {
				transaction.rollback();
				return false;
			}
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		}catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this BookId!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("No Book Found with this BookId!! Please Try Again ");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}

	}

	@Override
	public List<RequestDetails> getAllRequestBooks() {

		EntityManager manager = null;
		List<RequestDetails> recordList = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "select r from RequestDetails r";
			TypedQuery<RequestDetails> query = manager.createQuery(jpql, RequestDetails.class);
			recordList = query.getResultList();

		} catch (LMSException e) {
			throw new LMSException("Books are not Requested by any of the user");
		} catch (NoResultException e) {
			throw new LMSException("Books are not Requested by any of the user user");
		} catch (NullPointerException e) {
			throw new LMSException("Books are not Requested by any user!! Please Try Again ");
		} catch (Exception e) {
			throw new LMSException("Error Occured While Commiting the Transction");
		}
		return recordList;
	}

	@Override
	public List<BookIssue> getAllIssuedBooks() {
		EntityManager manager = null;
		List<BookIssue> recordList = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "select b from BookIssue b";
			TypedQuery<BookIssue> query = manager.createQuery(jpql, BookIssue.class);
			recordList = query.getResultList();

		} 
		catch (NoResultException e) {
			throw new LMSException("Books are not Issued");
		} catch (NullPointerException e) {
			throw new LMSException("Books are not Issued!! Please Try Again ");
		} catch (Exception e) {
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}

		return recordList;
	}

	@Override
	public List<User> getAllUsersInfo() {
		EntityManager manager = null;
		List<User> recordList = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "select u from User u";
			TypedQuery<User> query = manager.createQuery(jpql, User.class);
			recordList = query.getResultList();

		}catch (NoResultException e) {
			throw new LMSException("Till now, no Users Are Registerd");
		} catch (NullPointerException e) {
			throw new LMSException("Till now, no Users Are Registerd!! Please Try Again ");
		} catch (Exception e) {
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
		return recordList;

	}

}
