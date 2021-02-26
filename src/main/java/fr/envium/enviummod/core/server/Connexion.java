package fr.envium.enviummod.core.server;

import fr.envium.enviummod.api.config.ConfigHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {

    /*private static String machine = "51.210.108.210:3306";
    private static String users = "romain";
    private static String pw = "11IPOD11";*/

    private static String machine = (String) ConfigHandler.SERVER.machine.get();
    private static String users = (String) ConfigHandler.SERVER.user.get();
    private static String pw = (String) ConfigHandler.SERVER.mdp.get();

        Statement statement;

    private static Connection connection;
    private static boolean reconnect = false;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+machine+"/envium?user="+users+"&password="+pw);
            System.out.println("conexion etabli");
        } catch (SQLException throwables) {
            if (false) {
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://"+machine+"/envium?user="+users+"&password="+pw);
                    reconnect = false;
                } catch (SQLException exception) {
                    throwables.printStackTrace();
                    reconnect = true;
                }
            } else {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
