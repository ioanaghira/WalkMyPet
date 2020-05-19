package session_2_java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Java8Main {

    public static void main(String[] args) {

        // Comparator 1
        Comparator<Employee> compareByNameJava7 = new Comparator<Employee>() {

            @Override
            public int compare(Employee employee1, Employee employee2) {

                return employee1.getName().compareTo(employee2.getName());
            }
        };

        // Comparator 2
        Comparator<Employee> compareByNameJava8 =
                (employee1, employee2) -> employee1.getName().compareTo(employee2.getName());

        // Comparator 3
        Comparator<Employee> easiestComparator =
                Comparator.comparing(Employee::getName);

        // Create a list
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(new Employee(500, "Fotbal", 15000));
        employeeList.add(new Employee(700, "Baschet", 25000));
        employeeList.add(new Employee(550, "Tenis", 35000));
        employeeList.add(new Employee(600, "Handbal", 45000));

        System.out.println("Initial list");
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }

        System.out.println("------------------------------------------------");

        // Lambda expression
        System.out.println("Java 8 initial list");
        employeeList.forEach(employee -> System.out.println(employee));

        System.out.println("------------------------------------------------");

        employeeList.forEach(employee -> {
            System.out.println("Test");
            System.out.println(employee);
        });

        System.out.println("------------------------------------------------");

        // Method reference
        employeeList.forEach(System.out::println);

        System.out.println("------------------------------------------------");

        // A more specific method
        employeeList.forEach(Java8Main::doSomething);
        System.out.println("------------------------------------------------");
        employeeList.forEach(employee -> doSomething2(employee, "test"));
        System.out.println("------------------------------------------------");

        // Sorting
        Collections.sort(employeeList, compareByNameJava7);
        System.out.println("Sorted list");
        employeeList.forEach(System.out::println);

        System.out.println("------------------------------------------------");

        // Sorting 2
        employeeList.sort(compareByNameJava8);

        // To be discussed: streams and operations

        minMaxCountExample();
        reducerExample();
        optionalExample();
    }

    // Wrapper
    private static void optionalExample() {

        Optional<String> gender = Optional.of("MALE");
        String answer1 = "Yes";
        String answer2 = null;

        System.out.println("Non empty Optional: " + gender);

        // Validation 1
        if (gender.isPresent()) {
            System.out.println("Optional print value version1: " + gender.get());
        }

        // Validation 2
        gender.ifPresent(value -> System.out.println("Optional print value version2: " + gender.get()));

        // Never catch an unchecked exception
        try {
            answer2.getBytes();
        } catch (NullPointerException ex) {

        }

        // Correct validation of NPE
        if (answer2 != null) {
            answer2.getBytes();
        }

        // Empty
        System.out.println("Empty optional: " + Optional.empty());

        // ofNullable
        System.out.println("Wrapper version1: " + Optional.ofNullable(answer1));
        System.out.println("Wrapper version2: " + Optional.ofNullable(answer2));
    }

    private static void reducerExample() {

        List<String> words = Arrays.asList("Test", "ABC", "NoName", "Abecedar");

        // iterate over list
        // .length
        // longestWord => String

        Optional<String> longestWord = words.stream()
                .reduce((word1, word2) ->
                        word1.length() > word2.length() ? word1 : word2);

        // Optional print
        System.out.println("Longest word [OPTIONAL]: " + longestWord);

        // Value print
        longestWord.ifPresent(value -> System.out.println("Longest word: " + value));

        System.out.println("------------------------------------------------");
    }

    private static void minMaxCountExample() {

        Optional<Integer> max = Stream.of(1, 3, 5, 2, 9, 8)
                .max(Comparator.comparing(Integer::valueOf));

        Optional<Integer> min = Stream.of(1, 3, 5, 2, 9, 8)
                .min(Comparator.comparing(Integer::valueOf));

        long count = Stream.of(1, 3, 5, 2, 9, 8)
                .count();

        Optional<Object> max2 = Stream.of()
                .max((o1, o2) -> o1.hashCode());

        System.out.println("Max number from the stream: " + max.get());
        System.out.println("Min number from the stream: " + min.get());
        System.out.println("Number of elements: " + count);

        System.out.println("No list elements -> empty: " + max2);

        System.out.println("------------------------------------------------");
    }

    private static void doSomething(Employee employee) {
        System.out.println("Test");
        System.out.println(employee);
    }

    private static void doSomething2(Employee employee, String test) {
        System.out.println(test);
        System.out.println(employee);
    }
}
