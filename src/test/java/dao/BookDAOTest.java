package dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.silvan.springcourse.dao.BookDAO;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Sql(value = {"/queries/bookCreation","/queries/bookDestruction","/queries/personCreation",
        "/queries/personDestruction","/queries/PersonExamples","/queries/BookExamples"})
@SuppressWarnings("unchecked")
public class BookDAOTest {
    @Mock
    private final JdbcTemplate jdbcTemplate;
    public BookDAOTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Mock
    private BookDAO bookDAO;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookDAO = new BookDAO(jdbcTemplate);
    }

    @Test
    public void indexShouldWorkProperly() {
        List<Book> expectedBooks = Arrays.asList(new Book("Orlando furioso", "Ludovico Ariosto", 1516),
                new Book("Tirant Lo Blanc", "Joanot Marturell", 1460));

        when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expectedBooks);

        List<Book> actualBooks = BookDAO.index();

        assertNotNull(actualBooks);
        assertEquals(2, actualBooks.size());
    }

    @Test
    public void bookShouldBeShown() {
        int book_id = 1;
        Book expectedBook = new Book("Orlando furioso", "Ludovico Ariosto", 1516);

        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Arrays.asList(expectedBook));

        Book actualBook = BookDAO.show(book_id);

        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void bookShouldBeSaved() {
        Book book = new Book("Orlando furioso", "Ludovico Ariosto", 1516);
        bookDAO.save(book);

        verify(jdbcTemplate).update(any(String.class), any(Object.class), any(Object.class));
    }

    @Test
    public void bookShouldBeUpdated() {
        int book_id = 1;
        Book updatedBook = new Book("Orlando furioso", "Ludovico Ariosto", 1516);
        bookDAO.update(book_id, updatedBook);

        verify(jdbcTemplate).update(any(String.class), any(Object.class), any(Object.class), any(Object.class));
    }

    @Test
    public void bookShouldBeDeleted() {
        int book_id = 1;
        bookDAO.delete(book_id);

        verify(jdbcTemplate).update(any(String.class), any(Object.class));
    }

    @Test
    public void bookShouldBeReleasedFromAPerson(){
        int book_id = 1;
        bookDAO.release(book_id);
        verify(jdbcTemplate).update("UPDATE Book SET person_id = NULL WHERE book_id = ?", book_id);
    }

    @Test
    public void bookShouldBeAssignedToAPerson(){
        int book_id = 1;
        Person person = new Person("Bazanov Alexey Olegovich", 1990 );
        bookDAO.assign(book_id, person);
        verify(jdbcTemplate).update("UPDATE Book SET person_id = ? WHERE book_id = ?", person.getId(), book_id);
    }
}
