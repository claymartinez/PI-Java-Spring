package com.apirest.expenseapp.service.impl;

import com.apirest.expenseapp.repository.ExpenseRespository;
import com.apirest.expenseapp.dto.request.ExpenseRequestDto;
import com.apirest.expenseapp.dto.response.ExpenseCategoryResponseDto;
import com.apirest.expenseapp.dto.response.ExpenseResponseDto;
import com.apirest.expenseapp.entities.Expense;
import com.apirest.expenseapp.exception.DAOException;
import com.apirest.expenseapp.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRespository expenseRespository;

    public ExpenseServiceImpl(ExpenseRespository expenseRespository) {
        this.expenseRespository = expenseRespository;
    }

    @Override
    public String createExpense(ExpenseRequestDto expenseRequestDto) {
        String response = "Se registro el gasto con exito";

        Expense expense = mapDtoToExpense(expenseRequestDto);

        Integer responseInserted = expenseRespository.insert(expense);
        if (responseInserted.equals(0)) {
            System.out.println("No se inserto ningun registro");
        }

        return response;
    }

    @Override
    public List<ExpenseResponseDto> getAll() throws DAOException {
        List<Expense> expenses = expenseRespository.getAllExpenses();
        return expenses.stream()
                .map(this::mapExpenseToResponseDto)
                .collect(Collectors.toList());
    }

    private Expense mapDtoToExpense(ExpenseRequestDto expenseRequestDto) {
        Expense expense = new Expense();
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setCategoryName(expenseRequestDto.getCategoryRequestDto().getName());
        expense.setDate(expenseRequestDto.getDate());
        return expense;
    }

    private ExpenseResponseDto mapExpenseToResponseDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());

        ExpenseCategoryResponseDto categoryDto = new ExpenseCategoryResponseDto();
        categoryDto.setId(expense.getId());
        categoryDto.setName(expense.getCategoryName());

        expenseResponseDto.setCategoryDto(categoryDto);
        expenseResponseDto.setDate(expense.getDate());
        return expenseResponseDto;
    }

    @Override
    public String updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        // Defino un mensaje de éxito por default
        String response = "Se actualizó el gasto con éxito";
        Expense expense = mapDtoToExpense(expenseRequestDto);
        Integer responsesUpdated = expenseRespository.updateExpense(id, expense);
        // Si el update de BD no devolvió ningún registro actualizado, entonces devuelvo un mensaje de error
        if (responsesUpdated.equals(0)) {
            System.out.println("No se actualizó ningún registro con el id " + id);
        }
        System.out.println("Se actualiza la presentacion id: " + id);
        return response;
    }
    @Override
    public void deleteExpense(Long id) throws DAOException {
        expenseRespository.deleteExpense(id);
    }

}
