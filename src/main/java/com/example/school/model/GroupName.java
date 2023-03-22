//package com.example.school.model;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.util.Objects;
//
////try enum
//@Embeddable
//public class GroupName implements Serializable {
//    @Column(name = "class_number")
//    private int year; // 1-8
//    @Column(name = "class_letter")
//    private char letter; // A-D
//
//    long studentId;
//    long schoolId;
//
//    long teacherId;
//
//    public GroupName(int year, char letter) {
//        this.year = year;
//        this.letter = letter;
//    }
//    public GroupName() {
//
//    }
//    public int getYear() {
//        return year;
//    }
//
//    public char getLetter() {
//        return letter;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GroupName groupName = (GroupName) o;
//        return year == groupName.year && letter == groupName.letter;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(year, letter);
//    }
//}
