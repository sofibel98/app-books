package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class BookService {

    @Inject
    EntityManager entityManager;


    public List<Book> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> rootEntry = cq.from(Book.class);
        CriteriaQuery<Book> all = cq.select(rootEntry);
        TypedQuery<Book> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public Book findById(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new EntityNotFoundException("Book with id " + id + " not found.");
        }
        return book;
    }

    public Response delete(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.getTransaction().begin();

        entityManager.remove(book);

        entityManager.getTransaction().commit();
        entityManager.close();

        return Response.status(Response.Status.NO_CONTENT).build();

    }

    public Book create(Book book) {

        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();

        return book;
    }

    public Book update(Book book) {
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();

        return book;

    }
}