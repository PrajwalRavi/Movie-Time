import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller //implements Initializable
{
    public Button login_button;
    public TextField username,password;

   /* @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("Do database stuff here before starting program.....if any");
    }
    public void func() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/planet","test","password");
    }*/
    public void onClick() throws SQLException
    {
        String name = username.getText();
        String pass = password.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, name, pass);
            System.out.println(con.getMetaData());
        }
        catch(Exception e)
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/planet", "test", "password");
            Statement s = con.createStatement();
            int result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS "+name+";");
        }
    }
}
