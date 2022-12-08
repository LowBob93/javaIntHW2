package HomeWorkFour;

import HomeWorkFour.DB.DbHandler;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DbHandler.conn();
        DbHandler.readDB();
        DbHandler.closeDB();
    }
}