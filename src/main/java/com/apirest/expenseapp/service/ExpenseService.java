package com.apirest.expenseapp.service;

import com.apirest.expenseapp.dto.request.ExpenseRequestDto;
import com.apirest.expenseapp.dto.response.ExpenseResponseDto;
import com.apirest.expenseapp.exception.DAOException;

import java.util.List;

public interface ExpenseService {
    String createExpense(ExpenseRequestDto expenseRequestDto);
    List<ExpenseResponseDto> getAll() throws DAOException;
    String updateExpense(Long id, ExpenseRequestDto expenseRequestDto);
    void deleteExpense(Long id) throws DAOException;
}
