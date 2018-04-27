import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.sql.*;

public class SearchController {

    public static ListView<Integer> onClickSearch( String word ) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "root", "root");
        ListView<Integer> ids = new ListView<Integer>();
        Statement s = con.createStatement();
        ResultSet movies = s.executeQuery("SELECT movie.ID as ID FROM MOVIE JOIN MOVIE_DET WHERE MOVIE.ID=MOVIE_DET.ID AND (MOVIE.NAME LIKE '%"+word+"%' OR GENRE LIKE '%"+word+"%');");
        while (movies.next())
        {
            int a = movies.getInt("ID");
            if(ids.getItems().contains(a)==false)
                ids.getItems().add(a);

        }
        ResultSet dirs =  s.executeQuery("SELECT ID FROM DIRECTOR JOIN MOVIE_DET WHERE DIRECTOR.DID=MOVIE_DET.DID AND NAME LIKE '%"+word+"%';");
        while (dirs.next())
        {
            int a = dirs.getInt("ID");
            if(ids.getItems().contains(a)==false)
                ids.getItems().add(a);

        }
        ResultSet houses = s.executeQuery("SELECT ID FROM MOVIE_DET JOIN PRODHOUSE WHERE MOVIE_DET.PID=PRODHOUSE.PID AND NAME LIKE '%"+word+"%';");
        while (houses.next())
        {
            int a = houses.getInt("ID");
            if(ids.getItems().contains(a)==false)
                ids.getItems().add(a);

        }
        ResultSet cast = s.executeQuery("SELECT DISTINCT MID FROM MOVCAST WHERE CNAME LIKE '%"+word+"%';");
        while (cast.next())
        {
            int a = cast.getInt("MID");
            if(ids.getItems().contains(a)==false)
                ids.getItems().add(a);

        }
        return ids;


        /*ResultSet result = s.executeQuery("SELECT * FROM MOVIE_DET,MOVIE WHERE MOVIE.ID=MOVIE_DET.ID AND "+fiter.getText()+" LIKE '%"+word.getText()+"'%;");
        StringBuilder results = new StringBuilder();    //stores ALL the results by appending temp
        while(result.next())
        {
            String temp = "";   //stores EACH result's details
            int did = result.getInt("DID");
            int pid = result.getInt("PID");
            ResultSet dir = s.executeQuery("SELECT NAME FROM DIRECTOR WHERE DID="+did);
            ResultSet producer = s.executeQuery("SELECT NAME FROM PRODHOUSE WHERE PID="+pid);
            temp+= result.getString("NAME") + result.getInt("YEAR") + result.getDouble("RATING") +
                    result.getString("GENRE") + result.getString("BOX") + dir.getString("NAME") +
                    producer.getString("NAME");
            results.append(temp).append("\n\n");
        }*/
    }

    /*public void onClickGroup() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "test", "password");
        Statement s = con.createStatement();
        String category = fiter.getText();
        if(category.equalsIgnoreCase("director"))
        {
            ResultSet result = s.executeQuery("SELECT DIRECTOR.NAME,MOVIE.NAME FROM ((MOVIE INNER JOIN MOVIE_DET ON " +
                    "MOVIE.ID=MOVIE_DET.ID) INNER JOIN DIRECTOR ON DIRECTOR.DID = MOVIE_DET.DID) ORDER BY DIRECTOR.NAME;");
        }
        else if(category.equalsIgnoreCase("prod house"))
        {
            ResultSet result = s.executeQuery("SELECT PRODHOUSE.NAME,MOVIE.NAME FROM ((MOVIE INNER JOIN MOVIE_DET ON MOVIE.ID=MOVIE_DET.ID) INNER JOIN PRODHOUSE ON PRODHOUSE.PID = MOVIE_DET.PID) ORDER BY PRODHOUSE.NAME;");
        }
    }*/

}
