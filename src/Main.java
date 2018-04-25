import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application
{
    public static Stage stage;
    public static void main(String args[])
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("log_des.fxml"));
        primaryStage.setTitle("Login or Sign Up");
        primaryStage.setScene(new Scene(root,940,520));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public void reg() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("reg_des.fxml"));
        Stage reg_stage = new Stage();
        reg_stage.setScene(new Scene(root,1280,720));
        reg_stage.show();
    }
}
