package com.capgemini.main;

import com.capgemini.dao.MySQLStudentDAO;
import com.capgemini.dao.OracleStudentDAO;
import com.capgemini.dao.StudentDAO;
import com.capgemini.exception.InvalidStudentDataException;
import com.capgemini.model.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select Student DAO:");
        System.out.println("  1. MySQLStudentDao");
        System.out.println("  2. OracleStudentDAO");
        System.out.print("Choice: ");

        StudentDAO dao;
        int dbChoice = Integer.parseInt(sc.nextLine().trim());

        if (dbChoice == 2) {
            dao = new MySQLStudentDAO();
        } else {
            dao = new OracleStudentDAO();
        }

        int choice;
        do {
            printMenu();
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 1 -> addStudent(sc, dao);
                case 2 -> viewStudents(dao);
                case 3 -> updateStudent(sc, dao);
                case 4 -> deleteStudent(sc, dao);
                case 5 -> System.out.println("👋 Exiting. Goodbye!");
                default -> System.out.println("⚠️  Invalid choice. Try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    private static void printMenu() {
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("----------------------------------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
    }

    private static void addStudent(Scanner sc, StudentDAO dao) {
        System.out.println("\nAdd New Student");
        try {
            System.out.print("ID     : ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Name   : ");
            String name = sc.nextLine();
            System.out.print("Email  : ");
            String email = sc.nextLine();
            System.out.print("Age    : ");
            int age = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Mobile : ");
            String mobile = sc.nextLine();

            Student s = new Student(id, name, email, age, mobile);
            dao.addStudent(s);

        } catch (InvalidStudentDataException e) {
            System.out.println("🚫 " + e);
        } catch (NumberFormatException e) {
            System.out.println("🚫 ID and Age must be valid numbers.");
        }
    }

    private static void viewStudents(StudentDAO dao) {
        System.out.println("\nAll Students");
        List<Student> list = dao.getAllStudents(); // ← polymorphic call

        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : list) {
            System.out.println(s);
        }
    }

    private static void updateStudent(Scanner sc, StudentDAO dao) {
        System.out.println("\nUpdate Student");
        try {
            System.out.print("Enter Mobile to update: ");
            String mobile = sc.nextLine();
            System.out.print("New Email  : ");
            String email = sc.nextLine();

            dao.updateStudent(mobile, email);

        } catch (NumberFormatException e) {
            System.out.println("🚫 Invalid input.");
        }
    }

    private static void deleteStudent(Scanner sc, StudentDAO dao) {
        System.out.println("\nDelete Student");
        try {
            System.out.print("Enter Student ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            dao.deleteStudent(id);

        } catch (NumberFormatException e) {
            System.out.println("🚫 ID must be a valid number.");
        }
    }
}
