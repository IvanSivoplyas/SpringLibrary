package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.silvan.springcourse.controllers.PeopleController;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Person;
import ru.silvan.springcourse.validators.PersonValidator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PeopleControllerTest {
    @Mock
    private PersonDAO personDAO;
    @Mock
    private PersonValidator personValidator;
    @InjectMocks
    private PeopleController peopleController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        peopleController = new PeopleController(personDAO, personValidator);
    }

    @Test
    public void indexShouldWorkProperly() {
        Model model = mock(Model.class);

        assertEquals("people/index", peopleController.index(model));
        verify(model, times(1)).addAttribute(eq("people"), any());
    }

    @Test
    public void showShouldWorkProperly() {
        Model model = mock(Model.class);
        int id = 1;

        assertEquals("person/show", peopleController.show(id, model));
        verify(model, times(1)).addAttribute(eq("person"), any());
        verify(model, times(1)).addAttribute(eq("books"), any());
    }

    @Test
    public void newPersonShouldBeCreatedProperly() {
        Person person = new Person();
        String viewName = peopleController.newPerson(person);
        assertEquals("people/new", viewName);
    }

    @Test
    public void personShouldBeCreated() {
        Person person = new Person();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = peopleController.create(person, bindingResult);

        verify(personValidator).validate(eq(person), any());
        verify(personDAO).save(eq(person));
        assertEquals("redirect:/people", viewName);
    }

    @Test
    public void personShouldBeEdited() {
        Model model = mock(Model.class);
        int id = 1;
        Person person = new Person("Dronov Arkadiy Mikhailovich", 1985);

        when(personDAO.show(id)).thenReturn(person);

        String viewName = peopleController.edit(model, id);

        verify(model).addAttribute("person", person);
        assertEquals("people/edit", viewName);
    }

    @Test
    public void personShouldBeUpdated() {
        int id = 1;
        Person person = new Person( "Lapina Inna Vladimirovna", 1969);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = peopleController.update(person, bindingResult, id);

        verify(personDAO).update(eq(id), eq(person));
        assertEquals("redirect:/people", viewName);
    }

    @Test
    public void personShouldBeDeleted() {
        int id = 1;
        String viewName = peopleController.delete(id);

        verify(personDAO).delete(id);
        assertEquals("redirect:/people", viewName);
    }
}
