import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreation() {
        Student student = new Student("Иван", "Иванов", "ПМ-2541");

        assertEquals("Иван", student.firstName());
        assertEquals("Иванов", student.lastName());
        assertEquals("ПМ-2541", student.group());
    }

    @Test
    void testEqualsAndHashCode() {
        Student student1 = new Student("Иван", "Иванов", "ПМ-2541");
        Student student2 = new Student("Иван", "Иванов", "ПМ-2541");
        Student student3 = new Student("Петр", "Иванов", "ПМ-2541");
        Student student4 = new Student("Иван", "Петров", "ПМ-2541");
        Student student5 = new Student("Иван", "Иванов", "ПМ-2542");

        // Проверка equals
        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
        assertNotEquals(student1, student4);
        assertNotEquals(student1, student5);

        // Проверка hashCode для равных объектов
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void testToString() {
        Student student = new Student("Иван", "Иванов", "ПМ-2541");
        String toStringResult = student.toString();

        assertTrue(toStringResult.contains("Иванов"));
        assertTrue(toStringResult.contains("Иван"));
        assertTrue(toStringResult.contains("ПМ-2541"));
    }
}