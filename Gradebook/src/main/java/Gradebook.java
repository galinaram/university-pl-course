import java.util.*;

public class Gradebook {
    private final Map<Student, Map<String, Integer>> journal = new HashMap<>();

    private void validateStudent(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Студент не может быть null");
        }
    }

    private void validateSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Название предмета не может быть пустым");
        }
    }

    private void validateGrade(int grade) {
        if (grade < 2 || grade > 5) {
            throw new IllegalArgumentException("Оценка должна быть 2, 3, 4 или 5");
        }
    }

    public Student addStudent(String firstName, String lastName, String group) {
        if (firstName == null || lastName == null || group == null ||
                firstName.isBlank() || lastName.isBlank() || group.isBlank()) {
            throw new IllegalArgumentException("Имя, фамилия и группа не могут быть пустыми");
        }
        Student newStudent = new Student(firstName, lastName, group);
        return addStudent(newStudent);
    }

    public Student addStudent(Student student) {
        validateStudent(student);
        journal.putIfAbsent(student, new HashMap<>());
        for (Student s : journal.keySet()) {
            if (s.equals(student)) {
                return s;
            }
        }
        return student;
    }

    public Student findStudent(String firstName, String lastName, String group) {
        Student target = new Student(firstName, lastName, group);
        for (Student s : journal.keySet()) {
            if (s.equals(target)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(journal.keySet());
    }

    public void addGrade(Student s, String subject, int grade) {
        validateStudent(s);
        validateSubject(subject);
        validateGrade(grade);

        Map<String, Integer> subjects = journal.get(s);
        if (subjects != null) {
            subjects.put(subject, grade);
        }
    }

    public void removeGrade(Student s, String subject) {
        validateStudent(s);
        validateSubject(subject);

        Map<String, Integer> subjects = journal.get(s);
        if (subjects != null) {
            subjects.remove(subject);
        }
    }

    public List<Student> getStudents5() {
        List<Student> result = new ArrayList<>();
        for (Map.Entry<Student, Map<String, Integer>> entry : journal.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> grades = entry.getValue();

            if (!grades.isEmpty()) {
                int minGrade = Collections.min(grades.values());
                if (minGrade == 5) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    public List<Student> getStudents4() {
        List<Student> result = new ArrayList<>();
        for (Map.Entry<Student, Map<String, Integer>> entry : journal.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> grades = entry.getValue();

            if (!grades.isEmpty()) {
                int minGrade = Collections.min(grades.values());
                if (minGrade >= 4 && grades.containsValue(4)) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    public List<Student> getStudents3() {
        List<Student> result = new ArrayList<>();
        for (Map.Entry<Student, Map<String, Integer>> entry : journal.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> grades = entry.getValue();

            if (!grades.isEmpty()) {
                int minGrade = Collections.min(grades.values());
                // Все оценки ≥ 3 и есть хотя бы одна тройка
                if (minGrade >= 3 && grades.containsValue(3)) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    public List<Student> getStudents2() {
        List<Student> result = new ArrayList<>();
        for (Map.Entry<Student, Map<String, Integer>> entry : journal.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> grades = entry.getValue();

            if (!grades.isEmpty()) {
                int minGrade = Collections.min(grades.values());
                // Все оценки ≥ 2 и есть хотя бы одна тройка
                if (minGrade >= 2 && grades.containsValue(2)) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    public List<Student> getStudents0() {
        List<Student> result = new ArrayList<>();
        for (Map.Entry<Student, Map<String, Integer>> entry : journal.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> grades = entry.getValue();

            if (grades.isEmpty()) {
                result.add(student);
            }
        }
        return result;
    }
}