package university;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Model package remains the same, but with added equals and hashCode methods

class JsonManager {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void writeToFile(University university, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), university);
    }

    public static University readFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), University.class);
    }

    public static String convertToJson(University university) throws JsonProcessingException {
        return objectMapper.writeValueAsString(university);
    }

    public static University convertFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, University.class);
    }
}

// Extend model classes to support equality
package university.model;

import java.util.Objects;

abstract class Human {
    // Existing fields and methods...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return firstName.equals(human.firstName) &&
                lastName.equals(human.lastName) &&
                patronymic.equals(human.patronymic) &&
                sex == human.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, sex);
    }
}

class UniversityUnit {
    // Existing fields and methods...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityUnit that = (UniversityUnit) o;
        return name.equals(that.name) && head.equals(that.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, head);
    }
}

// Apply similar equals and hashCode methods to other model classes

// JUnit Test
package university.test;

import org.junit.jupiter.api.Test;
import university.controller.Run;
import university.model.University;
import university.JsonManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniversityJsonTest {

    @Test
    void testUniversitySerialization() throws IOException {
        // Create oldUniversity with nested structure
        University oldUniversity = Run.createTypicalUniversity();
        String filePath = "university.json";

        // Serialize and write to file
        JsonManager.writeToFile(oldUniversity, filePath);

        // Read from file and deserialize
        University newUniversity = JsonManager.readFromFile(filePath);

        // Compare old and new
        assertEquals(oldUniversity, newUniversity);
    }
}
