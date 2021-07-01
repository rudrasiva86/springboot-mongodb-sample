package com.rudrasiva86.springbootmongodbsample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import com.rudrasiva86.springbootmongodbsample.model.Expense;
import com.rudrasiva86.springbootmongodbsample.model.ExpenseCategory;
import com.rudrasiva86.springbootmongodbsample.repository.ExpenseRepository;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class SpringbootMongodbSampleApplicationTests {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	
	{
		mongoDBContainer.start();
	}
	
	@DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
	
	@Autowired
    private ExpenseRepository expenseRepository;
	
	// TODO: Use mongock to initialize the repo with 5 records
	@Test
    @DisplayName("Test Whether Expenses are pre-populated")
    void shouldReturnPrePopulatedExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        assertEquals(5, expenses.size());
    }
	
	@Test
	@DisplayName("Should Find Expense By Name")
    void shouldFindExpenseByName() {
        Expense expectedExpense = new Expense();
        expectedExpense.setExpenseName("Movies");
        expectedExpense.setExpenseCategory(ExpenseCategory.MISC);
        expectedExpense.setExpenseAmount(BigDecimal.TEN);
        expenseRepository.save(expectedExpense);
 
        Expense actualExpense = expenseRepository.findByName("Movies").orElseThrow(() -> new RuntimeException());
        assertEquals(expectedExpense.getExpenseName(), actualExpense.getExpenseName());
        assertEquals(expectedExpense.getExpenseCategory(), actualExpense.getExpenseCategory());
        assertEquals(expectedExpense.getExpenseAmount(), actualExpense.getExpenseAmount());
    }

}
