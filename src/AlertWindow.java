import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class AlertWindow {
    public static void login(String message,String txt)
    {
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label(message);
        label.setFont(new Font(25));
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: Black;");
        Button btn = new Button(txt);
        btn.setFont(new Font(18));
        btn.setStyle("-fx-background-color: red;\n" +
                "\t-fx-text-fill: white;\n" +
                "\t-fx-font-weight: bold;");
        btn.setOnAction(e -> {
            window.close();
        });
        btn.setOnMouseEntered(event -> {
            btn.setStyle("-fx-background-color: red;\n" +
                    "\t-fx-text-fill: brown;\n" +
                    "\t-fx-font-weight: bold;");
        });
        VBox vBox = new VBox(100);
        vBox.getChildren().addAll(label,btn);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,350,250);
        window.setScene(scene);
        window.showAndWait();

    }

}
