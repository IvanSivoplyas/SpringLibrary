package models;

import org.junit.Test;
import ru.silvan.springcourse.models.Book;

import static org.junit.Assert.assertEquals;

public class BookTest {
    @Test
    public void constructorShouldWorkProperly() {
        Book book = new Book("Harry Potter and the philosopher`s stone", "Joanne Rowling", 1997);
        assertEquals("Harry Potter and the philosopher`s stone", book.getTitle());
        assertEquals("Joanne Rowling", book.getAuthor());
        assertEquals(1997, book.getYear());
    }

    @Test
    public void settersAndGettersShouldWorkProperly() {
        Book book = new Book();
        book.setTitle("Lord of the Rings");
        book.setAuthor("John Ronald Ruel Tolkien");
        book.setYear(1954);
        assertEquals("Lord of the Rings", book.getTitle());
        assertEquals("John Ronald Ruel Tolkien", book.getAuthor());
        assertEquals(1955, book.getYear());
    }

    @Test
    public void toStringShouldWorkProperly() {
        Book book = new Book("1984", "George Orwell", 1949);
        assertEquals("Book{bookId=?, title='1984', author='George Orwell', year=1949}", book.toString());
    }
}
