package com.example.school.repository;

import com.example.school.model.School;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchoolRepository extends CrudRepository<School, Long> {
    Optional<School> findByName(String schoolName);

}