package org.example;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/example";
        String username = "postgres";
        String password = "password"; // not my real password

        TableThread employee = new TableThread("employee",jdbcUrl,username,password);
        TableThread department = new TableThread("department", jdbcUrl, username, password);

        employee.start();
        department.start();
    }
}