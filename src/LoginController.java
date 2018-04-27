import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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
    public  static ListView<String> left_list = new ListView<String>();
    public  static ListView<Integer> posterno=new ListView<Integer>();
    public  static ListView<String> poster_list=new ListView<String>();
    public static int integer;
    //* @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        pavan.getSelectionModel().select(SIGNIN);
        username.setOnKeyReleased(event -> {
            if(event.getCode()==(KeyCode.ENTER))
            {
                password.requestFocus();
            }
        });
        password.setOnKeyReleased(event -> {
            if(event.getCode()==(KeyCode.ENTER))
            {
                try {
                    onClickLogin();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        username2.setOnKeyReleased(event -> {
            if(event.getCode()==(KeyCode.ENTER))
            {
                password2.requestFocus();
            }
        });
        password2.setOnKeyReleased(event -> {
            if(event.getCode()==(KeyCode.ENTER))
            {
                password3.requestFocus();
            }
        });
        password3.setOnKeyReleased(event -> {
            if(event.getCode()==(KeyCode.ENTER))
            {
                try {
                    onClickRegister();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClickLogin() throws SQLException, IOException {
        System.out.println(javafx.scene.text.Font.getFamilies());
        name = username.getText();
        pass = password.getText();
       // new Details().getUserMovies();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akhil", "root","root");
            ResultSet valid = con.createStatement().executeQuery("SELECT * FROM creds WHERE uname='"+name+"' AND password='"+pass+"';");
            if (!valid.next())
            {
                AlertWindow.login("Wrong Credentials","Try Again");
                return;
            }
            con.close();
            display();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public TextField username2;
    public PasswordField password2,password3;

    public void createTables(String user) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + user, "root","root");
        Statement s = con.createStatement();
        s.executeUpdate("CREATE TABLE PRODHOUSE(PID INT AUTO_INCREMENT ,NAME VARCHAR(100),CEO VARCHAR(100),PRIMARY KEY(PID));");
        s.executeUpdate("CREATE TABLE DIRECTOR(DID INT AUTO_INCREMENT, NAME VARCHAR(80), AGE INT NOT NULL,PRIMARY KEY(DID));");
        s.executeUpdate("CREATE TABLE MOVIE(ID INT AUTO_INCREMENT,PATH VARCHAR(1000) NOT NULL,NAME VARCHAR(100) NOT NULL,PRIMARY KEY(ID));");
        s.executeUpdate("ALTER TABLE MOVIE MODIFY ID INT(11) NOT NULL AUTO_INCREMENT;");
        s.executeUpdate("CREATE TABLE MOVIE_DET(ID INT ,DID INT ,PID INT ,YEAR INT,RATING DOUBLE, LENGTH DOUBLE, SIZE DOUBLE,WATCHED BIT,BOX DOUBLE,FOREIGN KEY A(ID) REFERENCES MOVIE(ID),FOREIGN KEY B(DID) REFERENCES DIRECTOR(DID),FOREIGN KEY D(PID) REFERENCES PRODHOUSE(PID));");
        s.executeUpdate("CREATE TABLE GENRE(ID INT,TYPE VARCHAR(100),FOREIGN KEY GFDG(ID) REFERENCES MOVIE(ID));");
        s.executeUpdate("CREATE TABLE POSTER(MID INT,PID INT AUTO_INCREMENT,PATH VARCHAR(800), FOREIGN KEY Afr(MID) REFERENCES MOVIE(ID),PRIMARY KEY(PID));");
        s.executeUpdate("CREATE TABLE SUBTITLES(MID INT ,SID INT AUTO_INCREMENT,PATH VARCHAR(800),LANG VARCHAR(10),FOREIGN KEY Ajhg(MID) REFERENCES MOVIE(ID),PRIMARY KEY(SID));");
        s.executeUpdate("CREATE TABLE MOVCAST(ID INT AUTO_INCREMENT,MID INT,CNAME VARCHAR(300),FOREIGN KEY Apojh(MID) REFERENCES MOVIE(ID),PRIMARY KEY(ID));");
        AlertWindow.login("Registration Succesful","Proceed");
        pavan.getSelectionModel().select(SIGNIN);
    }

    public void onClickRegister() throws SQLException {
        if(username2.getText().equals("") )
        {
            AlertWindow.login("Username can't be null","Ok");
            return;
        }
        if(password2.getText().equals("") )
        {
            AlertWindow.login("Password can't be null","Ok");
            return;
        }
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akhil", "root", "root");
        Statement s = con.createStatement();
        String user = username2.getText();
        String password = password2.getText();
        if(password.equals(password3.getText())) {
            int a = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + user + ";");
            int b = s.executeUpdate("INSERT INTO creds VALUES('" + user + "','" + password + "');");
            createTables(user);
        }
        else {
            AlertWindow.login("Passwords are not matching","OK");
        }

    }

    public void onClickDelete() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "test", "password");
        con.createStatement().executeUpdate("DROP SCHEMA "+username.getText()+";");
        con.createStatement().executeUpdate("DELETE FROM creds WHERE uname='"+username.getText()+"';");
    }

    public TextField search= new TextField();

    public void display()
    {
        AnchorPane outer = new AnchorPane();
        AnchorPane anchor = new AnchorPane();
        MenuBar bar = new MenuBar();
        Menu filem = new Menu("_File");
        Menu editm = new Menu("_Edit");
        bar.getMenus().addAll(filem,editm);
        bar.setLayoutY(0);bar.setPrefWidth(400);
        ScrollPane sp = new ScrollPane();
        anchor.setPrefSize(1920,1048);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED     );
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER     );
        anchor.getChildren().add(bar);
        anchor.setStyle("-fx-background-color: #17181b");
        search.setPromptText("Search");
        search.setLayoutY(0);
        search.setLayoutX(1600);
        search.setStyle("-fx-text-box-border: transparent;\n" +
                "    -fx-background-color: -fx-text-box-border, -fx-background ;\n" +
                "    -fx-background-insets: 0, 0 0 1 0 ;\n" +
                "    -fx-background-radius: 0 ;\n" +
                "    -fx-prompt-text-fill: white ;\n" +
                "    -fx-text-fill:black;-fx-border: 0;\n" +
                "\t-fx-background:transparent;\n" +
                "  \t-fx-border-color: transparent transparent transparent transparent;\n" +
                "\t-webkit-transition: color 5s;\n" +
                "\t-moz-transition:    color 5s;\n" +
                "\t-ms-transition:     color 5s;\n" +
                "\t-o-transition:      color 5s;\n" +
                "\ttransition:         color 5s;\n" +
                "  \t-fx-border-width:1.75px;\n" +
                "  \t-fx-padding: 4 7 4 27; -fx-text-fill: white;" +
                "-fx-font-weight: bold");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        icon.setLayoutX(1600);
        icon.setLayoutY(22);
        icon.setFill(Color.WHITE);
        icon.setSize("20");
        FontAwesomeIconView logout = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
        logout.setLayoutX(1825);
        logout.setLayoutY(25);
        logout.setFill(Color.WHITE);
        logout.setSize("25");
        logout.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("log_des.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.stage.hide();
            Main.stage.setScene(new Scene(root,940,520));
            Main.stage.setMaxHeight(540);
            Main.stage.setMaxWidth(960);
            Main.stage.show();
        });
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, "root", "root");
            Statement s = con.createStatement();
            ListView<Integer> lv = new ListView<Integer>();
            ResultSet num = s.executeQuery("SELECT ID as something FROM MOVIE;");
            while(num.next())
            {
                lv.getItems().add(num.getInt("something"));
            }
            ResultSet r = s.executeQuery("SELECT NAME FROM MOVIE;");
            left_list.getItems().clear();
            while (r.next()) {
                left_list.getItems().add(r.getString(1));
            }
            ResultSet r1 = s.executeQuery("SELECT MID FROM POSTER;");
            posterno.getItems().clear();
            while (r1.next()) {
                posterno.getItems().add(r1.getInt("MID"));
            }

            ResultSet r2 = s.executeQuery("SELECT PATH FROM POSTER;");
            poster_list.getItems().clear();
            while (r2.next()) {

                poster_list.getItems().add(r2.getString("PATH"));
            }

            Image newimg = new Image("images/new.jpg");
            ImageView newimgview = new ImageView(newimg);
            newimgview.setFitHeight(266);
            newimgview.setFitWidth(180);
            newimgview.setLayoutX(30);
            newimgview.setLayoutY(52);
            DropShadow borderGlow2 = new DropShadow();
            borderGlow2.setColor(Color.WHITE);
            borderGlow2.setHeight(30);
            borderGlow2.setWidth(30);
            newimgview.setEffect(borderGlow2);
            newimgview.setOnMouseEntered(event -> {
                borderGlow2.setColor(Color.DEEPSKYBLUE);
                newimgview.setEffect(borderGlow2);
            });
            newimgview.setOnMouseExited(event -> {
                borderGlow2.setColor(Color.WHITE);
                newimgview.setEffect(borderGlow2);
            });
            newimgview.setOnMouseClicked(event -> {
                Parent root = null;
                try {
                    con.close();
                    root = FXMLLoader.load(getClass().getResource("add.fxml"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Main.stage.setScene(new Scene(root, 1920, 1080));

            });
            FontAwesomeIconView newicon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
            newicon.setLayoutX(107.5);
            newicon.setLayoutY(195);
            newicon.setFill(Color.WHITE);
            newicon.setSize("35");
            anchor.getChildren().add(newimgview);
            anchor.getChildren().add(newicon);
            Label newlabel = new Label("Insert Movie");
            newlabel.setLayoutX(65);newlabel.setLayoutY(325);newlabel.setPrefSize(150,25);
            newlabel.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 18px;");
            anchor.getChildren().add(newlabel);
            int i,j=1,i1=1;
            for(i=1;i<=lv.getItems().size();i++)
            {
                int k = lv.getItems().get(i-1);
                j=i/9+1;
                i1=i%9+1;
                if(i1==0)
                    i1=9;
                path = poster_list.getItems().get(i-1);
                Label yr = new Label();
                Label al = new Label(left_list.getItems().get(i-1));
                Image image = new Image(path);
                ImageView imgview = new ImageView(image);
                imgview.setFitHeight(266);
                imgview.setFitWidth(180);
                imgview.setLayoutX(210*i1-180);
                imgview.setLayoutY(316*j-264);
                anchor.getChildren().add(imgview);
                al.setLayoutX(210*i1-180);al.setLayoutY(316*j+4);al.setPrefSize(150,25);
                al.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 15px;");
                anchor.getChildren().add(al);
                ResultSet r3 = s.executeQuery(" select YEAR from MOVIE natural join MOVIE_DET where ID="+k+";");
                while (r3.next()) {
                    yr.setText(Integer.toString(r3.getInt("YEAR")));
                }
                Label rating = new Label();
                rating.setPrefHeight(25);
                rating.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 17px;");
                ResultSet r4 = s.executeQuery(" select RATING from MOVIE natural join MOVIE_DET where ID="+k+";");
                while (r4.next()) {
                    rating.setText(Double.toString(r4.getDouble("RATING")));
                }
                rating.setText(rating.getText().indexOf(".") < 0 ? rating.getText() : rating.getText().replaceAll("0*$", "").replaceAll("\\.$", ""));
                rating.setText(rating.getText()+"/10");
                rating.setLayoutX(210*i1-60);
                rating.setLayoutY(316*j-30);
                yr.setLayoutX(210*i1-180);yr.setLayoutY(316*j+22);yr.setPrefSize(150,25);
                yr.setStyle("-fx-font-weight: bold;  -fx-text-fill: #5b5b5b;-fx-font-size: 13px;");
                anchor.getChildren().add(yr);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.35);
                DropShadow borderGlow = new DropShadow();
                borderGlow.setColor(Color.DEEPSKYBLUE);
                borderGlow.setHeight(30);
                borderGlow.setWidth(30);
                borderGlow.setInput(colorAdjust);
                imgview.setOnMouseEntered(event -> {

                    imgview.setEffect(borderGlow);
                    anchor.getChildren().add(rating);
                });
                imgview.setOnMouseExited(event -> {
                    imgview.setEffect(null);
                    anchor.getChildren().remove(rating);
                });

                int finalI = i;
                imgview.setOnMouseClicked(event -> {

                    Parent root = null;
                    integer = finalI;
                    try {
                        root = FXMLLoader.load(getClass().getResource("movie.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Main.stage.setScene(new Scene(root, 1920, 1080));
                    Main.stage.hide();
                    Main.stage.setMaxHeight(1080);
                    Main.stage.setMaxWidth(1920);
                    Main.stage.show();
                    Main.stage.setMaximized(true);
                });
            }
            if(j>3)
            {
                HBox hbox = new HBox();
                hbox.setPrefSize(50,50);
                hbox.setLayoutY(316*(j+1)-264);
                hbox.setLayoutX(10);
                anchor.getChildren().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        search.setFocusTraversable(false);
        search.setOnAction(event -> {
            if(1==1)
            {
                try {
                    display(SearchController.onClickSearch(search.getText()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        sp.setContent(anchor);
        outer.getChildren().addAll(sp);
        outer.getChildren().add(search);
        outer.getChildren().add(icon);
        outer.getChildren().add(logout);
        Main.stage.setScene(new Scene(outer, 1920, 1080));
        Main.stage.hide();
        Main.stage.setMaxHeight(1080);
        Main.stage.setMaxWidth(1920);
        Main.stage.show();
        Main.stage.setMaximized(true);

    }


    public void display( ListView<Integer> listView)
    {
        AnchorPane outer = new AnchorPane();
        AnchorPane anchor = new AnchorPane();
        MenuBar bar = new MenuBar();
        Menu filem = new Menu("_File");
        Menu editm = new Menu("_Edit");
        bar.getMenus().addAll(filem,editm);
        bar.setLayoutY(0);bar.setPrefWidth(400);
        ScrollPane sp = new ScrollPane();
        anchor.setPrefSize(1920,1048);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED     );
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER     );
        anchor.getChildren().add(bar);
        anchor.setStyle("-fx-background-color: #17181b");
        search.setPromptText("Search");
        search.setLayoutY(0);
        search.setLayoutX(1600);
        search.setStyle("-fx-text-box-border: transparent;\n" +
                "    -fx-background-color: -fx-text-box-border, -fx-background ;\n" +
                "    -fx-background-insets: 0, 0 0 1 0 ;\n" +
                "    -fx-background-radius: 0 ;\n" +
                "    -fx-prompt-text-fill: white ;\n" +
                " -fx-border: 0;\n" +
                "\t-fx-background:transparent;\n" +
                "  \t-fx-border-color: transparent transparent transparent transparent;\n" +
                "\t-webkit-transition: color 5s;\n" +
                "\t-moz-transition:    color 5s;\n" +
                "\t-ms-transition:     color 5s;\n" +
                "\t-o-transition:      color 5s;\n" +
                "\ttransition:         color 5s;\n" +
                "  \t-fx-border-width:1.75px;\n" +
                "  \t-fx-padding: 4 7 4 27; -fx-text-fill: white;" +
                "-fx-font-weight: bold");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        icon.setLayoutX(1600);
        icon.setLayoutY(22);
        icon.setFill(Color.WHITE);
        icon.setSize("20");
        FontAwesomeIconView back = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        back.setLayoutX(1575);
        back.setLayoutY(22);
        back.setFill(Color.WHITE);
        back.setSize("20");
        back.setOnMouseClicked(event -> {
            display();
        });
        FontAwesomeIconView logout = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
        logout.setLayoutX(1825);
        logout.setLayoutY(25);
        logout.setFill(Color.WHITE);
        logout.setSize("25");
        logout.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("log_des.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.stage.hide();
            Main.stage.setScene(new Scene(root,940,520));
            Main.stage.setMaxHeight(540);
            Main.stage.setMaxWidth(960);
            Main.stage.show();
        });
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, "root","root");
            s = con.createStatement();
            ListView<Integer> lv = new ListView<Integer>();
            ResultSet num = s.executeQuery("SELECT ID as something FROM MOVIE;");
            while(num.next())
            {
                lv.getItems().add(num.getInt("something"));
            }
            ResultSet r = s.executeQuery("SELECT NAME FROM movie;");
            left_list.getItems().clear();
            while (r.next()) {
                left_list.getItems().add(r.getString(1));
            }
            ResultSet r1 = s.executeQuery("SELECT MID FROM poster;");
            posterno.getItems().clear();
            while (r1.next()) {
                posterno.getItems().add(r1.getInt("MID"));
            }

            ResultSet r2 = s.executeQuery("SELECT PATH FROM poster;");
            poster_list.getItems().clear();
            while (r2.next()) {
                poster_list.getItems().add(r2.getString("PATH"));
            }

            int i,j=1,i1=1;
            for(i=1;i<=listView.getItems().size();i++)
            {
                int k=listView.getItems().get(i-1);
                j=i/9+1;
                i1=i%9;
                if(i1==0)
                    i1=9;
                if(posterno.getItems().contains(k))
                {
                    path = poster_list.getItems().get(posterno.getItems().indexOf(k));
                }
                Label yr = new Label();
                Label al = new Label(left_list.getItems().get(lv.getItems().indexOf(k)));
                Image image = new Image(path);
                ImageView imgview = new ImageView(image);
                imgview.setFitHeight(266);
                imgview.setFitWidth(180);
                imgview.setLayoutX(210*i1-180);
                imgview.setLayoutY(316*j-264);
                anchor.getChildren().add(imgview);
                al.setLayoutX(210*i1-180);al.setLayoutY(316*j+4);al.setPrefSize(150,25);
                al.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 15px;");
                anchor.getChildren().add(al);
                ResultSet r3 = s.executeQuery(" select YEAR from movie natural join movie_det where ID="+k+";");
                while (r3.next()) {
                    yr.setText(Integer.toString(r3.getInt("YEAR")));
                }
                Label rating = new Label();
                rating.setPrefHeight(25);
                rating.setStyle("-fx-font-weight: bold; -fx-text-fill: White;-fx-font-size: 17px;");
                ResultSet r4 = s.executeQuery(" select RATING from movie natural join movie_det where ID="+k+";");
                while (r4.next()) {
                    rating.setText(Double.toString(r4.getDouble("RATING")));
                }
                rating.setText(rating.getText().indexOf(".") < 0 ? rating.getText() : rating.getText().replaceAll("0*$", "").replaceAll("\\.$", ""));
                rating.setText(rating.getText()+"/10");
                rating.setLayoutX(210*i1-60);
                rating.setLayoutY(316*j-30);
                yr.setLayoutX(210*i1-180);yr.setLayoutY(316*j+22);yr.setPrefSize(150,25);
                yr.setStyle("-fx-font-weight: bold;  -fx-text-fill: #5b5b5b;-fx-font-size: 13px;");
                anchor.getChildren().add(yr);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.35);
                DropShadow borderGlow = new DropShadow();
                borderGlow.setColor(Color.DEEPSKYBLUE);
                borderGlow.setHeight(30);
                borderGlow.setWidth(30);
                borderGlow.setInput(colorAdjust);
                imgview.setOnMouseEntered(event -> {
                    imgview.setEffect(borderGlow);
                    anchor.getChildren().add(rating);
                });
                imgview.setOnMouseExited(event -> {
                    imgview.setEffect(null);
                    anchor.getChildren().remove(rating);
                });

                int finalI = i;
                imgview.setOnMouseClicked(event -> {

                    Parent root = null;
                    integer = finalI;
                    try {
                        root = FXMLLoader.load(getClass().getResource("movie.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Main.stage.hide();
                    Main.stage.setScene(new Scene(root, 1920, 1080));
                    Main.stage.setMaxHeight(1080);
                    Main.stage.setMaxWidth(1920);
                    Main.stage.show();
                });
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

        search.setFocusTraversable(false);
        search.setOnAction(event -> {
            if(1==1)
            {
                try {
                    display(SearchController.onClickSearch(search.getText()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        sp.setContent(anchor);
        outer.getChildren().addAll(sp);
        outer.getChildren().add(search);
        outer.getChildren().add(icon);
        outer.getChildren().add(back);
        outer.getChildren().add(logout);
        Main.stage.setScene(new Scene(outer, 1920, 1080));
        Main.stage.hide();
        Main.stage.setMaxHeight(1080);
        Main.stage.setMaxWidth(1920);
        Main.stage.show();
        Main.stage.setMaximized(true);
    }

    public void forgot(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akhil", "root", "root");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("select password from creds where uname='"+username.getText()+"';");
            r.next();
            AlertWindow.login("Password is "+r.getString("password"),"Continue");


        }catch (Exception e){
            AlertWindow.login("Write Correct Username ","Ok");
        }

    }
}
