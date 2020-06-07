package com.capgemini.lmsspringrest.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.exception.LMSException;

@Repository
public class StudentDAOImplementation implements StudentDAO {

	@PersistenceUnit
	EntityManagerFactory factory;


	@Override
	public boolean returnBook(int bId, int uId) {
		EntityManager manager = null;

		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpqBookDetails = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpqBookDetails, BookDetails.class);
			query.setParameter("bookId", bId);
			BookDetails rs = query.getSingleResult();
			if (rs != null) {
				String jpql1 = "select b from BookIssue b where b.bookId=:bookId and b.uId=:uId ";
				TypedQuery<BookIssue> query1 = manager.createQuery(jpql1, BookIssue.class);
				query1.setParameter("bookId", bId);
				query1.setParameter("uId", uId);
				BookIssue rs1 = query1.getSingleResult();
				if (rs1 != null) {
					Date issueDate = rs1.getIssueDate();
					Calendar cal = Calendar.getInstance();
					Date returnDate = cal.getTime();
					long difference = issueDate.getTime() - returnDate.getTime();
					float daysBetween = (difference / (1000 * 60 * 60 * 24));
					if (daysBetween > 7.0) {

						System.err.println("The User has to pay fine for delaying book return");
						return false;
					} else {
						transaction.begin();
						manager.remove(rs1);
						transaction.commit();

						transaction.begin();
						String jpqlbooks = "select b from BooksBorrowed b  where b.bookId=:bookId and b.uId=:uId";
						Query queryBooks = manager.createQuery(jpqlbooks);
						queryBooks.setParameter("bookId", bId);
						queryBooks.setParameter("uId", uId);
						BooksBorrowed bbb = (BooksBorrowed) queryBooks.getSingleResult();
						manager.remove(bbb);
						transaction.commit();

						return true;

					}
				} else {
					transaction.rollback();
					throw new LMSException("Respective Book Not Issued to User");
				}

			} else {
				transaction.rollback();
				throw new LMSException("Book doesnt exist in the library");
			}
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Book Not Returned to Library!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("Book Not Returned to Library!! Please Try Again");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean request(int bookId, int id) {
		EntityTransaction transaction = null;
		EntityManager manager = null;
		int noOfBooks = 0;
		List<BooksBorrowed> rsBookBorrowed = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpqlBookDetails = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpqlBookDetails, BookDetails.class);
			query.setParameter("bookId", bookId);
			List<BookDetails> resultSetBookDetails = query.getResultList();
			if (resultSetBookDetails != null) {
				String jpqlRequest = "select r from RequestDetails r where r.uId=:uId and r.bookId=:bookId";
				TypedQuery<RequestDetails> queryRequest = (TypedQuery<RequestDetails>) manager.createQuery(jpqlRequest,
						RequestDetails.class);
				queryRequest.setParameter("uId", id);
				queryRequest.setParameter("bookId", bookId);
				List<RequestDetails> listReq = queryRequest.getResultList();
				if (listReq.size() < 1) {

					String jpqlBookBorrwed = "select b from BooksBorrowed b where b.uId=:uId and b.bookId=:bookId";
					TypedQuery<BooksBorrowed> queryBookBorrowed = (TypedQuery<BooksBorrowed>) manager
							.createQuery(jpqlBookBorrwed, BooksBorrowed.class);
					queryBookBorrowed.setParameter("uId", id);
					queryBookBorrowed.setParameter("bookId", bookId);
					rsBookBorrowed = queryBookBorrowed.getResultList();
//					int noOfBorrowedBooks = rsBookBorrowed.size();
					if (rsBookBorrowed.isEmpty() || rsBookBorrowed == null) {
						String jpqlBookIssue = "select b from BookIssue b where b.uId=:uId";
						TypedQuery<BookIssue> query2 = (TypedQuery<BookIssue>) manager.createQuery(jpqlBookIssue,
								BookIssue.class);
						query2.setParameter("uId", id);
						List<BookIssue> rsBookIssue = query2.getResultList();
						noOfBooks = rsBookIssue.size();
						if (noOfBooks <= 3) {
							BookDetails book = manager.find(BookDetails.class, bookId);
							User userEmail = manager.find(User.class, id);
							transaction.begin();
							RequestDetails rsRequestDetails = new RequestDetails();
							rsRequestDetails.setBookId(bookId);
							rsRequestDetails.setEmail(userEmail.getEmail());
							rsRequestDetails.setBookName(book.getBookName());
							rsRequestDetails.setUId(id);
							manager.persist(rsRequestDetails);
							transaction.commit();
							return true;
						} else {
							transaction.rollback();
							throw new LMSException("You have crossed the book limit");
						}
					} else {
						transaction.rollback();
						throw new LMSException("You have already borrowed the requested book");
					}
				} else {
					transaction.rollback();
					throw new LMSException("You Are Requesting the Same book!!");
				}
			} else {
				transaction.rollback();
				throw new LMSException("Requested Book Id Not Found");
			}
//			else {
//				throw new LMSException("The book with requested id is not present");
//			}
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Book Not Requested!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("ssssBook Not Requested!! Please Try Again");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}

	}
//	@Override
//	public boolean request(int bookId, int id) {
//		EntityTransaction transaction = null;
//		EntityManager manager = null;
//		List<BooksBorrowed> rsBookBorrowed = null;
//		int noOfBooks =0 ;
//		try {
//			manager = factory.createEntityManager();
//			transaction = manager.getTransaction();
//			String jpqlBookDetails = "select b from BookDetails b where b.bookId=:bookId";
//			TypedQuery<BookDetails> query = manager.createQuery(jpqlBookDetails, BookDetails.class);
//			query.setParameter("bookId", bookId);
//			List<BookDetails> resultSetBookDetails = query.getResultList();
//			if (resultSetBookDetails != null) {
//				String jpqlRequest = "select r from RequestDetails r where r.uId=:uId and r.bookId=:bookId";
//				TypedQuery<RequestDetails> queryRequest = (TypedQuery<RequestDetails>) manager.createQuery(jpqlRequest,
//						RequestDetails.class);
//				queryRequest.setParameter("uId", id);
//				queryRequest.setParameter("bookId", bookId);
//				List<RequestDetails> listReq = queryRequest.getResultList();
//				if (listReq.size() < 1) {
//					String jpqlBookBorrwed = "select b from BooksBorrowed b where b.uId=:uId and b.bookId=:bookId";
//					TypedQuery<BooksBorrowed> queryBookBorrowed = (TypedQuery<BooksBorrowed>) manager
//							.createQuery(jpqlBookBorrwed, BooksBorrowed.class);
//					queryBookBorrowed.setParameter("uId", id);
//					queryBookBorrowed.setParameter("bookId", bookId);
//					rsBookBorrowed = queryBookBorrowed.getResultList();
//					if (rsBookBorrowed.isEmpty() || rsBookBorrowed == null) {
//						String jpqlBookIssue = "select b from BookIssue b where b.uId=:uId";
//						TypedQuery<BookIssue> query2 = (TypedQuery<BookIssue>) manager.createQuery(jpqlBookIssue,
//								BookIssue.class);
//						query2.setParameter("uId", id);
//						List<BookIssue> rsBookIssue = query2.getResultList();
//						noOfBooks = rsBookIssue.size();
//						if (noOfBooks <= 3) {
//							BookDetails book = manager.find(BookDetails.class, bookId);
//							System.out.println("jhvhbhkjnhbgv");
//							User userEmail = manager.find(User.class, id);
//							System.err.println("09876543");
//							transaction.begin();
//							RequestDetails rsRequestDetails = new RequestDetails();
//							System.out.println(" jknj");
//							rsRequestDetails.setBookId(bookId);
//							System.out.println("0987");
//							rsRequestDetails.setEmail(userEmail.getEmail());
//							System.out.println("000");
//							rsRequestDetails.setBookName(book.getBookName());
//							System.out.println("oiuy");
//							rsRequestDetails.setUId(id);
//							System.out.println("567");
//							manager.persist(rsRequestDetails);
//							transaction.commit();
//							return true;
//						} else {
//							throw new LMSException("You have crossed the book limit");
//						}
//					} else {
//						throw new LMSException("You have already borrowed the requested book");
//					}
//				} else {
//					throw new LMSException("You Are Requesting the Same book");
//				}
//			} else {
//				throw new LMSException("The book with requested id is not present");
//			}
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//			transaction.rollback();
//			return false;
//		} finally {
//			manager.close();
//		}
//	}
//	
	@Override
	public List<BooksBorrowed> getBorrowedBooks(int uid) {
		EntityManager manager = null;

		try {
			manager = factory.createEntityManager();
			String jpql = "select b from BooksBorrowed b where b.uId=:uId";
			TypedQuery<BooksBorrowed> query = manager.createQuery(jpql, BooksBorrowed.class);
			query.setParameter("uId", uid);
			List<BooksBorrowed> recordList = query.getResultList();
			return recordList;
		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} catch (NoResultException e) {
			throw new LMSException("Till Now, No Books are Borrowed By the User!! Please Try Again");
		} catch (NullPointerException e) {
			throw new LMSException("Till Now, No Books are Borrowed By the User!! Please Try Again");
		} catch (Exception e) {
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

}
