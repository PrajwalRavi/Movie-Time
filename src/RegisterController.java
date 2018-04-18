import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {

    public TextField uname, pass;

    public void createTables(String user, String pass) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + user, "test", "password");
        Statement s = con.createStatement();
        int a = 0;
        a = s.executeUpdate("CREATE TABLE PRODHOUSE(ID INT PRIMARY KEY,NAME VARCHAR(100),CEO VARCHAR(100));");
        a = s.executeUpdate("CREATE TABLE DIRECTOR(DID INT PRIMARY KEY, NAME VARCHAR(80), AGE INT NOT NULL);");
        a = s.executeUpdate("CREATE TABLE MOVIE(ID INT  PRIMARY KEY,PATH VARCHAR(100) NOT NULL,NAME VARCHAR(100) NOT NULL);");
        int r = s.executeUpdate("ALTER TABLE MOVIE MODIFY ID INT(11) NOT NULL AUTO_INCREMENT;");
        a = s.executeUpdate("CREATE TABLE MOVIE_DET(ID INT ,DID INT ,PID INT ,YEAR YEAR,RATING DOUBLE, GENRE VARCHAR(20),WATCHED BIT,BOX DOUBLE,FOREIGN KEY A(ID) REFERENCES MOVIE(ID),FOREIGN KEY B(DID) REFERENCES DIRECTOR(ID),FOREIGN KEY D(PID) REFERENCES PRODHOUSE(DID));");
        a = s.executeUpdate("CREATE TABLE POSTER(MID INT,PID INT PRIMARY KEY,PATH VARCHAR(200), FOREIGN KEY A(MID) REFERENCES MOVIE_DET(ID));");
        a = s.executeUpdate("CREATE TABLE SUBTITLES(MID INT ,SID INT PRIMARY KEY ,PATH VARCHAR(200),LANG VARCHAR(10),FOREIGN KEY A(MID) REFERENCES MOVIE_DET(ID));");
        a = s.executeUpdate("CREATE TABLE MOVCAST(ID INT PRIMARY KEY,MID INT,CNAME VARCHAR(300),FOREIGN KEY A(MID) REFERENCES MOVIE_DET(ID));");


    }

    public void onClickRegister() throws SQLException {
        String user = uname.getText();
        String password = pass.getText();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/planet", "test", "password");
        Statement s = con.createStatement();
        int result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + user + ";");
         s.execute("grant all privileges on " + user + ".* to '" + user + "'@'localhost' identified by '" + password + "'");
        createTables(user, password);

    }
}
