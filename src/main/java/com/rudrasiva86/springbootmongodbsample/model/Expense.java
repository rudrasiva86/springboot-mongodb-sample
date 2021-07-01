package com.rudrasiva86.springbootmongodbsample.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document("expense")
public class Expense {
	@Id
	private String id;
	
	@Field("name")
	@Indexed(unique = true)
	private String expenseName;
	
	@Field("category")
	private ExpenseCategory expenseCategory;
	
	@Field("amount")
	private BigDecimal expenseAmount;
}
