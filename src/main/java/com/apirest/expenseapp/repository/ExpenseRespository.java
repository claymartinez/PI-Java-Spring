package com.apirest.expenseapp.repository;

import com.apirest.expenseapp.entities.Expense;
import com.apirest.expenseapp.exception.DAOException;

import java.util.List;

public interface ExpenseRespository {
    Integer insert(Expense expense);
    List<Expense> getAllExpenses() throws DAOException;
    Integer updateExpense(Long id, Expense expense);
    void deleteExpense(Long id) throws DAOException;
}
