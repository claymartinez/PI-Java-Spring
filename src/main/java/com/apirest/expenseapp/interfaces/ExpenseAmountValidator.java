package com.apirest.expenseapp.interfaces;

import com.apirest.expenseapp.exception.InvalidExpenseException;

@FunctionalInterface
public interface ExpenseAmountValidator {
    boolean notvalidAmount(double amount) throws InvalidExpenseException;
}
