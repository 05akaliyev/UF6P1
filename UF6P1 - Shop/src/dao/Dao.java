package dao;

import model.Employee;

public interface Dao {
    void connect();
    Employee getEmployee(int employeeId, String password);
    void disconnect();
}
