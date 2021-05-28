package com.shoprich.ShopRich.database;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorToDB {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/product_shop";
    static final String USER = "postgres";
    static final String PASS = "2222";
    static Connection connection;
    static{
    try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();

        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");


        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

    }
    public static Connection getConnection() {
        return connection;
    }

}
//    private static Connection connection;
//    private static final String LOOKUP_SERVICE = "java:/comp/env/jdbc/product_shop";
//
//    static {
//        try {
//
//            Context context = new InitialContext();
//            Object lol = context.lookup(LOOKUP_SERVICE);
//            if (lol != null) {
//                DataSource dataSource = (DataSource) lol;
//                connection = dataSource.getConnection();
//            } else throw new NamingException("no connect");
//        } catch (NamingException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Connection getConnection() {
//        return connection;
//


//    public static DataSource getDataSource() {
//        return dataSource;
//    }