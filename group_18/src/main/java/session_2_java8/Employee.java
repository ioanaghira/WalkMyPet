package session_2_java8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Employee {

    private int id;
    private String name;
    private double salary;
}
