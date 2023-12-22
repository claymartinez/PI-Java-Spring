package com.apirest.expenseapp.interfaces;

import com.apirest.expenseapp.repository.dto.ExpenseDto;
import com.apirest.expenseapp.entities.Expense;

import java.util.List;

public class ExpenseCalculatorImpl implements ExpenseCalculator {
    @Override
    public double calculateExpense(Expense expense) {
        return expense.getAmount();
    }

    @Override
    public double calculatorTotalExpense(List<ExpenseDto> expenses) {
        double totalExpense = 0;
        for (ExpenseDto expense : expenses){
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }
}
