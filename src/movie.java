import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
    @FXML Label directorage;
    @FXML Label cast;
    @FXML Label year;
    @FXML Label time;
    @FXML Label size;
    @FXML Label genre;
    @FXML Label box;
    @FXML Label production;
    @FXML Label ceo;
    @FXML Label rating;
    @FXML AnchorPane background;
    String user = LoginController.name;
    Connection con;
    public int k;
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
        background.setStyle("-fx-background-image: url(images/background.png)");
        name.setText(left_list.getItems().get(i-1));
        ListView<Integer> lv = new ListView<Integer>();
        ResultSet num = null;
        ResultSet r3 = null;
        ResultSet r4 = null;
        try {
            num = s.executeQuery("SELECT ID as something FROM MOVIE;");
            while(num.next())
            {
                lv.getItems().add(num.getInt("something"));
            }
            k= lv.getItems().get(i-1);
            r3 = s.executeQuery(" select * from MOVIE_DET where ID="+k+";");
            r3.next();
            year.setText(Integer.toString(r3.getInt("YEAR")));
            rating.setText(Double.toString(r3.getDouble("RATING")));
            rating.setText(rating.getText().indexOf(".") < 0 ? rating.getText() : rating.getText().replaceAll("0*$", "").replaceAll("\\.$", ""));
            rating.setText(rating.getText()+"/10");
            box.setText(Integer.toString(r3.getInt("BOX")));
            time.setText(Integer.toString(r3.getInt("LENGTH")));
            size.setText(Double.toString(r3.getDouble("SIZE")));
            r3 = s.executeQuery("select TYPE from genre where ID="+k+";");
            String g ="";
            while(r3.next()) {
                g+=r3.getString("TYPE") + ", ";
            }
            genre.setText(g.substring(0,g.length()-2));
            r3 = s.executeQuery("select * from DIRECTOR natural join MOVIE_DET where ID="+k+";");
            r3.next();
            director.setText(r3.getString("NAME"));
            directorage.setText(Integer.toString(r3.getInt("AGE")));
            r3 = s.executeQuery("select CNAME from MOVCAST where MID="+k+";");
            String c ="";
            while(r3.next()) {
                c+=r3.getString("CNAME")+", ";
            }
            cast.setText(c.substring(0,c.length()-2));
            r3 = s.executeQuery("select * from PRODHOUSE natural join MOVIE_DET where ID="+k+";");
            r3.next();
            production.setText(r3.getString("NAME"));
            ceo.setText(r3.getString("CEO"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(posterno.getItems().contains(k))
        {
            path = poster_list.getItems().get(posterno.getItems().indexOf(k));
        }
        image.setImage(new Image(path));
        name.setStyle("-fx-font-weight: bold");
        director.setStyle("-fx-font-weight: bold");
        directorage.setStyle("-fx-font-weight: bold");
        cast.setStyle("-fx-font-weight: bold");
        year.setStyle("-fx-font-weight: bold");
        time.setStyle("-fx-font-weight: bold");
        genre.setStyle("-fx-font-weight: bold");
        box.setStyle("-fx-font-weight: bold");
        production.setStyle("-fx-font-weight: bold");
        ceo.setStyle("-fx-font-weight: bold");
        rating.setStyle("-fx-font-weight: bold");
        size.setStyle("-fx-font-weight: bold");

        director.setText("Director :  " +director.getText());
        directorage.setText("Age :  " +directorage.getText());
        cast.setText("Cast  :  "+cast.getText());
        year.setText("Year   :  "+year.getText());
        time.setText("Running Time :  " + time.getText());
        size.setText(size.getText()+" GB");
        genre.setText("Genre  : "+genre.getText());
        box.setText("Box Office : "+box.getText());
        production.setText("Production House : "+production.getText());
        ceo.setText("CEO  :  "+ ceo.getText());
        rating.setText("Rating : "+rating.getText());
    }

    public void back()
    {
        LoginController l = new LoginController();
        l.display();
    }

    public void delete(){
        DeleteController l =new DeleteController();
        try {
            l.DeleteFunction(k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        back();
    }

    public void Mohanty(){
        Parent root = null;
        try {
            con.close();
            root = FXMLLoader.load(getClass().getResource("edit.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.stage.setScene(new Scene(root, 1920, 1080));
    }

    public void play(){
        try {
            ResultSet r3 = s.executeQuery("select PATH from movie where ID="+k+";");
            r3.next();
            String moviep,subp;
            moviep = r3.getString("PATH");
            r3 = s.executeQuery("select * from subtitles where MID="+k+";");
            r3.next();

            subp=r3.getString("PATH");
            Process p = Runtime.getRuntime().exec("vlc "+moviep);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
