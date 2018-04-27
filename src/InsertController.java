
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
    @FXML Label size;
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
    TextField message;
    String posterpath;
    public int l=1;
    public void InsertIntoTables() throws SQLException {
        try {
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
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "root", "root");
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

        int p_id,d_id,m_id;
        //message.setEditable(false);

            //prodhouse
            ResultSet r = con.createStatement().executeQuery("SELECT * FROM PRODHOUSE WHERE NAME='" + prodname + "'");
            if (r.next()) {
                p_id = r.getInt("PID");
            } else {
                PreparedStatement s = con.prepareStatement("INSERT INTO PRODHOUSE(NAME,CEO) VALUES(?,?);");
                s.setString(1, prodname);
                s.setString(2, prodceo);
                s.execute();
                String query1 = "SELECT PID FROM PRODHOUSE WHERE NAME = ?;";
                PreparedStatement p1 = con.prepareStatement(query1);
                p1.setString(1, prodname);
                ResultSet r1 = p1.executeQuery();
                r1.next();
                p_id = r1.getInt("PID");
            }

            //director
            r = con.createStatement().executeQuery("SELECT * FROM DIRECTOR WHERE NAME='" + dname + "';");
            if (r.next()) {
                d_id = r.getInt("DID");
            } else {
                PreparedStatement s2 = con.prepareStatement(("INSERT INTO DIRECTOR(NAME,AGE) VALUES(?,?);"));
                s2.setString(1, dname);
                s2.setInt(2, dage);
                s2.execute();
                String query2 = "SELECT DID FROM DIRECTOR WHERE NAME = ?;";
                PreparedStatement p2 = con.prepareStatement(query2);
                p2.setString(1, dname);
                ResultSet r2 = p2.executeQuery();
                r2.next();
                d_id = r2.getInt("DID");
            }

            //movie
            PreparedStatement s3 = con.prepareStatement("INSERT INTO MOVIE(PATH,NAME) VALUES(?,?);");
            s3.setString(1, mpath);
            s3.setString(2, movie_name);
            s3.execute();

            //movie_det
            String query3 = "SELECT ID FROM MOVIE WHERE NAME = ?;";
            PreparedStatement p3 = con.prepareStatement(query3);
            p3.setString(1, movie_name);
            ResultSet r3 = p3.executeQuery();
            r3.next();
            m_id = r3.getInt("ID");
            PreparedStatement s4 = con.prepareStatement("INSERT INTO MOVIE_DET(ID,DID,PID,YEAR,RATING,WATCHED,BOX) VALUES(?,?,?,?,?,0,?);");
            s4.setInt(1, m_id);
            s4.setInt(2, d_id);
            s4.setInt(3, p_id);
            s4.setInt(4, year);
            s4.setDouble(5, rating);
            s4.setDouble(6, box);
            s4.execute();

            String ar[] = genre.split(",");
            for (int i = 0; i < ar.length; i++)
                con.createStatement().executeUpdate("INSERT INTO GENRE VALUES(" + m_id + ",'" + ar[i] + "');");

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
            con.close();
            back();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void insertposter(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file =fileChooser.showOpenDialog(Main.stage);
        if(file!=null)
        {
            l=0;
            image.setImage(new Image("file:"+file.getAbsolutePath()));
            posterpath = "file:"+file.getAbsolutePath();
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
        if(file!=null) {
            moviep.setText(file.getAbsolutePath());
            moviep.setAlignment(Pos.CENTER_LEFT);

            double s;
            s = file.length();
            if (s > 1024 * 1024 * 102.4) {
                s /= (double) 1024;
                s /= (double) 1024;
                s /= (double) 1024;
                size.setText(Double.toString(s).substring(0, 4));
            } else {
                size.setText("0");
            }
            size.setText(size.getText() + " GB");
        }

    }

    public void choosesub(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file =fileChooser.showOpenDialog(Main.stage);
        if(file!=null)
        {
            subp.setText(file.getAbsolutePath());
            subp.setAlignment(Pos.CENTER_LEFT);
        }
    }

    public void back()
    {
        LoginController l = new LoginController();
        l.display();
    }

}
