package ru.silvan.springcourse.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Book {
    private int bookId;
    @NotBlank(message = "У книги должно быть указано название")
    private String title;
    @NotBlank(message = "У книги должен быть указан автор")
    private String author;
    @NotNull(message = "У книги должен быть указан год")
    private int year;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
