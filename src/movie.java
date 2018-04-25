import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class movie implements Initializable {
    public  int i = LoginController.integer;
    public  static ListView<String> left_list = LoginController.left_list;
    public  static ListView<Integer> posterno=LoginController.posterno;
    public  static ListView<String> poster_list=LoginController.poster_list;
    String path;
    @FXML ImageView image;
    @FXML Label name;
    @FXML Label director;
    @FXML Label cast;
    @FXML Label year;
    @FXML Label time;
    @FXML Label genre;
    @FXML Label box;
    @FXML Label production;
    @FXML Label rating;
    @FXML AnchorPane background;
    String user = LoginController.name;
    Connection con;
    Statement s;
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + user, "root", "root");
            s = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(posterno.getItems().contains(i))
        {
            path = poster_list.getItems().get(posterno.getItems().indexOf(i));
        }
        image.setImage(new Image("/"+path));
        image.setFitWidth(1200);
        image.setFitHeight(800);
        background.setStyle("-fx-background-image: url(images/background.png)");
        name.setText(left_list.getItems().get(i-1));
        ResultSet r3 = null;
        ResultSet r4 = null;
        try {
            r3 = s.executeQuery(" select YEAR from movie natural join movie_det where ID="+i+";");
            while (r3.next()) {
                year.setText(Integer.toString(r3.getInt("YEAR")));
            }
            r4 = s.executeQuery(" select RATING from movie natural join movie_det where ID="+i+";");
            while (r4.next()) {
                rating.setText(Double.toString(r4.getDouble("RATING")));
            }
            rating.setText(rating.getText().indexOf(".") < 0 ? rating.getText() : rating.getText().replaceAll("0*$", "").replaceAll("\\.$", ""));
            rating.setText(rating.getText()+"/10");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name.setStyle("-fx-font-weight: bold");
        director.setStyle("-fx-font-weight: bold");
        cast.setStyle("-fx-font-weight: bold");
        year.setStyle("-fx-font-weight: bold");
        time.setStyle("-fx-font-weight: bold");
        genre.setStyle("-fx-font-weight: bold");
        box.setStyle("-fx-font-weight: bold");
        production.setStyle("-fx-font-weight: bold");
        rating.setStyle("-fx-font-weight: bold");

        director.setText("Director :  " +director.getText());
        cast.setText("Cast");
        year.setText("Year   :  "+year.getText());
        time.setText("Running Time :  " + time.getText());
        genre.setText("Genre  : "+genre.getText());
        box.setText("Box Office : "+box.getText());
        production.setText("Production House : "+production.getText());
        rating.setText("Rating : "+rating.getText());
    }

    public void back()
    {
        LoginController l = new LoginController();
        l.display();
    }
}
