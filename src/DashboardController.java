import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public ListView<String> left_list;
    public TextArea ar;
    Statement s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String name = LoginController.name;
        String pass = LoginController.pass;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + name, "test", "password");
            s = con.createStatement();
            ResultSet r = s.executeQuery("SELECT NAME FROM MOVIE;");
            while (r.next())
            {
                left_list.getItems().add(r.getString("NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void show_det() throws SQLException {
        String sel = left_list.getSelectionModel().getSelectedItem();
        String out="";         //Edit AND FORMAT THIS
        ResultSet mov = s.executeQuery("SELECT * FROM MOVIE_DET,MOVIE WHERE MOVIE_DET.ID=MOVIE.ID AND NAME = '"+sel+"'");
        ResultSet dir = s.executeQuery("SELECT NAME FROM DIRECTOR WHERE DID=" + mov.getString("DID") + ";");
        ResultSet house = s.executeQuery("SELECT NAME FROM PRODHOUSE WHERE ID=" + mov.getString("ID") + ";");

        out+= sel + dir + house + mov.getInt("YEAR") + mov.getDouble("RATING")
                + mov.getString("GENRE" + mov.getInt("BOX"));
        ar.setText(out);
    }



}
