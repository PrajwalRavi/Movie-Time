import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class Details {
    File selectedDirectory;

    public void getUserMovies()  {
        try {
            String name = LoginController.name;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name, "root", "root");
            ResultSet movies = con.createStatement().executeQuery("SELECT * FROM MOVIE JOIN MOVIE_DET WHERE MOVIE.ID=MOVIE_DET.ID;");
            ResultSet dirs =  con.createStatement().executeQuery("SELECT * FROM DIRECTOR JOIN MOVIE_DET WHERE DIRECTOR.DID=MOVIE_DET.DID;");
            ResultSet houses = con.createStatement().executeQuery("SELECT * FROM MOVIE_DET JOIN PRODHOUSE WHERE MOVIE_DET.PID=PRODHOUSE.PID;");
            PrintWriter out = new PrintWriter(selectedDirectory.getAbsoluteFile()+"/movies.txt");
            ResultSet cast = con.createStatement().executeQuery("SELECT * FROM MOVIE_DET JOIN MOVCAST WHERE MOVIE_DET.ID=MOVCAST.MID;");
            out.println("\t\t\t\t\t\t\tMOVIES");
            while (movies.next() && dirs.next() && houses.next() && cast.next())
            {
                ResultSet cas = con.createStatement().executeQuery("SELECT CNAME FROM MOVCAST WHERE MID="+movies.getInt("ID"));
                String str="";
                while (cas.next())
                    str+=cas.getString("CNAME")+", ";
                String st="";
                cas = con.createStatement().executeQuery("SELECT TYPE FROM GENRE WHERE ID="+movies.getInt("ID"));
                while (cas.next()) {
                    st+=cas.getString("TYPE")+" , ";
                }
                str.substring(0,str.length()-2);
                st.substring(0,str.length()-2);
                out.println("Movie name :      "+movies.getString("NAME"));
                out.println("Year of Release:  "+movies.getInt("YEAR"));
                out.println("Box-office:       $"+movies.getInt("BOX")+"000000");
                out.println("Director:         "+dirs.getString("NAME"));
                out.println("Production House: "+houses.getString("NAME"));
                out.println("Cast:             "+str);
                out.println("Genre:            "+st);
                out.println();
            }
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getDirectors() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+LoginController.name, "root", "root");
        ResultSet dir = con.createStatement().executeQuery("SELECT * FROM DIRECTOR;");
        try {

                PrintWriter out = new PrintWriter(selectedDirectory.getAbsoluteFile()+"/director.txt");
                out.println("\t\t\t\t\t\t\tDIRECTORS");
                while (dir.next())
                {
                    out.println("Name: " + dir.getString("NAME"));
                    out.println("Age:  " + String.valueOf(dir.getInt("AGE")));
                    out.println();
                }
                out.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        con.close();
    }

    public void getProds() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+LoginController.name, "root", "root");
        ResultSet pro = con.createStatement().executeQuery("SELECT * FROM PRODHOUSE;");
        try {
            PrintWriter out = new PrintWriter(selectedDirectory.getAbsoluteFile()+"/prod.txt");
            out.println("\t\t\t\t\t\t\tPRODUCTION HOUSES");
            while (pro.next())
            {
                out.println("Name: " + pro.getString("NAME"));
                out.println("CEO:  " + pro.getString("CEO"));
                out.println();
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        con.close();
    }

    public void click() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(Main.stage);
        if(selectedDirectory == null)
            AlertWindow.login("No Directory selected","Try Again");
        else{
            try {
                getDirectors();
                getProds();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getUserMovies();
        }
    }
}
