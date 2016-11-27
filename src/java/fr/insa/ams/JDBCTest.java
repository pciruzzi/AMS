package fr.insa.ams;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCTest {

    private static final String url = "jdbc:mysql://localhost:3306/ams";
    private static final String user = "amsUser";
    private static final String password = "amsuser";

    public static void main(String args[]) {
//        NO Hibernate
//        try {
//            Connection con = DriverManager.getConnection(url, user, password);
//            Statement test = con.createStatement();
//            test.executeUpdate("CREATE TABLE IF NOT EXISTS test (NUM INTEGER PRIMARY KEY NOT NULL)");
//            test.executeUpdate("INSERT INTO test (NUM) VALUES (1)");
//            System.out.println("Success");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Hibernate
        Database db = new Database();
        Integer id = db.addStudent("pepe");
        System.out.println("Student created with id " + id);
    }

}
