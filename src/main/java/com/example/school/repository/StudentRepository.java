package com.example.school.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.school.model.Student;

public interface StudentRepository extends CrudRepository<Student,Long>{

}
