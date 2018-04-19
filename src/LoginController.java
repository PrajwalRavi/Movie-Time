import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController //implements Initializable
{
    public Button login_button;
    public TextField username, password;

    public static String name, pass;
   /* @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("Do database stuff here before starting program.....if any");
    }*/

    public void onClickLogin() {

        name = username.getText();
        pass = password.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "test", "password");
            ResultSet valid = con.createStatement().executeQuery("SELECT * FROM creds WHERE uname='" + name + "' AND " +
                    "password='" + pass + "';");
            if (!valid.next())
                throw new Exception("Invalid login");       //NEW SCREEN HERE AKHIL
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Main.stage.setScene(new Scene(root, 500, 500));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onClickRegister() throws SQLException, IOException {

        Parent root = FXMLLoader.load(getClass().getResource("reg_des.fxml"));
        Main.stage.setScene(new Scene(root, 500, 600));
    }
}
