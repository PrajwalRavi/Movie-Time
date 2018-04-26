
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class InsertController implements Initializable {

    @FXML ImageView image;
    @FXML TextField name;
    @FXML TextField director;
    @FXML TextField directorage;
    @FXML TextField casts;
    @FXML TextField years;
    @FXML TextField time;
    @FXML TextField size;
    @FXML TextField Genre;
    @FXML TextField boxs;
    @FXML TextField production;
    @FXML TextField ceo;
    @FXML TextField Rating;
    @FXML TextField sublang;
    @FXML Label moviep;
    @FXML Label subp;
    @FXML FontAwesomeIconView plus;
    @FXML AnchorPane background;
    String posterpath;

    public void InsertIntoTables() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "root", "root");
        con.setAutoCommit(false);
        Statement p = con.createStatement();
        String movie_name = name.getText();
        int year = Integer.parseInt(years.getText());
        double rating = Double.parseDouble(Rating.getText());
        String genre = Genre.getText();
        String spath = "file:"+subp.getText();
        String ppath = posterpath;
        String mpath = "file:"+moviep.getText();
        String dname = director.getText();
        int dage = Integer.parseInt(directorage.getText());
        String prodname = production.getText();
        String prodceo = ceo.getText();
        String cast  = casts.getText();
        String slang = sublang.getText();
        double box = Double.parseDouble(boxs.getText());


        //prodhouse
        PreparedStatement s = con.prepareStatement("INSERT INTO PRODHOUSE(NAME,CEO) VALUES(?,?);");
        s.setString(1, prodname);
        s.setString(2, prodceo);
        s.execute();

        //director
        PreparedStatement s2 = con.prepareStatement(("INSERT INTO DIRECTOR(NAME,AGE) VALUES(?,?);"));
        s2.setString(1, dname);
        s2.setInt(2, dage);
        s2.execute();

        //movie
        PreparedStatement s3 = con.prepareStatement("INSERT INTO MOVIE(PATH,NAME) VALUES(?,?);");
        s3.setString(1, mpath);
        s3.setString(2, movie_name);
        s3.execute();

        //movie_det
        String query1 = "SELECT PID FROM PRODHOUSE WHERE NAME = ?;";
        String query2 = "SELECT DID FROM DIRECTOR WHERE NAME = ?;";
        String query3 = "SELECT ID FROM MOVIE WHERE NAME = ?;";
        PreparedStatement p1 = con.prepareStatement(query1);
        PreparedStatement p2 = con.prepareStatement(query2);
        PreparedStatement p3 = con.prepareStatement(query3);
        p1.setString(1, prodname);
        p2.setString(1, dname);
        p3.setString(1, movie_name);
        ResultSet r1 = p1.executeQuery();
        r1.next();
        int p_id = r1.getInt("PID");
        ResultSet r2 = p2.executeQuery();
        r2.next();
        int d_id = r2.getInt("DID");
        ResultSet r3 = p3.executeQuery();
        r3.next();
        int m_id = r3.getInt("ID");
        PreparedStatement s4 = con.prepareStatement("INSERT INTO MOVIE_DET(ID,DID,PID,YEAR,RATING,GENRE,WATCHED,BOX) VALUES(?,?,?,?,?,?,0,?);");
        s4.setInt(1, m_id);
        s4.setInt(2, d_id);
        s4.setInt(3, p_id);
        s4.setInt(4, year);
        s4.setDouble(5, rating);
        s4.setString(6, genre);
        s4.setDouble(7, box);
        s4.execute();

        //poster
        PreparedStatement s5 = con.prepareStatement("INSERT INTO POSTER(MID,PID,PATH) VALUES(?,NULL,?);");
        s5.setInt(1, m_id);
        s5.setString(2, ppath);
        s5.execute();

        //subtitles
        PreparedStatement s6 = con.prepareStatement("INSERT INTO SUBTITLES(MID,SID,PATH,LANG) VALUES(?,NULL,?,?);");
        s6.setInt(1, m_id);
        s6.setString(2, spath);
        s6.setString(3, slang);
        s6.execute();

        //movie cast
        PreparedStatement s7 = con.prepareStatement("INSERT INTO MOVCAST(MID,CNAME) VALUES(?,?);");
        s7.setInt(1, m_id);
        s7.setString(2, cast);
        s7.execute();
        con.commit();
        con.close();
        back();
    }

    public void insertposter(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file =fileChooser.showOpenDialog(Main.stage);
        if(file!=null)
        {
            image.setImage(new Image("file:"+file.getAbsolutePath()));
            posterpath = "file:"+file.getAbsolutePath();
            background.getChildren().remove(plus);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.DEEPSKYBLUE);
        borderGlow.setHeight(30);
        borderGlow.setWidth(30);
        image.setImage(new Image("images/new.jpg"));
        image.setEffect(borderGlow);
    }

    public void choosemovie(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file =fileChooser.showOpenDialog(Main.stage);
        if(file!=null)
        {
            moviep.setText(file.getAbsolutePath().toString());
            moviep.setAlignment(Pos.CENTER_LEFT);
        }
    }

    public void choosesub(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file =fileChooser.showOpenDialog(Main.stage);
        if(file!=null)
        {
            subp.setText(file.getAbsolutePath().toString());
            subp.setAlignment(Pos.CENTER_LEFT);
        }
    }

    public void back()
    {
        LoginController l = new LoginController();
        l.display();
    }

}
