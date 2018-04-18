import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController //implements Initializable
{
    public Button login_button;
    public TextField username, password;

   /* @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("Do database stuff here before starting program.....if any");
    }*/

    public void onClickLogin() throws SQLException {
        String name = username.getText();
        String pass = password.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, name, pass);
            System.out.println(con.getMetaData());
        } catch (Exception e) {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/planet", "test", "password");
            Statement s = con.createStatement();
        }
    }

    public void onClickRegister() throws SQLException, IOException {
        Parent root = FXMLLoader.load(getClass().getResource("reg_des.fxml"));
        Main.stage.setScene(new Scene(root, 500,600));
    }
}
