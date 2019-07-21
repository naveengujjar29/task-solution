package com.task.solution.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Comic;

@Repository
public interface ComicRepository extends CrudRepository<Comic, Integer>{

}
