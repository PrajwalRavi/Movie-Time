import java.sql.*;

public class Details {

    public void getUserDetails() throws SQLException {
        String name = LoginController.name;
        String pass = LoginController.pass;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name, "test", "password");
        Statement s = con.createStatement();
        ResultSet details = s.executeQuery("SELECT * FROM creds WHERE uname='"+name+"' AND password='"+pass+"'");
    }

    public void getUserMovies() throws SQLException {
        String name = LoginController.name;
        String pass = LoginController.pass;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name, "test", "password");
        Statement s = con.createStatement();
        ResultSet movies = s.executeQuery("SELECT * FROM MOVIE JOIN MOVIE_DET WHERE MOVIE.ID=MOVIE_DET.ID;");
        ResultSet dirs =  s.executeQuery("SELECT * FROM DIRECTOR JOIN MOVIE_DET WHERE DIRECTOR.DID=MOVIE_DET.DID;");
        ResultSet houses = s.executeQuery("SELECT * FROM MOVIE_DET JOIN PRODHOUSE WHERE MOVIE_DET.PID=PRODHOUSE.PID;");
        while (movies.next() && dirs.next() && houses.next())
        {
            //do front-end stuff
        }
    }
}
