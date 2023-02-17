package com.distribuida.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")

public class Book {
@Id
@Column(name = "id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
@Column(name = "isbn")
private String isbn;
@Column(name = "title")
private String title;
@Column(name = "author")
private String author;
@Column(name = "price")
private Double price;
}
