package session_1_io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class PeopleCSVReader {

    List<Person> readPeople(String fileName) {

        List<Person> people = new ArrayList<>();

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(fileName));

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");

                Person person = new Person(
                        tokens[0],
                        Integer.parseInt(tokens[1]),
                        Double.parseDouble(tokens[2])
                );

                people.add(person);

                line = bufferedReader.readLine();
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return people;
    }
}
