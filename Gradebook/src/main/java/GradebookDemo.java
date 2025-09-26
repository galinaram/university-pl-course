import java.util.List;

public class GradebookDemo {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ GRADEBOOK ===\n");

        // Создаем журнал успеваемости
        Gradebook journal = new Gradebook();

        // 1. Зачисление студентов на курс
        System.out.println("1. ЗАЧИСЛЕНИЕ СТУДЕНТОВ НА КУРС 'ПРОГРАММИРОВАНИЕ':");
        System.out.println("---------------------------------------------------");

        Student ivanov = journal.addStudent("Иван", "Иванов", "ПМ-2541");
        Student petrov = journal.addStudent("Петр", "Петров", "ПМ-2541");
        Student sidorova = journal.addStudent("Мария", "Сидорова", "ПМ-2541");
        Student kuznetsov = journal.addStudent("Алексей", "Кузнецов", "ПМ-2542");
        Student smirnova = journal.addStudent("Анна", "Смирнова", "ПМ-2542");

        System.out.println("Зачислено студентов: " + journal.listStudents().size());
        System.out.println("Список всех студентов:");
        journal.listStudents().forEach(s -> System.out.println("  📘 " + s));
        System.out.println();

        // 2. Процесс обучения: выставление оценок
        System.out.println("2. УЧЕБНЫЙ ПРОЦЕСС - ВЫСТАВЛЕНИЕ ОЦЕНОК:");
        System.out.println("---------------------------------------");

        // Первая сессия
        System.out.println("🎓 Первая сессия:");
        journal.addGrade(ivanov, "Программирование", 5);
        journal.addGrade(ivanov, "Математика", 4);
        journal.addGrade(ivanov, "Английский язык", 5);

        journal.addGrade(petrov, "Программирование", 5);
        journal.addGrade(petrov, "Математика", 5);
        journal.addGrade(petrov, "Английский язык", 5);

        journal.addGrade(sidorova, "Программирование", 4);
        journal.addGrade(sidorova, "Математика", 3);
        journal.addGrade(sidorova, "Английский язык", 4);

        journal.addGrade(kuznetsov, "Программирование", 3);
        journal.addGrade(kuznetsov, "Математика", 3);
        journal.addGrade(kuznetsov, "Английский язык", 4);

        // Смирнова пока не сдала экзамены
        System.out.println("   Оценки выставлены для 4 студентов");
        System.out.println();

        // 3. Анализ успеваемости после первой сессии
        System.out.println("3. АНАЛИЗ УСПЕВАЕМОСТИ ПОСЛЕ ПЕРВОЙ СЕССИИ:");
        System.out.println("------------------------------------------");

        printStudentCategory("🏆 Отличники (все 5):", journal.getStudents5());
        printStudentCategory("👍 Хорошисты (все ≥4, есть 4):", journal.getStudents4());
        printStudentCategory("✅ Троечники (все ≥3, есть 3):", journal.getStudents3());
        printStudentCategory("⚠️  Студенты с двойками:", journal.getStudents2());
        printStudentCategory("📋 Студенты без оценок:", journal.getStudents0());
        System.out.println();

        // 4. Вторая сессия - изменения в успеваемости
        System.out.println("4. ВТОРАЯ СЕССИЯ - ИЗМЕНЕНИЯ В УСПЕВАЕМОСТИ:");
        System.out.println("--------------------------------------------");

        // Смирнова сдала экзамены
        System.out.println("📈 Анна Смирнова сдала экзамены:");
        journal.addGrade(smirnova, "Программирование", 4);
        journal.addGrade(smirnova, "Математика", 4);
        journal.addGrade(smirnova, "Английский язык", 5);

        // Сидорова улучшила оценку по математике
        System.out.println("📈 Мария Сидорова улучшила оценку по математике:");
        journal.addGrade(sidorova, "Математика", 4); // Была 3, стала 4

        // Кузнецов получил двойку по программированию
        System.out.println("📉 Алексей Кузнецов получил двойку по программированию:");
        journal.addGrade(kuznetsov, "Программирование", 2); // Была 3, стала 2
        System.out.println();

        // 5. Итоговый анализ успеваемости
        System.out.println("5. ИТОГОВЫЙ АНАЛИЗ УСПЕВАЕМОСТИ:");
        System.out.println("--------------------------------");

        printStudentCategory("🏆 Отличники:", journal.getStudents5());
        printStudentCategory("👍 Хорошисты:", journal.getStudents4());
        printStudentCategory("✅ Троечники:", journal.getStudents3());
        printStudentCategory("⚠️  Студенты с двойками:", journal.getStudents2());
        printStudentCategory("📋 Студенты без оценок:", journal.getStudents0());
        System.out.println();

        // 6. Работа с индивидуальными студентами
        System.out.println("6. ИНДИВИДУАЛЬНАЯ РАБОТА СО СТУДЕНТАМИ:");
        System.out.println("--------------------------------------");

        // Поиск студента
        Student foundStudent = journal.findStudent("Алексей", "Кузнецов", "ПМ-2542");
        if (foundStudent != null) {
            System.out.println("🔍 Найден студент: " + foundStudent);
            System.out.println("   Удаляем двойку по программированию (пересдача)");
            journal.removeGrade(foundStudent, "Программирование");
        }

        // Добавляем новую оценку после пересдачи
        journal.addGrade(foundStudent, "Программирование", 4);
        System.out.println("   Новая оценка по программированию: 4");
        System.out.println();

        // 7. Финальная статистика
        System.out.println("7. ФИНАЛЬНАЯ СТАТИСТИКА КУРСА:");
        System.out.println("-----------------------------");

        System.out.println("📊 Общее количество студентов: " + journal.listStudents().size());

        int excellentCount = journal.getStudents5().size();
        int goodCount = journal.getStudents4().size();
        int averageCount = journal.getStudents3().size();
        int failedCount = journal.getStudents2().size();
        int noGradesCount = journal.getStudents0().size();

        System.out.println("🏆 Отличников: " + excellentCount);
        System.out.println("👍 Хорошистов: " + goodCount);
        System.out.println("✅ Троечников: " + averageCount);
        System.out.println("⚠️  С двойками: " + failedCount);
        System.out.println("📋 Без оценок: " + noGradesCount);

        double successRate = (double) (excellentCount + goodCount + averageCount) / journal.listStudents().size() * 100;
        System.out.printf("📈 Общая успеваемость: %.1f%%\n", successRate);
        System.out.println();

        // 8. Детальная информация по каждому студенту
        System.out.println("8. ДЕТАЛЬНАЯ ИНФОРМАЦИЯ ПО СТУДЕНТАМ:");
        System.out.println("-----------------------------------");

        for (Student student : journal.listStudents()) {
            System.out.println("👤 " + student);
            Student found = journal.findStudent(student.firstName(), student.lastName(), student.group());
            // В реальной реализации здесь можно добавить метод для получения всех оценок студента
            String status = getStudentStatus(journal, student);
            System.out.println("   Статус: " + status);
        }

        System.out.println("\n=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }

    private static void printStudentCategory(String title, List<Student> students) {
        System.out.println(title);
        if (students.isEmpty()) {
            System.out.println("   — нет студентов");
        } else {
            for (Student student : students) {
                System.out.println("   👨‍🎓 " + student);
            }
        }
    }

    private static String getStudentStatus(Gradebook journal, Student student) {
        if (!journal.getStudents5().isEmpty() && journal.getStudents5().contains(student)) {
            return "Отличник";
        } else if (!journal.getStudents4().isEmpty() && journal.getStudents4().contains(student)) {
            return "Хорошист";
        } else if (!journal.getStudents3().isEmpty() && journal.getStudents3().contains(student)) {
            return "Троечник";
        } else if (!journal.getStudents2().isEmpty() && journal.getStudents2().contains(student)) {
            return "Есть двойки";
        } else if (!journal.getStudents0().isEmpty() && journal.getStudents0().contains(student)) {
            return "Без оценок";
        }
        return "Не определен";
    }
}
