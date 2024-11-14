package jpabook.jpa_bookshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpa_bookshop.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;


    public static Book createBook(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        return book;
    }
}
