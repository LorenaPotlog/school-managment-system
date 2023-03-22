package com.example.school.repository;

import com.example.school.model.School;
import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<School,Long> {

}