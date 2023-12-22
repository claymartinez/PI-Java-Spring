package com.apirest.expenseapp.repository.impl;

import com.apirest.expenseapp.exception.DAOException;
import com.apirest.expenseapp.repository.ExpenseRespository;
import com.apirest.expenseapp.entities.Expense;
import com.apirest.expenseapp.entities.ExpenseCategory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class ExpenseRespositoryImplH2 implements ExpenseRespository {
    private static final String INSERT_INTO_EXPENSE =
            "INSERT INTO Expense (amount, category_id, category_name, date) VALUES (?,?,?,?)";
    private static final String INSERT_INTO_CATEGORY_EXPENSE = "INSERT INTO ExpenseCategory (name) VALUES (?)";
    private static final String SELECT_FROM_EXPENSE_CATEGORY_BY_NAME = "SELECT * FROM ExpenseCategory WHERE name = ?";
    private static final String GET_EXPENSE_BY_ID = "SELECT * FROM Expense WHERE id = ?";
    private static final String GET_ALL_EXPENSES = "SELECT * FROM Expense";
    private static final String UPDATE_EXPENSE_BY_ID = "UPDATE Expense SET amount = ?, category_name = ?, date = ? WHERE id = ?";
    private static final String UPDATE_EXPENSE = "UPDATE Expense SET amount = ?, category_id = ?, date = ? WHERE id = ?";
    private static final String DELETE_EXPENSE = "DELETE FROM Expense WHERE id = ?";
    private static final String DELETE_FROM_EXPENSE_BY_ID = "DELETE FROM Expense WHERE id = ?";


    private final JdbcTemplate jdbcTemplate;

    public ExpenseRespositoryImplH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer insert(Expense expense) {
        // Verificar si la categoría ya existe
        Object[] params = {expense.getCategoryName()};
        int[] types = {1};
        List<ExpenseCategory> categories =
                jdbcTemplate.query(
                        SELECT_FROM_EXPENSE_CATEGORY_BY_NAME,
                        params, types,
                        new ExpenseCategoryRowMapper());

        ExpenseCategory expenseCategory;

        if (categories.isEmpty()) {
            // Si la categoría no existe, insertarla
            jdbcTemplate.update(INSERT_INTO_CATEGORY_EXPENSE, expense.getCategoryName().toLowerCase());

            // Obtener la categoría recién insertada
            expenseCategory =
                    jdbcTemplate.queryForObject(
                            SELECT_FROM_EXPENSE_CATEGORY_BY_NAME,
                            params, types,
                            new ExpenseCategoryRowMapper());
        } else {
            // Si la categoría ya existe, usar la primera encontrada
            expenseCategory = categories.get(0);
        }

        // Insertar el gasto asociado a la categoría
        assert expenseCategory != null;
        return jdbcTemplate.update(INSERT_INTO_EXPENSE,
                expense.getAmount(),
                expenseCategory.getId(),
                expenseCategory.getName(),
                expense.getDate());
    }

    static class ExpenseCategoryRowMapper implements RowMapper<ExpenseCategory> {
        @Override
        public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setId(rs.getLong("id"));
            expenseCategory.setName(rs.getString("name"));
            return expenseCategory;
        }
    }



    @Override
    public List<Expense> getAllExpenses() {
        // En el caso de recuperar todos los registros, no necesitamos especificar parametros de búsqueda
        return jdbcTemplate.query(GET_ALL_EXPENSES, new ExpenseRowMapper());
    }

    // Clase interna que permite mapear cada resultado del ResultSet a las propiedades de la entidad
    public static class ExpenseRowMapper implements RowMapper<Expense> {
        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expense expense = new Expense();
            expense.setId(rs.getLong("id"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setCategoryId(rs.getInt("category_id"));
            expense.setCategoryName(rs.getString("category_name"));
            expense.setDate(rs.getString("date"));
            return expense;
        }
    }

    @Override
    public Integer updateExpense(Long id, Expense expense) {
        System.out.println("Actualizando la presentación");
        return jdbcTemplate.update(UPDATE_EXPENSE_BY_ID,
                expense.getAmount(),
                expense.getCategoryName(),
                expense.getDate(),
                id);
    }

    @Override
    public void deleteExpense(Long id) throws DAOException {
        System.out.println("Se elimina el gasto con ID: " + id);
        // Manejamos un try/catch para que, en caso de error al ejecutar la sentencia SQL de delete, arrojemos una excepción customizada
        try {
            jdbcTemplate.update(DELETE_FROM_EXPENSE_BY_ID, id);
        } catch (DataAccessException exception) {
            throw new DAOException("Hubo un error al eliminar el gasto con id " + id);
        }
        System.out.println("Gasto eliminado con éxito");
    }

}
