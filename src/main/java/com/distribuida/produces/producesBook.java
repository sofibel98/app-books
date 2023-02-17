package com.distribuida.produces;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/books")
@ApplicationScoped
public class producesBook {

    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") int id) {
        Book book = bookService.findById(id);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        try {
            bookService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Book book) {
        try {
            Book result = bookService.create(book);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update  book by id",
            description = "This is used to update a  book.")
    @APIResponse(description = "Simple JSON containing the greeting",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class)))
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void update(@PathParam("id") int id, Book bookUpdate) {
        Book book = bookService.findById(id);
        if (book != null) {
            book.setAuthor(bookUpdate.getAuthor());
            book.setTitle(bookUpdate.getTitle());
            book.setPrice(bookUpdate.getPrice());
            book.setIsbn(bookUpdate.getIsbn());
            bookService.update(book);
        } else {
            throw new EntityNotFoundException("Book not found");
        }

    }
}