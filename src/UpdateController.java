import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class UpdateController implements Initializable {
    public  int i = LoginController.integer;
    public  static ListView<String> left_list = LoginController.left_list;
    public  static ListView<Integer> posterno=LoginController.posterno;
    public  static ListView<String> poster_list=LoginController.poster_list;
    Hashtable h1 = new Hashtable(7);

    String path;

    @FXML
    ImageView image;
    @FXML
    TextField name;
    @FXML
    TextField director;
    @FXML
    TextField directorage;
    @FXML
    TextField casts;
    @FXML
    TextField years;
    @FXML
    TextField time;
    @FXML
    TextField size;
    @FXML
    TextField Genre;
    @FXML
    TextField boxs;
    @FXML
    TextField production;
    @FXML
    TextField ceo;
    @FXML
    TextField Rating;
    @FXML
    TextField sublang;
    @FXML
    Label moviep;
    @FXML
    Label subp;
    @FXML
    FontAwesomeIconView plus;
    @FXML
    AnchorPane background;
    String posterpath;
    Connection con;
    public int k;
    Statement s;
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "test", "password");
            s = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update() throws SQLException{

        String query1 =  "SELECT PID FROM MOVIE_DET WHERE ID = ?;";
        String query2 = "SELECT DID FROM MOVIE_DET WHERE ID = ?;";
        PreparedStatement p1 = con.prepareStatement(query1);
        PreparedStatement p2 = con.prepareStatement(query2);
        p1.setInt(1,k);
        p2.setInt(1,k);
        ResultSet r1 = p1.executeQuery();
        ResultSet r2 = p2.executeQuery();
        r1.next();
        r2.next();
        int pid = r1.getInt("PID");
        int did = r2.getInt("DID");
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE SET NAME = "+ newValue + "WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        director.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE DIRECTOR SET NAME = " + newValue + "WHERE DID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        directorage.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE DIRECTOR SET AGE = " + newValue + "WHERE DID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,did);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        casts.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVCAST SET CNAME = " + newValue+" WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        years.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET YEAR = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        time.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET LENGTH = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        size.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET SIZE = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Genre.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE GENRE SET TYPE = " + newValue +" WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        boxs.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET BOX = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        production.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE PRODHOUSE SET NAME = " + newValue+ " WHERE PID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,pid);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        ceo.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE PRODHOUSE SET CEO = " + newValue+ " WHERE PID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,pid);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Rating.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET RATING = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        sublang.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE SUBTITLES SET LANG = " + newValue + " WHERE MID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(1,k);
                p3.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

   

    public void insertposter() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            image.setImage(new Image("file:" + file.getAbsolutePath()));
            posterpath = "file:" + file.getAbsolutePath();
            background.getChildren().remove(plus);
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
            r3 = s.executeQuery(" select * from movie_det where ID="+k+";");
            r3.next();
            years.setText(Integer.toString(r3.getInt("YEAR")));
            Rating.setText(Double.toString(r3.getDouble("RATING")));
            Rating.setText(Rating.getText().indexOf(".") < 0 ? Rating.getText() : Rating.getText().replaceAll("0*$", "").replaceAll("\\.$", ""));
            Rating.setText(Rating.getText());
            Genre.setText(r3.getString("GENRE"));
            boxs.setText(Integer.toString(r3.getInt("BOX")));
            r3 = s.executeQuery("select * from director natural join movie_det where ID="+k+";");
            r3.next();
            director.setText(r3.getString("NAME"));
            directorage.setText(Integer.toString(r3.getInt("AGE")));
            r3 = s.executeQuery("select CNAME from movcast where MID="+k+";");
            r3.next();
            casts.setText(r3.getString("CNAME"));
            r3 = s.executeQuery("select * from prodhouse natural join movie_det where ID="+k+";");
            r3.next();
            production.setText(r3.getString("NAME"));
            ceo.setText(r3.getString("CEO"));
            r3 = s.executeQuery("select * from subtitles where MID="+k+";");
            r3.next();
            sublang.setText(r3.getString("LANG"));
            subp.setText(r3.getString("PATH"));
            r3 = s.executeQuery("select PATH from movie where ID="+k+";");
            r3.next();
            moviep.setText(r3.getString("PATH"));
            ////////////////////////////////////////***********/////////////////////////////////////////
            //////////////////////////////////////////////*/////////////////////////////////////////////
            /////////////////////// RUNNING TIME & SIZE  ////////////////////////////////////////////////

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(posterno.getItems().contains(k))
        {
            path = poster_list.getItems().get(posterno.getItems().indexOf(k));
        }
        image.setImage(new Image(path));
    }

    public void choosemovie() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            moviep.setText(file.getAbsolutePath().toString());
            moviep.setAlignment(Pos.CENTER_LEFT);
        }
    }

    public void choosesub() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            subp.setText(file.getAbsolutePath().toString());
            subp.setAlignment(Pos.CENTER_LEFT);
        }
    }

    public void back() {
        LoginController l = new LoginController();
        l.display();
    }
}
