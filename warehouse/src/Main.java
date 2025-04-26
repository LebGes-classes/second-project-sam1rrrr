import ui.ui;

import java.sql.SQLException;
import db.DataBase;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBase dataBase = new DataBase();
        DataBase.createTables();
        ui ui = new ui();
        ui.start();
    }
}