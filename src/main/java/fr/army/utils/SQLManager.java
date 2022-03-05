package fr.army.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.army.App;

public class SQLManager {
    private String database;
    private Connection connection;

    public SQLManager() {
        this.database = "data.db";
    }


    public boolean isConnected() {
        return connection == null ? false : true;
    }


    public void connect() throws ClassNotFoundException, SQLException{
        if(!isConnected()){
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+App.instance.getDataFolder().getAbsolutePath()+"/"+this.database);
        }
    }


    public void disconnect() {
        if(isConnected()){
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Connection getConnection() {
        return connection;
    }


    public boolean isRegistered(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT playername FROM players WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                boolean isParticipant = result.next();
                query.close();
                return isParticipant;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean isDisable(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT particle FROM particles WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                query.close();
                if (result.next()) {
                    return result.getString("particle").contains("Disable");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public void insertPlayer(String playername){
        if(isConnected()){
            try {
                PreparedStatement queryPlayers = connection.prepareStatement("INSERT INTO players (id, playername) VALUES (null, ?)");
                queryPlayers.setString(1, playername);
                queryPlayers.executeUpdate();
                queryPlayers.close();

                PreparedStatement queryParticles = connection.prepareStatement("INSERT INTO particles (id, playername, particle) VALUES (null, ?, ?)");
                queryParticles.setString(2, playername);
                queryParticles.setString(3, "Disable");
                queryParticles.executeUpdate();
                queryParticles.close();

                PreparedStatement querySounds = connection.prepareStatement("INSERT INTO sounds (id, playername, sound) VALUES (null, ?, ?)");
                querySounds.setString(2, playername);
                querySounds.setString(3, "Disable");
                querySounds.executeUpdate();
                querySounds.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateParticle(String playername, String particle){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("UPDATE particles SET particle = ? WHERE playername = ?");
                query.setString(1, playername);
                query.setString(2, particle);
                query.executeUpdate();
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateSound(String playername, String sound){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("UPDATE sounds SET sound = ? WHERE playername = ?");
                query.setString(1, playername);
                query.setString(2, sound);
                query.executeUpdate();
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public String getParticle(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT particle FROM particles WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                query.close();
                if(result.next()){
                    return result.getString("particle");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}