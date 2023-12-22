package com.apirest.expenseapp.ExpenseRepositoryImplH2;

import com.apirest.expenseapp.entities.Expense;
import com.apirest.expenseapp.exception.DAOException;
import com.apirest.expenseapp.repository.impl.ExpenseRespositoryImplH2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseRepositoryImplH2Test {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ExpenseRespositoryImplH2 expenseRepository;

    @Before
    public void setUp() {
        // Configuración adicional antes de las pruebas
    }

    @Test
    public void testGetAllExpenses() {
        // Datos de prueba
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setAmount(100.0);
        expense1.setCategoryId(1);
        expense1.setCategoryName("TestCategory");
        expense1.setDate("2023-01-01");

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setAmount(150.0);
        expense2.setCategoryId(2);
        expense2.setCategoryName("TestCategory");
        expense2.setDate("2023-01-02");

        List<Expense> expectedExpenses = Arrays.asList(expense1, expense2);

        // Configuración del mock
        when(jdbcTemplate.query(anyString(), any(ExpenseRespositoryImplH2.ExpenseRowMapper.class)))
                .thenReturn(expectedExpenses);

        // Llamada al método que estamos probando
        List<Expense> actualExpenses = expenseRepository.getAllExpenses();

        // Verificaciones
        verify(jdbcTemplate, times(1)).query(anyString(), any(ExpenseRespositoryImplH2.ExpenseRowMapper.class));
        assertEquals(expectedExpenses, actualExpenses);
    }

    @Test
    public void testDeleteExpenseById() throws DAOException {
        // Datos de prueba
        Long expenseId = 1L;

        // Configuración del mock para simular la eliminación exitosa del gasto por ID
        when(jdbcTemplate.update(anyString(), any(Long.class)))
                .thenReturn(1); // Simula una eliminación exitosa

        // Llamada al método que estamos probando
        expenseRepository.deleteExpense(expenseId);

        // Verificaciones
        verify(jdbcTemplate, times(1)).update(anyString(), any(Long.class));
    }

}