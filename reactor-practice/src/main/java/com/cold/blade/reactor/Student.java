package com.cold.blade.reactor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.Getter;

/**
 * @description
 * @author cold_blade
 * @date 2019/7/8
 * @version 1.0
 */
@Getter
public class Student {

    private String name;
    private int grade;
    private char sex;
    private double totalScore;
    private double chineseSoce;
    private double mathScore;
    private double englishSoce;

    public static List<Student> filterFirstYearStudents(List<Student> peopleList) {
        List<Student> results = new ArrayList<>();
        for (Student student : peopleList) {
            if (student.getGrade() == 1) {
                results.add(student);
            }
        }
        return results;
    }

    public static List<Student> filterStudents(List<Student> peopleList, int grade, char sex, double totalScore) {
        List<Student> results = new ArrayList<>();
        for (Student student : peopleList) {
            if (grade != 0) {
                if (student.getGrade() == grade) {
                    results.add(student);
                }
            } else if (sex != 0) {
                if (student.getSex() == sex) {
                    results.add(student);
                }
            } else if (totalScore != 0.0) {
                if (student.getTotalScore() == totalScore) {
                    results.add(student);
                }
            }
        }
        return results;
    }

    public static List<Student> filterStudents(List<Student> peopleList, StudentPredicate predicate) {
        List<Student> results = new ArrayList<>();
        for (Student student : peopleList) {
            if (predicate.test(student)) {
                results.add(student);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        List<Student> firstGradeStudents = filterStudents(students, new StudentPredicate() {
            @Override
            public boolean test(Student student) {
                return student.getGrade() == 1;
            }
        });

        firstGradeStudents = filterStudents(students, s -> s.getGrade() == 1);
        Predicate<Student> cs = s -> s.getGrade() == 1;

        Callable<String> c = () -> "test";
        Supplier<String> s = () -> "test";
        Comparator.comparing(Student::getGrade);

        Student student = new Student();
        printStudentAttribute(student::getGrade);
        printStudentAttribute(student::getSex);
    }

    public static void printStudentAttribute(Supplier<Object> supplier) {
        System.out.println(supplier.get());
    }

    public interface StudentPredicate {

        boolean test(Student student);
    }

    public class StudentFirstGradePredicate implements StudentPredicate {

        @Override
        public boolean test(Student student) {
            return student.getGrade() == 1;
        }
    }

    public class StudentGirlPredicate implements StudentPredicate {

        @Override
        public boolean test(Student student) {
            return student.getSex() == 'W';
        }
    }
}
