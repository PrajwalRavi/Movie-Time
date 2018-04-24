import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    public Button login_button;
    public TextField username, password;
    public TabPane pavan;
    public Tab SIGNIN;
    public Button login_button2;
    public static String name, pass;
    String path ="";
    public  Statement s;
    public  ListView<String> left_list = new ListView<String>();
    public  ListView<Integer> posterno=new ListView<Integer>();
    public  ListView<String> poster_list=new ListView<String>();
    //* @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        pavan.getSelectionModel().select(SIGNIN);
    }

    public void onClickLogin() {

        name = username.getText();
        pass = password.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "test", "password");
            ResultSet valid = con.createStatement().executeQuery("SELECT * FROM creds WHERE uname='" + name + "' AND " +
                    "password='" + pass + "';");
            if (!valid.next())
                throw new Exception("Invalid login");       //NEW SCREEN HERE AKHIL

            display();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public TextField username2;
    public PasswordField password2,password3;

    public void createTables(String user) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + user, "test", "password");
        Statement s = con.createStatement();
        s.executeUpdate("CREATE TABLE PRODHOUSE(PID INT AUTO_INCREMENT ,NAME VARCHAR(100),CEO VARCHAR(100),PRIMARY KEY(PID));");
        s.executeUpdate("CREATE TABLE DIRECTOR(DID INT AUTO_INCREMENT, NAME VARCHAR(80), AGE INT NOT NULL,PRIMARY KEY(DID));");
        s.executeUpdate("CREATE TABLE MOVIE(ID INT AUTO_INCREMENT,PATH VARCHAR(100) NOT NULL,NAME VARCHAR(100) NOT NULL,PRIMARY KEY(ID));");
        s.executeUpdate("ALTER TABLE MOVIE MODIFY ID INT(11) NOT NULL AUTO_INCREMENT;");
        s.executeUpdate("CREATE TABLE MOVIE_DET(ID INT ,DID INT ,PID INT ,YEAR YEAR,RATING DOUBLE, GENRE VARCHAR(20),WATCHED BIT,BOX DOUBLE,FOREIGN KEY A(ID) REFERENCES MOVIE(ID),FOREIGN KEY B(DID) REFERENCES DIRECTOR(DID),FOREIGN KEY D(PID) REFERENCES PRODHOUSE(PID));");
        s.executeUpdate("CREATE TABLE POSTER(MID INT,PID INT AUTO_INCREMENT,PATH VARCHAR(200), FOREIGN KEY Afr(MID) REFERENCES MOVIE_DET(ID),PRIMARY KEY(PID));");
        s.executeUpdate("CREATE TABLE SUBTITLES(MID INT ,SID INT AUTO_INCREMENT,PATH VARCHAR(200),LANG VARCHAR(10),FOREIGN KEY Ajhg(MID) REFERENCES MOVIE_DET(ID),PRIMARY KEY(SID));");
        s.executeUpdate("CREATE TABLE MOVCAST(ID INT AUTO_INCREMENT,MID INT,CNAME VARCHAR(300),FOREIGN KEY Apojh(MID) REFERENCES MOVIE_DET(ID),PRIMARY KEY(ID));");


    }

    public void onClickRegister() throws SQLException {
        String user = username2.getText();
        String password = password2.getText();
        if(password2.getText().equals(password3.getText())) {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akhil", "test", "password");
            Statement s = con.createStatement();
            s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + user + ";");
            s.executeUpdate("INSERT INTO creds VALUES('" + user + "','" + password + "');");
            createTables(user);
        }

    }

    public void display()
    {
        AnchorPane outer = new AnchorPane();
        AnchorPane anchor = new AnchorPane();
        MenuBar bar = new MenuBar();
        Menu filem = new Menu("_File");
        Menu editm = new Menu("_Edit");
        bar.getMenus().addAll(filem,editm);
        bar.setLayoutY(0);bar.setPrefWidth(1920);
        ScrollPane sp = new ScrollPane();
        anchor.setPrefSize(1920,1048);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED     );
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER     );
        anchor.getChildren().add(bar);
        anchor.setStyle("-fx-background-color: #17181b");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, "test", "password");
            s = con.createStatement();
            ResultSet num = s.executeQuery("SELECT COUNT(*) as something FROM movie;");
            num.next();
            int count = num.getInt("something");
            System.out.println(count);
            ResultSet r = s.executeQuery("SELECT NAME FROM movie;");
            while (r.next()) {
                left_list.getItems().add(r.getString(1));
            }
            System.out.println(left_list.getItems());
            ResultSet r1 = s.executeQuery("SELECT MID FROM poster;");
            while (r1.next()) {
                posterno.getItems().add(r1.getInt("MID"));
            }
            System.out.println(posterno.getItems());
            ResultSet r2 = s.executeQuery("SELECT PATH FROM poster;");
            while (r2.next()) {
                poster_list.getItems().add(r2.getString("PATH"));
            }

            int i,j=1,i1=1;
            for(i=1;i<=count;i++)
            {

                j=i/9+1;
                i1=i%9;
                if(i1==0)
                    i1=9;
                if(posterno.getItems().contains(i))
                {
                    path = poster_list.getItems().get(posterno.getItems().indexOf(i));
                }
                Label yr = new Label();
                Label al = new Label(left_list.getItems().get(i-1));
                Image image = new Image("/"+path);
                ImageView imgview = new ImageView(image);
                System.out.println(232323);
                imgview.setLayoutX(210*i1-180);
                imgview.setLayoutY(316*j-264);
                anchor.getChildren().add(imgview);
                al.setLayoutX(210*i1-180);al.setLayoutY(316*j+4);al.setPrefSize(150,25);
                al.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 15px;");
                anchor.getChildren().add(al);
                ResultSet r3 = s.executeQuery(" select YEAR from movie natural join movie_det where ID="+i+";");
                while (r3.next()) {
                    yr.setText(Integer.toString(r3.getInt("YEAR")));
                }
                yr.setLayoutX(210*i1-180);yr.setLayoutY(316*j+22);yr.setPrefSize(150,25);
                yr.setStyle("-fx-font-weight: bold;  -fx-text-fill: #5b5b5b;-fx-font-size: 13px;");
                anchor.getChildren().add(yr);
            }
            if(j>3)
            {
                HBox hbox = new HBox();
                hbox.setPrefSize(50,50);
                hbox.setLayoutY(316*(j+1)-264);
                hbox.setLayoutX(10);
                anchor.getChildren().add(hbox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
















        sp.setContent(anchor);
        outer.getChildren().addAll(sp);
        Main.stage.setScene(new Scene(outer, 1920, 1080));
        Main.stage.setMaximized(true);
    }


}
