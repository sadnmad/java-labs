package university;

// Model package
package university.model;

import java.util.ArrayList;
import java.util.List;

abstract class Human {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Sex sex;

    public Human(String firstName, String lastName, String patronymic, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.sex = sex;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPatronymic() { return patronymic; }
    public Sex getSex() { return sex; }

    @Override
    public String toString() {
        return firstName + " " + patronymic + " " + lastName;
    }
}

enum Sex {
    MALE, FEMALE
}

class Student extends Human {
    public Student(String firstName, String lastName, String patronymic, Sex sex) {
        super(firstName, lastName, patronymic, sex);
    }
}

class UniversityUnit {
    private String name;
    private Human head;

    public UniversityUnit(String name, Human head) {
        this.name = name;
        this.head = head;
    }

    public String getName() { return name; }
    public Human getHead() { return head; }

    @Override
    public String toString() {
        return name + " (Head: " + head + ")";
    }
}

class Group extends UniversityUnit {
    private List<Student> students = new ArrayList<>();

    public Group(String name, Human head) {
        super(name, head);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }
}

class Department extends UniversityUnit {
    private List<Group> groups = new ArrayList<>();

    public Department(String name, Human head) {
        super(name, head);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Group> getGroups() {
        return groups;
    }
}

class Faculty extends UniversityUnit {
    private List<Department> departments = new ArrayList<>();

    public Faculty(String name, Human head) {
        super(name, head);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public List<Department> getDepartments() {
        return departments;
    }
}

class University extends UniversityUnit {
    private List<Faculty> faculties = new ArrayList<>();

    public University(String name, Human head) {
        super(name, head);
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }
}

// Controller package
package university.controller;

import university.model.*;

class HumanCreator {
    public static Human createHuman(String firstName, String lastName, String patronymic, Sex sex) {
        return new Human(firstName, lastName, patronymic, sex) {};
    }
}

class StudentCreator {
    public static Student createStudent(String firstName, String lastName, String patronymic, Sex sex) {
        return new Student(firstName, lastName, patronymic, sex);
    }
}

class GroupCreator {
    public static Group createGroup(String name, Human head) {
        return new Group(name, head);
    }
}

class DepartmentCreator {
    public static Department createDepartment(String name, Human head) {
        return new Department(name, head);
    }
}

class FacultyCreator {
    public static Faculty createFaculty(String name, Human head) {
        return new Faculty(name, head);
    }
}

class UniversityCreator {
    public static University createUniversity(String name, Human head) {
        return new University(name, head);
    }
}

public class Run {
    public static void main(String[] args) {
        University university = createTypicalUniversity();
        System.out.println("University created: " + university.getName());
    }

    public static University createTypicalUniversity() {
        Human universityHead = HumanCreator.createHuman("John", "Doe", "Ivanovich", Sex.MALE);
        University university = UniversityCreator.createUniversity("Global University", universityHead);

        Human facultyHead = HumanCreator.createHuman("Jane", "Smith", "Petrovna", Sex.FEMALE);
        Faculty faculty = FacultyCreator.createFaculty("Engineering Faculty", facultyHead);
        university.addFaculty(faculty);

        Human departmentHead = HumanCreator.createHuman("Alex", "Johnson", "Sergeevich", Sex.MALE);
        Department department = DepartmentCreator.createDepartment("Computer Science", departmentHead);
        faculty.addDepartment(department);

        Human groupHead = HumanCreator.createHuman("Emily", "Brown", "Ivanovna", Sex.FEMALE);
        Group group = GroupCreator.createGroup("CS101", groupHead);
        department.addGroup(group);

        Student student = StudentCreator.createStudent("Michael", "Clark", "Alexeevich", Sex.MALE);
        group.addStudent(student);

        return university;
    }
}
