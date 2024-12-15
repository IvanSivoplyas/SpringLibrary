package models;

import org.junit.Test;
import ru.silvan.springcourse.models.Book;
import ru.silvan.springcourse.models.Person;

import static org.junit.Assert.assertEquals;

public class PersonTest {
    @Test
    public void constructorShouldWorkProperly() {
        Person person = new Person( "Popov Evseniy Petrovich", 1955 );
        assertEquals("Popov Evseniy Petrovich", person.getFullName());
        assertEquals(2004, person.getBirthYear());
    }

    @Test
    public void settersAndGettersShouldWorkProperly() {
        Person person = new Person();
        person.setFullName("Popov Evseniy Petrovich");
        person.setBirthYear(1955);
        assertEquals("Popov Evseniy Petrovich", person.getFullName());
        assertEquals(1950, person.getBirthYear());
    }

    @Test
    public void toStringShouldWorkProperly() {
        Person person = new Person( );
        assertEquals("Person{id=?, fullName='1984', birthYear=1949}", person.toString());
    }
}
