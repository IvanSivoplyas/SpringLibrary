package ru.silvan.springcourse.models;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Person {
    private int id;
    @NotEmpty(message = "У человека должно быть указано имя")
    private String fullName;
    @NotNull(message = "У человека должен быть указан год рождения")
    private int birthYear;

    public Person() { }

    public Person(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
