package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.silvan.springcourse.controllers.BookController;
import ru.silvan.springcourse.dao.BookDAO;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BookControllerTest {
    @Mock
    private BookDAO bookDAO;
    @Mock
    private PersonDAO personDAO;
    @InjectMocks
    private BookController bookController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(personDAO,bookDAO);
    }

    @Test
    public void indexShouldWorkProperly() {
        Model model = mock(Model.class);

        assertEquals("books/index", bookController.index(model));
        verify(model, times(1)).addAttribute(eq("books"), any());
    }

    @Test
    public void showShouldWorkProperly() {
        Model model = mock(Model.class);
        Person person = new Person(); // Создание объекта Person

        int book_id = 1;

        String viewName = bookController.show(book_id, model, person);

        assertEquals("books/show", viewName); // Исправлено ожидаемое значение

        // Проверка добавления всех атрибутов в модель
        verify(model, times(1)).addAttribute(eq("book"), any());
        verify(model, times(1)).addAttribute(eq("owner"), any());
        verify(model, times(1)).addAttribute(eq("people"), any());
    }

    @Test
    public void newBookShouldBeCreatedProperly() {
        Book book = new Book();
        String viewName = bookController.newBook(book);
        assertEquals("books/new", viewName);
    }

    @Test
    public void bookShouldBeCreated() {
        Book book = new Book();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = bookController.create(book, bindingResult);

        verify(bookDAO).save(eq(book));
        assertEquals("redirect:/books", viewName);
    }

    @Test
    public void bookShouldBeEdited() {
        Model model = mock(Model.class);
        int book_id = 1;
        Book book = new Book("Tirant Lo Blanc", "Joanot Marturell", 1460);

        when(bookDAO.show(book_id)).thenReturn(book);

        String viewName = bookController.edit(model, book_id);

        verify(model).addAttribute("book", book);
        assertEquals("books/edit", viewName);
    }

    @Test
    public void bookShouldBeUpdated() {
        int book_id = 1;
        Book book = new Book("Tirant Lo Blanc", "Joanot Marturell", 1460);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = bookController.update(book, bindingResult, book_id);

        verify(bookDAO).update(eq(book_id), eq(book));
        assertEquals("redirect:/books", viewName);
    }

    @Test
    public void bookShouldBeDeleted() {
        int book_id = 1;
        String viewName = bookController.delete(book_id);

        verify(bookDAO).delete(book_id);
        assertEquals("redirect:/books", viewName);
    }
}
