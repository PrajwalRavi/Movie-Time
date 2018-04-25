import java.sql.*;
import java.util.ArrayList;

import javafx.scene.control.TextField;

public class UpdateController {

    public void UpdateFunction() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ LoginController.name,"test","password");
        Statement p = con.createStatement();
        String[] queries = new String[outer.size()];
        String[] keys = new String[4];
        for (int i = 0;i < outer.size();i++){
            queries[i] = "";
        }
        int mid = outer[0];
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
        for(int i = 1; i < outer.size();i++) {
            String table = outer[i][1];
            int key = 0;
            int index = 0;
            if(table.equals("MOVIE") || table.equals("MOVIE_DET")){
                key = mid;
                index = 2;
                keys[index] = "ID";
            }
            else if(table.equals("MOVCAST") || table.equals("POSTER") || table.equals("SUBTITLES")){
                key = mid;
                index = 1;
                keys[index] = "MID";
            }
            else if(table.equals("DIRECTOR")){
                key = did;
                index = 0;
                keys[index] = "DID";
            }
            else{
                key = pid;
                index = 3;
                keys[index] = "PID";
            }
            queries[i-1] = "UPDATE " + table + " SET ";
            for (int j = 1; j < outer[i].size; j += 2){
                queries[i-1] += "," + outer[i][j] + " = " + outer[i][j + 1];
            }
            queries[i-1] += " WHERE " + keys[index] + "= " + key +  ";";
        }
        for(int i = 0;i < outer.size();i++){
            p.executeUpdate(queries[i]);
        }
        p.close();
    }
}
