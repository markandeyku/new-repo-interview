package dsignpattern.creational;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingltoDesignPattern {

    // example of database pool connection and thread pool executor

    // client to create database connection
    public static void main(String[] args) {

        DatabaseConnection dbConnection1 = DatabaseConnection.getConnectionInstance();
        Connection conn1 = dbConnection1.getConnection();

        DatabaseConnection dbConnection2 = DatabaseConnection.getConnectionInstance();
        Connection conn2 = dbConnection2.getConnection();

        // Both connections point to the same instance
        System.out.println(conn1 == conn2); // Output: true (Same        // prepare query and data
        // then commit
        // then close the connection

    }
}

class DatabaseConnection{

    private static DatabaseConnection databaseConnection;

    private Connection connection;

    private DatabaseConnection(){
        try {
            String url = "jdbc:mysql://localhost:3306/mydb";
            String username = "root";
            String password = "password";
            this.connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static DatabaseConnection getConnectionInstance(){
        if(databaseConnection == null){
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }
    public Connection getConnection(){
        return this.connection;
    }
}
