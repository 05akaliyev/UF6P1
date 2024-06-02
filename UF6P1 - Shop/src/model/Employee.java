package model;

import dao.Dao;
import dao.DaoImplJDBC;

public class Employee extends Person {
    private int employeeId;
    private Dao dao;

    public Employee(String name, int employeeId) {
        super(name);
        this.employeeId = employeeId;
        this.dao = new DaoImplJDBC();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean login(int user, String password) {
        dao.connect();
        Employee employee = dao.getEmployee(user, password);
        dao.disconnect();
        if (employee != null) {
            System.out.println("UserID " + user + " logged");
            return true;
        } else {
            System.out.println("ERROR, wrong dates");
            return false;
        }
    }
}
