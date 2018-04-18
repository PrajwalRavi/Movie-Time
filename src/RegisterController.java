import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {

    public TextField uname,pass;
    public void createTables(String user, String pass) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + user, "test", "password");
        Statement s = con.createStatement();
        int r = s.executeUpdate("CREATE TABLE MOVIE(ID INT PRIMARY KEY AUTO INCREMENT, NAME VARCHAR(100) NOT NULL, PATH VARCHAR(200) NOT NULL );");

    }

     public void onClickRegister() throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/planet", "test", "password");
        Statement s = con.createStatement();
        int result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + uname.getText() + ";");
        createTables(uname.getText(), pass.getText());

    }
}
