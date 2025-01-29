package studentdatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDatabaseApp {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/university";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the month (1-12) to find students born in that month:");
            int month = scanner.nextInt();

            if (month < 1 || month > 12) {
                System.out.println("Invalid month. Please enter a number between 1 and 12.");
                return;
            }

            // Option 1: Fetch all students and filter in Java
            List<Student> students = fetchAllStudents();
            System.out.println("Students born in month " + month + ":");
            students.stream()
                    .filter(student -> student.getBirthMonth() == month)
                    .forEach(System.out::println);

            // Option 2: Fetch filtered data from database
            System.out.println("Fetching directly from the database:");
            List<Student> filteredStudents = fetchStudentsByMonth(month);
            filteredStudents.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Student> fetchAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {

            while (resultSet.next()) {
                students.add(mapToStudent(resultSet));
            }
        }
        return students;
    }

    private static List<Student> fetchStudentsByMonth(int month) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE MONTH(date_of_birth) = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, month);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapToStudent(resultSet));
                }
            }
        }
        return students;
    }

    private static Student mapToStudent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");
        String patronymic = resultSet.getString("patronymic");
        Date dateOfBirth = resultSet.getDate("date_of_birth");
        String recordBookNumber = resultSet.getString("record_book_number");
        return new Student(id, lastName, firstName, patronymic, dateOfBirth, recordBookNumber);
    }
}

class Student {
    private final int id;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final Date dateOfBirth;
    private final String recordBookNumber;

    public Student(int id, String lastName, String firstName, String patronymic, Date dateOfBirth, String recordBookNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.recordBookNumber = recordBookNumber;
    }

    public int getBirthMonth() {
        return dateOfBirth.toLocalDate().getMonthValue();
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, lastName='%s', firstName='%s', patronymic='%s', dateOfBirth=%s, recordBookNumber='%s'}",
                id, lastName, firstName, patronymic, dateOfBirth, recordBookNumber);
    }
}
