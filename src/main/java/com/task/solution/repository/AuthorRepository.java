package com.task.solution.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
