import org.example.InvalidPersonDataException;
import org.example.Person;
import org.example.PersonReader;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class PersonReaderTest {

    @Test
    public void testReadValidPersons() {
        List<Person> persons = PersonReader.readPersons("persons.json");

        assertNotNull(persons);
        assertEquals(2, persons.size());

        Person firstPerson = persons.get(0);
        assertEquals("Ivan", firstPerson.getFirstName());
        assertEquals("Ivanov", firstPerson.getSurName());
        assertEquals("9111234567", firstPerson.getPhone());
        assertEquals(22, firstPerson.getAge());

        Person secondPerson = persons.get(1);
        assertEquals("Maria", secondPerson.getFirstName());
        assertEquals("Petrova", secondPerson.getSurName());
        assertEquals("9123456789", secondPerson.getPhone());
        assertEquals(25, secondPerson.getAge());
    }

    @Test(expected = InvalidPersonDataException.class)
    public void testInvalidPhoneFormat() {
        PersonReader.readPersons("invalid_phone.json");
    }

    @Test(expected = InvalidPersonDataException.class)
    public void testInvalidAge() {
        PersonReader.readPersons("invalid_age.json");
    }

    @Test(expected = InvalidPersonDataException.class)
    public void testEmptyName() {
        PersonReader.readPersons("empty_name.json");
    }

    @Test(expected = InvalidPersonDataException.class)
    public void testMissingField() {
        PersonReader.readPersons("missing_field.json");
    }

    @Test
    public void testPhoneTransformation() {
        // Test various phone formats
        String[] testPhones = {
                " +7(911)123-45-67",
                "8(911)123-45-67",
                "+7(911)1234567",
                "8(911)1234567"
        };

        for (String phone : testPhones) {
            try {
                PersonReader reader = new PersonReader();
                java.lang.reflect.Method method = PersonReader.class.getDeclaredMethod("validateAndTransformPhone", String.class);
                method.setAccessible(true);
                String result = (String) method.invoke(null, phone);
                assertEquals("9111234567", result);
            } catch (Exception e) {
                fail("Phone transformation failed for: " + phone);
            }
        }
    }
}