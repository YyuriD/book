package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import telran.java51.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findByAuthorsName(String name) {
		TypedQuery<Book> query = em.createQuery("select b from Authors a join a.books b where a.name=?1", Book.class);
		query.setParameter(1, name);
		return query.getResultStream();
	}

	@Override
	public Stream<Book> findByPublisherPublisherName(String name) {
		TypedQuery<Book> query = em.createQuery("select b from Publisher p join p.books b where p.publisherName=?1", Book.class);
		query.setParameter(1, name);
		return query.getResultStream();
	}

	@Override
	public void deleteByAuthorsName(String name) {
		Query query = em.createQuery("delete from Book b join b.authors a where a.name=?1");
		query.setParameter(1, name);
		query.executeUpdate();
	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}

	@Override
	public void deleteById(String isbn) {
//		Book book = em.find(Book.class, isbn);
//		em.remove(book);
		Query query = em.createQuery("delete from Book b join fetch b.authors a where b.isbn=?1");
		query.setParameter(1, isbn);
		query.executeUpdate();
	}

}
