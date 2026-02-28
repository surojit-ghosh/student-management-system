package com.capgemini.dao;

import com.capgemini.exception.InvalidStudentDataException;
import com.capgemini.model.Student;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student) throws InvalidStudentDataException;
    List<Student> getAllStudents();
    void updateStudent(String mobile, String email);
    void deleteStudent(int id);

    default void validateStudent(Student student) throws InvalidStudentDataException {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidStudentDataException("Name cannot be empty");
        }
        if (student.getName().matches(".*\\d.*")) {
            throw new InvalidStudentDataException("Name cannot contain numbers");
        }

        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            throw new InvalidStudentDataException("Email must contain '@' symbol");
        }

        if (student.getAge() <= 0) {
            throw new InvalidStudentDataException("Age must be a positive number");
        }

        if (student.getMobile() == null || !student.getMobile().matches("\\d{10}")) {
            throw new InvalidStudentDataException("Mobile number must contain exactly 10 digits");
        }
    }
}

