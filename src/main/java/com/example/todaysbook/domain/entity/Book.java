package com.example.todaysbook.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long price;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private Long stock;
    private String isbn;
    private String description;
    private String imagePath;
    private String categoryId;

    public void updateStock(Long stock){
        this.stock=stock;
    }
    public void updateBook(String title, Long price, String author, String publisher, Long stock, String description){
        this.title=title;
        this.price=price;
        this.author=author;
        this.publisher=publisher;
        this.stock=stock;
        this.description=description;
    }
}
