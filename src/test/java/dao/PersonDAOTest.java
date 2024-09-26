package dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Sql(value = {"/queries/bookCreation","/queries/bookDestruction","/queries/personCreation",
        "/queries/personDestruction","/queries/PersonExamples","/queries/BookExamples"})
@SuppressWarnings("unchecked")
public class PersonDAOTest {
    @Mock
    private final JdbcTemplate jdbcTemplate;
    public PersonDAOTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Mock
    private PersonDAO personDAO;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        personDAO = new PersonDAO(jdbcTemplate);
    }

    @Test
    public void indexShouldWorkProperly() {
        List<Person> expectedPersons = Arrays.asList(new Person("Bazanov Alexey Olegovich", 1990),
                new Person("Fedunkiv Marina Semyonovna", 1980));

        when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expectedPersons);

        List<Person> actualPersons = personDAO.index();

        assertNotNull(actualPersons);
        assertEquals(2, actualPersons.size());
    }

    @Test
    public void personShouldBeShown() {
        int id = 1;
        Person expectedPerson = new Person("Bazanov Alexey Olegovich", 1990);

        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Arrays.asList(expectedPerson));

        Person actualPerson = personDAO.show(id);

        assertNotNull(actualPerson);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void personShouldBeSaved() {
        Person person = new Person("Bazanov Alexey Olegovich", 1990);
        personDAO.save(person);

        verify(jdbcTemplate).update(any(String.class), any(Object.class), any(Object.class));
    }

    @Test
    public void personShouldBeUpdated() {
        int id = 1;
        Person updatedPerson = new Person("Bazanov Alexey Olegovich0", 1990);
        personDAO.update(id, updatedPerson);

        verify(jdbcTemplate).update(any(String.class), any(Object.class), any(Object.class), any(Object.class));
    }

    @Test
    public void personShouldBeDeleted() {
        int id = 1;
        personDAO.delete(id);

        verify(jdbcTemplate).update(any(String.class), any(Object.class));
    }

    @Test
    public void personShouldBeAcquiredByFullName() {
        String fullName = "Bazanov Alexey Olegovich";
        Person expectedPerson = new Person("Bazanov Alexey Olegovich", 1990);

        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class)))
                .thenReturn(Arrays.asList(expectedPerson));

        Optional<Person> actualPerson = personDAO.getPersonByFullName(fullName);

        assertEquals(Optional.of(expectedPerson), actualPerson);
    }

    @Test
    public void bookShouldBeAcquiredByPersonId() {
        int id = 1;
        List<Book> expectedBooks = Arrays.asList(new Book("Orlando furioso", "Ludovico Ariosto", 1516),
                new Book("Tirant Lo Blanc", "Joanot Marturell", 1460));

        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(RowMapper.class)))
                .thenReturn(expectedBooks);

        List<Book> actualBooks = personDAO.getBooksByPersonId(id);

        assertNotNull(actualBooks);
        assertEquals(2, actualBooks.size());
    }
}
