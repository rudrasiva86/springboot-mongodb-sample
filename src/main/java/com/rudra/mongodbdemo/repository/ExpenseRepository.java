package com.rudra.mongodbdemo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rudra.mongodbdemo.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

	@Query("{'name': ?0}")
	Optional<Expense> findByName(String name);
}
