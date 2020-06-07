package com.capgemini.lmsspringrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.exception.LMSException;

@Repository
public class UserDAOImplementation implements UserDAO {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistence");

	@Override
	public boolean registerUser(User user) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		boolean isRegistered = false;
		boolean flag = false;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			flag = true;
			manager.persist(user);
			transaction.commit();
			isRegistered = true;
			return isRegistered;

		} catch (LMSException e) {
			if (flag) {
				transaction.rollback();
			}
			throw new LMSException("No Book Found");
		} catch (NoResultException e) {
			if (flag) {
				transaction.rollback();
			}
			throw new LMSException("User Already Exists");
		} catch (Exception e) {

			System.err.println(e.getMessage());
			if (flag) {
				transaction.rollback();
			}
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public User authUser(String email, String password) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpql = "select u from User u where u.email=:email and u.password=:password";
			transaction.begin();
			TypedQuery<User> query = manager.createQuery(jpql, User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			User bean = query.getSingleResult();
			transaction.commit();
			return bean;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("User Not Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Invalid UserName or Password");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookByTitle(String bookName) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.bookName=:bookName";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bookName", bookName);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("No Book Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Books Not Found!! Please Try Again");
		}catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("User Not Found");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookByAuthor(String authorName) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.authorName=:authorName";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("authorName", authorName);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("No Book Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Books Not Found!! Please Try Again");
		}catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("User Not Found");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookById(int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bookId", bookId);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("No Book Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Books Not Found!! Please Try Again");
		}catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("Book Not Found");
		}catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> getBooksInfo() {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		List<BookDetails> recordList = null;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("No Book Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Books Not Found!! Please Try Again");
		}catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("Book Not Found");
		}catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}

	}

	@Override
	public boolean updatePassword(int id, String password, String newPassword, String role) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpql = "select u from User u where u.id=:id and u.role=:role and u.password=:password";
			TypedQuery<User> query = manager.createQuery(jpql, User.class);
			query.setParameter("id", id);
			query.setParameter("role", role);
			query.setParameter("password", password);
			User rs = query.getSingleResult();
			if (rs != null) {
				transaction.begin();
				User record = manager.find(User.class, id);
				record.setPassword(newPassword);
				manager.persist(record);
				transaction.commit();
				return true;
			} else {
				throw new LMSException("User doesnt exist");
			}
		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException("Book Not Found");
		} catch (NoResultException e) {
			transaction.rollback();
			throw new LMSException("Books Not Found!! Please Try Again");
		} catch (NullPointerException e) {
			transaction.rollback();
			throw new LMSException("Book Not Found");
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Error Occured While Commiting the Transction");
		} finally {
			manager.close();
		}
	}

}
