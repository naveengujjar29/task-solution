package com.task.solution.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Author;

@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
