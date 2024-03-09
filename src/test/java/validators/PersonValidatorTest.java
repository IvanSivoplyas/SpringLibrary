package validators;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.silvan.springcourse.dao.PersonDAO;
import ru.silvan.springcourse.models.Person;
import ru.silvan.springcourse.validators.PersonValidator;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonValidatorTest {

    @Test
    public void testValidate() {
        PersonDAO personDAO = mock(PersonDAO.class);
        PersonValidator personValidator = new PersonValidator(personDAO);
        Person person = new Person("Barbaris Alexander Alexandrovich ", 2003);

        when(personDAO.getPersonByFullName(person.getFullName())).thenReturn(Optional.empty());

        Errors errors = new BeanPropertyBindingResult(person, "person");
        personValidator.validate(person, errors);

        assertEquals(0, errors.getErrorCount());
    }
}