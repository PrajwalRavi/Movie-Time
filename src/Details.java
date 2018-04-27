import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

public class Details {

    public void getUserMovies() throws SQLException {
        String name = LoginController.name;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name, "test", "password");
        ResultSet movies = con.createStatement().executeQuery("SELECT * FROM MOVIE JOIN MOVIE_DET WHERE MOVIE.ID=MOVIE_DET.ID;");
        ResultSet dirs =  con.createStatement().executeQuery("SELECT * FROM DIRECTOR JOIN MOVIE_DET WHERE DIRECTOR.DID=MOVIE_DET.DID;");
        ResultSet houses = con.createStatement().executeQuery("SELECT * FROM MOVIE_DET JOIN PRODHOUSE WHERE MOVIE_DET.PID=PRODHOUSE.PID;");
        ResultSet cast = con.createStatement().executeQuery("SELECT * FROM MOVIE_DET JOIN MOVCAST WHERE MOVIE_DET.ID=MOVCAST.MID;");
        try {
            PrintWriter out = new PrintWriter("/home/prajwal/Desktop/movies.txt");
            out.println("\t\t\t\t\t\t\tMOVIES");
            while (movies.next() && dirs.next() && houses.next() && cast.next())
            {
                ResultSet cas = con.createStatement().executeQuery("SELECT CNAME FROM MOVCAST WHERE MID="+movies.getInt("ID"));
                String str="";
                while (cas.next())
                    str+=cas.getString("CNAME")+" , ";
                String st="";
                cas = con.createStatement().executeQuery("SELECT TYPE FROM GENRE WHERE ID="+movies.getInt("ID"));
                while (cas.next())
                    st+=cas.getString("TYPE")+" , ";
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        con.close();
    }

    public void getDirectors() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+LoginController.name, "test", "password");
        ResultSet dir = con.createStatement().executeQuery("SELECT * FROM DIRECTOR;");
        try {
            PrintWriter out = new PrintWriter("/home/prajwal/Desktop/director.txt");
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
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+LoginController.name, "test", "password");
        ResultSet pro = con.createStatement().executeQuery("SELECT * FROM PRODHOUSE;");
        try {
            PrintWriter out = new PrintWriter("/home/prajwal/Desktop/prod.txt");
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

}
