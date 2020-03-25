package DAO;

import java.sql.*;

public class JDBCConnection {

    public static Connection getDBconnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:5432/webappdatabase";
            connection =
                    DriverManager.getConnection(dbURL, "bd", "123456789");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean validate(String name, String pass) {
        boolean status = false;
        try {
            Connection con = getDBconnection();

            PreparedStatement ps = con.prepareStatement(
                    "select * from users where login=? and pass=?");
            ps.setString(1, name);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
}
