package com.apirest.expenseapp.controller;

import com.apirest.expenseapp.dto.request.ExpenseRequestDto;
import com.apirest.expenseapp.dto.response.ExpenseResponseDto;
import com.apirest.expenseapp.exception.DAOException;
import com.apirest.expenseapp.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<String> createExpenseHandler(@RequestBody ExpenseRequestDto expenseRequestDto) {
        String response = expenseService.createExpense(expenseRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses() throws DAOException {
        List<ExpenseResponseDto> responses = expenseService.getAll();
        System.out.println("ExpenseController: obteniendo todos los gastos");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    // El endpoint con PUT envía un body definido por las propiedades del dto para actualizar el gasto con id especificado por parametro
    @PutMapping("/update")
    public ResponseEntity<String> updateExpense(@RequestParam Long id,
                                                @RequestBody ExpenseRequestDto expenseRequestDto) {
        String response = expenseService.updateExpense(id, expenseRequestDto);
        System.out.println("ExpenseController: actualizando el gasto");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) throws DAOException {
        expenseService.deleteExpense(id);
        System.out.println("ExpenseController: eliminando el gasto");
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body("Se eliminó el gasto con id: " + id);
    }

}
