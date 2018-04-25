import java.sql.*;

public class DeleteController {
    public void DeleteFunction() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + LoginController.name, "test", "password");
        int mid = 0;
        String query1 =  "SELECT PID FROM MOVIE_DET WHERE ID = ?;";
        String query2 = "SELECT DID FROM MOVIE_DET WHERE ID = ?;";
        PreparedStatement p1 = con.prepareStatement(query1);
        PreparedStatement p2 = con.prepareStatement(query2);
        p1.setInt(1,mid);
        p2.setInt(1,mid);
        ResultSet r1 = p1.executeQuery();
        ResultSet r2 = p2.executeQuery();
        r1.next();
        r2.next();
        int pid = r1.getInt("PID");
        int did = r2.getInt("DID");
        query1 = "DELETE FROM MOVIE WHERE ID = ?;";
        query2 = "DELETE FROM MOVIE_DET WHERE ID = ?;";
        String query3 = "DELETE FROM POSTER WHERE MID = ?;";
        String query4 = "DELETE FROM SUBTITLES WHERE MID = ?;";
        String query5 = "DELETE FROM MOVCAST WHERE MID = ?;";
        p1 = con.prepareStatement(query1);
        p2 = con.prepareStatement(query2);
        PreparedStatement p3 = con.prepareStatement(query3);
        PreparedStatement p4 = con.prepareStatement(query4);
        PreparedStatement p5 = con.prepareStatement(query5);
        p1.setInt(1,mid);
        p2.setInt(1,mid);
        p3.setInt(1,mid);
        p4.setInt(1,mid);
        p5.setInt(1,mid);
        p1.executeQuery();
        p2.executeQuery();
        p3.executeQuery();
        p4.executeQuery();
        p5.executeQuery();
    }
}
