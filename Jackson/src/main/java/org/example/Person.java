package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Person {
    private final String firstName;
    private final String surName;
    private final String phone;
    private final byte age;

    public Person(String firstName, String surName, String phone, byte age) {
        this.firstName = Objects.requireNonNull(firstName, "First name cannot be null");
        this.surName = Objects.requireNonNull(surName, "Surname cannot be null");
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(surName, person.surName) &&
                Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surName, phone, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                '}';
    }
}