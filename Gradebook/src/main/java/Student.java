public record Student(String firstName, String lastName, String group) {

    public Student {
        if (firstName == null || lastName == null || group == null) {
            throw new IllegalArgumentException("Все поля студента должны быть не null");
        }
    }

    public String toString(){
        return firstName + " " + lastName + " " + group;
    }
}