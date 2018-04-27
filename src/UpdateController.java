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
    Label size;
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
    public int l=1;
    Statement s;
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "root", "root");
            s = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update() throws SQLException{

        if(name.getText().equals(""))
            name.setStyle("-fx-border-color: transparent transparent red transparent;");
        if(director.getText().equals(""))
            director.setStyle("-fx-border-color: transparent transparent red transparent;");
        try {
            Integer.parseInt(directorage.getText());
        }catch (Exception e)
        {
            directorage.setStyle("-fx-border-color: transparent transparent red transparent;");
        }
        if(casts.getText().equals(""))
            casts.setStyle("-fx-border-color: transparent transparent red transparent;");
        try {
            Integer.parseInt(years.getText());
        }catch (Exception e)
        {
            years.setStyle("-fx-border-color: transparent transparent red transparent;");
        }
        try {
            Integer.parseInt(boxs.getText());
        }catch (Exception e)
        {
            boxs.setStyle("-fx-border-color: transparent transparent red transparent;");
        }
        try {
            Integer.parseInt(time.getText());
        }catch (Exception e)
        {
            time.setStyle("-fx-border-color: transparent transparent red transparent;");
        }
        if(Genre.getText().equals(""))
            Genre.setStyle("-fx-border-color: transparent transparent red transparent;");
        if(production.getText().equals(""))
            production.setStyle("-fx-border-color: transparent transparent red transparent;");
        if(ceo.getText().equals(""))
            ceo.setStyle("-fx-border-color: transparent transparent red transparent;");
        if(sublang.getText().equals(""))
            sublang.setStyle("-fx-border-color: transparent transparent red transparent;");
        try {
            Integer.parseInt(Rating.getText());
        }catch (Exception e)
        {
            Rating.setStyle("-fx-border-color: transparent transparent red transparent;");
        }
        if(l==1)
        {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.RED);
            borderGlow.setHeight(30);
            borderGlow.setWidth(30);
            image.setImage(new Image("images/new.jpg"));
            image.setEffect(borderGlow);
        }
    }

   

    public void insertposter() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            l=0;
            image.setImage(new Image("file:" + file.getAbsolutePath()));
            posterpath = "file:" + file.getAbsolutePath();
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
            boxs.setText(Integer.toString(r3.getInt("BOX")));
            time.setText(Integer.toString(r3.getInt("LENGTH")));
            size.setText(Integer.toString(r3.getInt("SIZE")));
            r3 = s.executeQuery("select TYPE from genre where ID="+k+";");
            String g ="";
            while(r3.next()) {
                g+=r3.getString("TYPE");
            }
            Genre.setText(g);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(posterno.getItems().contains(k))
        {
            path = poster_list.getItems().get(posterno.getItems().indexOf(k));
        }
        image.setImage(new Image(path));
        String query1 =  "SELECT PID FROM MOVIE_DET WHERE ID = ?;";
        String query2 = "SELECT DID FROM MOVIE_DET WHERE ID = ?;";
        int dide=0,pide=0;
        try {
            PreparedStatement p1 = con.prepareStatement(query1);
            PreparedStatement p2 = con.prepareStatement(query2);
            p1.setInt(1,k);
            p2.setInt(1,k);
            ResultSet r1 = p1.executeQuery();
            ResultSet r2 = p2.executeQuery();
            r1.next();
            r2.next();
            pide = r1.getInt("PID");
            dide = r2.getInt("DID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final int  did = dide;
        final int pid = pide;
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE SET NAME = ? WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setString(1,newValue);
                p3.setInt(2,k);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        director.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE DIRECTOR SET NAME = ? WHERE DID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setString(1,newValue);
                p3.setInt(2,k);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        directorage.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE DIRECTOR SET AGE = ? WHERE DID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setString(1,newValue);
                p3.setInt(2,did);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        casts.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVCAST SET CNAME = ? WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setString(1,newValue);
                p3.setInt(2,k);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        years.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET YEAR = ? WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        time.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET LENGTH = ? WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Genre.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE GENRE SET TYPE = ? WHERE ID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        boxs.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET BOX = ? WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        production.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE PRODHOUSE SET NAME = ? WHERE PID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,pid);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        ceo.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE PRODHOUSE SET CEO = ? WHERE PID = ?;";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,pid);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Rating.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE MOVIE_DET SET RATING = " + newValue + " WHERE ID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        sublang.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = "UPDATE SUBTITLES SET LANG = ? WHERE MID = ?";
            PreparedStatement p3 = null;
            try {
                p3 = con.prepareStatement(query);
                p3.setInt(2,k);
                p3.setString(1,newValue);
                p3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void choosemovie() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file != null) {
            moviep.setText(file.getAbsolutePath().toString());
            moviep.setAlignment(Pos.CENTER_LEFT);
        }
        double s;
        s=file.length();
        if(s>1024*1024*102.4)
        {
            s/=(double)1024;
            s/=(double)1024;
            s/=(double)1024;
            size.setText(Double.toString(s).substring(0,4));
        }
        else {
            size.setText("0");
        }
        size.setText(size.getText()+" GB");
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
