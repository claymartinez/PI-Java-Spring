package com.apirest.expenseapp.interfaces;

import com.apirest.expenseapp.repository.dto.ExpenseDto;
import com.apirest.expenseapp.entities.Expense;

import java.util.List;

public interface ExpenseCalculator {
    double calculateExpense(Expense expense);
    double calculatorTotalExpense(List<ExpenseDto> expenses);
}
