package com.apirest.expenseapp.interfaces;

import com.apirest.expenseapp.exception.InvalidExpenseException;

public class ExpenseAmountValidatorImpl implements ExpenseAmountValidator {
    @Override
    public boolean notvalidAmount(double amount) throws InvalidExpenseException {
        if (amount < 0) {
            throw new InvalidExpenseException("El monto debe ser igual o mayor a cero");
        }
        return false;
    }
}
