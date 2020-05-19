package session_1_io;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private int age;
    private double height;

    Person(String name, int age, double height) {

        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "name: " + name + " of age:" + age;
    }
}
