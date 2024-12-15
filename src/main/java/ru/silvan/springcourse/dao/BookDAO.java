package ru.silvan.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public static Book show(int book_id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?",
                new Object[]{book_id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title,author,year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear());
    }
    public void update(int book_id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE book_id = ?",
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getYear(),
                book_id);
    }
    public void delete(int book_id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", book_id);
    }

    public Optional<Person> getBookOwner(int book_id){
        return jdbcTemplate.query("SELECT Person. * FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.book_id = ?",
                new Object[]{book_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int book_id){
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE book_id = ?",book_id);
    }
    public void assign(int book_id,Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?",
                selectedPerson.getId(),
                book_id);
    }
}
