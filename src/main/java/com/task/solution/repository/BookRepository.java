package com.task.solution.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
