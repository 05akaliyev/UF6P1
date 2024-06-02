package dao;

import model.Employee;
import java.sql.*;

public class DaoImplJDBC implements Dao {
    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop", "root", "Adisha0682");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null;
        try {
            String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employeeId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getString("name"), employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
