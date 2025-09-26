import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GradebookTest {

    private Gradebook gradebook;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        gradebook = new Gradebook();
        student1 = new Student("Иван", "Иванов", "ПМ-2541");
        student2 = new Student("Петр", "Петров", "ПМ-2541");
        student3 = new Student("Анна", "Сидорова", "ПМ-2542");
    }

    // Тесты добавления студентов
    @Test
    void testAddStudentWithParameters() {
        Student addedStudent = gradebook.addStudent("Иван", "Иванов", "ПМ-2541");

        assertEquals("Иван", addedStudent.firstName());
        assertEquals("Иванов", addedStudent.lastName());
        assertEquals("ПМ-2541", addedStudent.group());

        // Проверяем, что студент действительно добавлен
        assertEquals(1, gradebook.listStudents().size());
        assertTrue(gradebook.listStudents().contains(addedStudent));
    }

    @Test
    void testAddStudentWithObject() {
        Student addedStudent = gradebook.addStudent(student1);

        assertEquals(student1, addedStudent);
        assertEquals(1, gradebook.listStudents().size());
    }

    @Test
    void testAddDuplicateStudent() {
        Student firstAdd = gradebook.addStudent(student1);
        Student secondAdd = gradebook.addStudent(student1);
        Student thirdAdd = gradebook.addStudent(new Student("Иван", "Иванов", "ПМ-2541"));

        // Все должны возвращать один и тот же объект
        assertSame(firstAdd, secondAdd);
        assertSame(firstAdd, thirdAdd);
        assertEquals(1, gradebook.listStudents().size());
    }

    @Test
    void testAddStudentWithNullParameters() {
        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent(null, "Иванов", "ПМ-2541"));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent("Иван", null, "ПМ-2541"));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent("Иван", "Иванов", null));
    }

    @Test
    void testAddStudentWithBlankParameters() {
        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent("", "Иванов", "ПМ-2541"));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent("Иван", "   ", "ПМ-2541"));
    }

    @Test
    void testAddNullStudent() {
        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addStudent(null));
    }

    // Тесты поиска студентов
    @Test
    void testFindExistingStudent() {
        gradebook.addStudent(student1);
        gradebook.addStudent(student2);

        Student found = gradebook.findStudent("Иван", "Иванов", "ПМ-2541");

        assertNotNull(found);
        assertEquals(student1, found);
    }

    @Test
    void testFindNonExistingStudent() {
        gradebook.addStudent(student1);

        Student found = gradebook.findStudent("Несуществующий", "Студент", "Группа");

        assertNull(found);
    }

    @Test
    void testFindStudentWithNullParameters() {
        assertThrows(IllegalArgumentException.class, () ->
                gradebook.findStudent(null, "Иванов", "ПМ-2541"));
    }

    // Тесты списка студентов
    @Test
    void testListStudentsEmpty() {
        List<Student> students = gradebook.listStudents();

        assertNotNull(students);
        assertTrue(students.isEmpty());
    }

    @Test
    void testListStudentsMultiple() {
        gradebook.addStudent(student1);
        gradebook.addStudent(student2);

        List<Student> students = gradebook.listStudents();

        assertEquals(2, students.size());
        assertTrue(students.contains(student1));
        assertTrue(students.contains(student2));
    }

    @Test
    void testListStudentsReturnsCopy() {
        gradebook.addStudent(student1);
        List<Student> students = gradebook.listStudents();

        students.clear(); // Не должно влиять на внутреннее состояние

        assertEquals(1, gradebook.listStudents().size());
    }

    // Тесты добавления оценок
    @Test
    void testAddGrade() {
        gradebook.addStudent(student1);

        gradebook.addGrade(student1, "Математика", 5);
        gradebook.addGrade(student1, "Программирование", 4);

        // Проверяем через фильтрацию
        List<Student> studentsWith4 = gradebook.getStudents4();
        assertEquals(1, studentsWith4.size());
        assertEquals(student1, studentsWith4.get(0));
    }

    @Test
    void testAddGradeToNonExistingStudent() {
        // Студент не добавлен в журнал
        gradebook.addGrade(student1, "Математика", 5);

        // Не должно быть исключения, но и не должно добавить оценку
        assertTrue(gradebook.getStudents5().isEmpty());
    }

    @Test
    void testAddGradeWithInvalidGrade() {
        gradebook.addStudent(student1);

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(student1, "Математика", 1));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(student1, "Математика", 6));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(student1, "Математика", 0));
    }

    @Test
    void testAddGradeWithNullParameters() {
        gradebook.addStudent(student1);

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(null, "Математика", 5));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(student1, null, 5));

        assertThrows(IllegalArgumentException.class, () ->
                gradebook.addGrade(student1, "", 5));
    }

    @Test
    void testAddGradeOverwriteExisting() {
        gradebook.addStudent(student1);

        gradebook.addGrade(student1, "Математика", 4);
        gradebook.addGrade(student1, "Математика", 5); // Перезапись

        List<Student> excellentStudents = gradebook.getStudents5();
        assertEquals(1, excellentStudents.size());
        assertEquals(student1, excellentStudents.get(0));
    }

    // Тесты удаления оценок
    @Test
    void testRemoveGrade() {
        gradebook.addStudent(student1);
        gradebook.addGrade(student1, "Математика", 4);
        gradebook.addGrade(student1, "Программирование", 3);

        gradebook.removeGrade(student1, "Математика");

        // Теперь у студента должна быть только тройка
        List<Student> studentsWith3 = gradebook.getStudents3();
        assertEquals(1, studentsWith3.size());
        assertEquals(student1, studentsWith3.get(0));

        assertTrue(gradebook.getStudents4().isEmpty());
    }

    @Test
    void testRemoveNonExistingGrade() {
        gradebook.addStudent(student1);

        // Не должно быть исключения
        gradebook.removeGrade(student1, "Несуществующий предмет");
    }

    @Test
    void testRemoveGradeFromNonExistingStudent() {
        // Не должно быть исключения
        gradebook.removeGrade(student1, "Математика");
    }

    // Тесты фильтрации студентов по оценкам
    @Test
    void testGetStudents5_AllExcellent() {
        gradebook.addStudent(student1);
        gradebook.addStudent(student2);

        gradebook.addGrade(student1, "Математика", 5);
        gradebook.addGrade(student1, "Физика", 5);
        gradebook.addGrade(student2, "Математика", 4); // Не отличник

        List<Student> excellentStudents = gradebook.getStudents5();

        assertEquals(1, excellentStudents.size());
        assertEquals(student1, excellentStudents.get(0));
    }

    @Test
    void testGetStudents5_EmptyWhenNoGrades() {
        gradebook.addStudent(student1);

        List<Student> excellentStudents = gradebook.getStudents5();

        assertTrue(excellentStudents.isEmpty());
    }

    @Test
    void testGetStudents4_GoodStudentsWithAtLeastOne4() {
        gradebook.addStudent(student1); // Хорошист
        gradebook.addStudent(student2); // Отличник
        gradebook.addStudent(student3); // Троечник

        gradebook.addGrade(student1, "Математика", 4);
        gradebook.addGrade(student1, "Физика", 5);
        gradebook.addGrade(student2, "Математика", 5);
        gradebook.addGrade(student2, "Физика", 5);
        gradebook.addGrade(student3, "Математика", 3);
        gradebook.addGrade(student3, "Физика", 4);

        List<Student> goodStudents = gradebook.getStudents4();

        assertEquals(1, goodStudents.size());
        assertEquals(student1, goodStudents.get(0));
        // student3 не подходит, т.к. есть тройка
        // student2 не подходит, т.к. нет четверки
    }

    @Test
    void testGetStudents3_AverageStudentsWithAtLeastOne3() {
        gradebook.addStudent(student1); // Хорошист с одной тройкой
        gradebook.addStudent(student2); // Отличник
        gradebook.addStudent(student3); // Троечник

        gradebook.addGrade(student1, "Математика", 4);
        gradebook.addGrade(student1, "Физика", 3);
        gradebook.addGrade(student2, "Математика", 5);
        gradebook.addGrade(student3, "Математика", 3);
        gradebook.addGrade(student3, "Физика", 3);

        List<Student> averageStudents = gradebook.getStudents3();

        assertEquals(2, averageStudents.size());
        assertTrue(averageStudents.contains(student1));
        assertTrue(averageStudents.contains(student3));
    }

    @Test
    void testGetStudents2_StudentsWithAtLeastOne2() {
        gradebook.addStudent(student1); // С двойкой
        gradebook.addStudent(student2); // Без двойки

        gradebook.addGrade(student1, "Математика", 2);
        gradebook.addGrade(student1, "Физика", 3);
        gradebook.addGrade(student2, "Математика", 3);

        List<Student> studentsWith2 = gradebook.getStudents2();

        assertEquals(1, studentsWith2.size());
        assertEquals(student1, studentsWith2.get(0));
    }

    @Test
    void testGetStudents0_StudentsWithoutGrades() {
        gradebook.addStudent(student1); // Без оценок
        gradebook.addStudent(student2); // С оценками

        gradebook.addGrade(student2, "Математика", 5);

        List<Student> studentsWithoutGrades = gradebook.getStudents0();

        assertEquals(1, studentsWithoutGrades.size());
        assertEquals(student1, studentsWithoutGrades.get(0));
    }

    @Test
    void testComplexScenario() {
        // Комплексный тест с несколькими студентами
        gradebook.addStudent(student1);
        gradebook.addStudent(student2);
        gradebook.addStudent(student3);

        // student1: 5, 5, 5 → отличник
        gradebook.addGrade(student1, "Математика", 5);
        gradebook.addGrade(student1, "Физика", 5);
        gradebook.addGrade(student1, "Программирование", 5);

        // student2: 5, 4, 5 → хорошист
        gradebook.addGrade(student2, "Математика", 5);
        gradebook.addGrade(student2, "Физика", 4);
        gradebook.addGrade(student2, "Программирование", 5);

        // student3: 3, 4, 2 → с двойкой
        gradebook.addGrade(student3, "Математика", 3);
        gradebook.addGrade(student3, "Физика", 4);
        gradebook.addGrade(student3, "Программирование", 2);

        // Проверяем все категории
        assertEquals(1, gradebook.getStudents5().size()); // student1
        assertEquals(1, gradebook.getStudents4().size()); // student2
        assertEquals(0, gradebook.getStudents3().size()); // student3 не подходит из-за двойки
        assertEquals(1, gradebook.getStudents2().size()); // student3
        assertEquals(0, gradebook.getStudents0().size()); // все имеют оценки

        // Удаляем двойку у student3
        gradebook.removeGrade(student3, "Программирование");

        // Теперь student3 становится троечником
        assertEquals(1, gradebook.getStudents3().size()); // student3
        assertEquals(0, gradebook.getStudents2().size()); // двоек нет
    }

    @Test
    void testBoundaryGrades() {
        gradebook.addStudent(student1);

        // Проверяем все допустимые оценки
        gradebook.addGrade(student1, "subject1", 2);
        gradebook.addGrade(student1, "subject2", 3);
        gradebook.addGrade(student1, "subject3", 4);
        gradebook.addGrade(student1, "subject4", 5);

        // Должен быть в категории с двойками
        assertEquals(1, gradebook.getStudents2().size());
    }
}