import java.sql.*;

public class test {

    public static void main(String[] args) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/p", "root", "root");
        Statement p = con.createStatement();
        String movie_name = "As";
        int year = 2013;
        double rating = 9.2;
        String genre = "fiction";
        String spath = "";
        String ppath = "/home/prajwal/Downloads/img.jpg";
        String mpath = "/media/prajwal/A47E84577E8423E0/Desktop/Heaven/Movies/Phase2/Avengers.mkv";
        String dname = "Nolan";
        int dage = 75;
        String prodname = "Marvel";
        String prodceo = "Bill Gte";
        String cast = "Robert Downey";
        String slang = "Eng";
        double box = 1;


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
        s.execute();

        //movie cast
        PreparedStatement s7 = con.prepareStatement("INSERT INTO MOVCAST(MID,CNAME) VALUES(?,?);");
        s7.setInt(1, m_id);
        s7.setString(2, cast);
        s7.execute();

        ResultSet r = con.createStatement().executeQuery("SELECT * FROM MOVIE");
        while (r.next())
        System.out.println(r.getString("NAME"));
    }
}
