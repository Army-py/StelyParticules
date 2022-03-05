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


    public void createTables(){
        if (isConnected()){
            try {
                PreparedStatement queryParticles = connection.prepareStatement("CREATE TABLE IF NOT EXISTS 'particles' ('id' INTEGER, 'playername' TEXT, 'particle' TEXT, PRIMARY KEY('id' AUTOINCREMENT));");
                queryParticles.executeUpdate();
                queryParticles.close();

                PreparedStatement querySounds = connection.prepareStatement("CREATE TABLE IF NOT EXISTS 'sounds' ('id' INTEGER, 'playername' TEXT, 'sound' TEXT, PRIMARY KEY('id' AUTOINCREMENT));");
                querySounds.executeUpdate();
                querySounds.close();

                PreparedStatement queryPlayers = connection.prepareStatement("CREATE TABLE IF NOT EXISTS 'players' ('id' INTEGER, 'playername' TEXT, PRIMARY KEY('id' AUTOINCREMENT));");
                queryPlayers.executeUpdate();
                queryPlayers.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
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


    public boolean isDisableParticles(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT particle FROM particles WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                Boolean disable = null;
                if (result.next()) {
                    disable = result.getString("particle").contains("Disable");
                }
                query.close();
                return disable;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean isDisableSounds(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT sound FROM sounds WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                Boolean disable = null;
                if (result.next()) {
                    disable = result.getString("sound").contains("Disable");
                }
                query.close();
                return disable;
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
                queryParticles.setString(1, playername);
                queryParticles.setString(2, "Disable");
                queryParticles.executeUpdate();
                queryParticles.close();

                PreparedStatement querySounds = connection.prepareStatement("INSERT INTO sounds (id, playername, sound) VALUES (null, ?, ?)");
                querySounds.setString(1, playername);
                querySounds.setString(2, "Disable");
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
                query.setString(1, particle);
                query.setString(2, playername);
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
                query.setString(1, sound);
                query.setString(2, playername);
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
                String particle = null;
                if(result.next()){
                    particle = result.getString("particle");
                }
                query.close();
                return particle;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String getSound(String playername){
        if(isConnected()){
            try {
                PreparedStatement query = connection.prepareStatement("SELECT sound FROM sounds WHERE playername = ?");
                query.setString(1, playername);
                ResultSet result = query.executeQuery();
                String sound = null;
                if(result.next()){
                    sound = result.getString("sound");
                }
                query.close();
                return sound;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}