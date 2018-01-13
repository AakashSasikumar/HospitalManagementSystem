import javafx.application.Application;

import java.sql.*;

public class Main {
    static String USERNAME = "system";
    static String PASSWORD = "aakash1997";
    static PreparedStatement loginVerification;
    static String EMP_ID;
    //static GUI gui = new GUI();
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "aakash1997");
        loginVerification = conn.prepareStatement("select emp_pw from employee where emp_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
        Application.launch(GUI.class);
    }
}
