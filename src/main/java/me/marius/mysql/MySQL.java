package me.marius.mysql;

import java.sql.*;
import java.util.ArrayList;

public class MySQL {

    private static Connection con;
    private String host = "localhost";
    private int port = 3306;
    private String database = "discordbot";
    private String username = "root";
    private String password = "pP8nt2J9t5xpdUiN";

    private boolean isRunningCreateNewPlayer;
    private boolean isRunningUpdatePlayer;

    public boolean connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?allowMultiQueries=true",username,password);
            System.out.println("[BaumbalabungaBot] Die Verbindung zur MySQL-Datenbank wurde hergestellt!");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[BaumbalabungaBot] Die Verbindung zur MySQL-Datenbank wurde nicht hergestellt!");
        }
        return false;
    }

    public boolean isConnected() { return (con == null ? false : true); }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean userIsExisting(String userID){
        if(!isConnected())
            if(!connect())
                return false;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("UserID").equals(userID))
                    return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void createTables(){
        if(!isConnected())
            return;

        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS Ranking (UserID VARCHAR(100), Username VARCHAR(100), Punkte INT, Nachrichten INT, Reaktionen INT, ChannelTime TIMESTAMP); CREATE TABLE IF NOT EXISTS Wartungen (WartungBoolean BOOLEAN NOT NULL);");
            ps.executeUpdate();

            PreparedStatement ps1 = con.prepareStatement("SELECT EXISTS (SELECT 1 FROM Wartungen)");
            ResultSet rs = ps1.executeQuery();
            while(rs.next()) {
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Wartungen (WartungBoolean) VALUES (FALSE)");
                ps2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createNewPlayer(String userID, String userName, int punkte, int messages, int reactions, int channeltime){

        isRunningCreateNewPlayer = !isRunningCreateNewPlayer;

        new Thread(() -> {
            while (isRunningCreateNewPlayer){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isConnected())
                    if(!userIsExisting(userID))
                        return;

                try {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ranking (UserID,Username,Punkte,Nachrichten,Reaktionen,ChannelTime) VALUES (?,?,?,?,?,?)");
                    ps.setString(1, userID);
                    ps.setString(2, userName);
                    ps.setInt(3, punkte);
                    ps.setInt(4, messages);
                    ps.setInt(5, reactions);
                    ps.setInt(6, 0);
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                isRunningCreateNewPlayer = false;
            }
        }).start();
    }

    public void setPunkte(String userID, String username, int punkte, int nachrichten, int reaktionen){

        isRunningUpdatePlayer = !isRunningUpdatePlayer;

        new Thread(() -> {
            while(isRunningUpdatePlayer){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isConnected())
                    if(!userIsExisting(userID))
                        return;

                try {
                    PreparedStatement ps = con.prepareStatement("UPDATE ranking SET Username = ?, Punkte = ?, Nachrichten = ?, Reaktionen = ? WHERE UserID = ?");
                    ps.setString(1, username);
                    ps.setInt(2, getPunkte(userID)+punkte);
                    ps.setInt(3, getNachrichten(userID)+nachrichten);
                    ps.setInt(4, getReaktionen(userID)+reaktionen);
                    ps.setString(5, userID);
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                isRunningUpdatePlayer = false;
            }
        }).start();
    }

    public int getReaktionen(String userID){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT Reaktionen FROM ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("Reaktionen");
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getNachrichten(String userID){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT Nachrichten FROM ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("Nachrichten");
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getPunkte(String userID){

        try {
            PreparedStatement ps = con.prepareStatement("SELECT Punkte FROM ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                return rs.getInt("Punkte");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getRanking(){
        if(!isConnected())
            if(!connect())
                return null;

        ArrayList<String> ranking = new ArrayList<>();
        ranking.clear();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ranking ORDER BY Punkte DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ranking.add(rs.getString("UserID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ranking;
    }

    public int getRank(String UserID){
        if (!isConnected())
            if (!connect())
                return 0;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) From ranking WHERE Punkte >= (SELECT Punkte FROM ranking WHERE UserID = ?)");
            ps.setString(1, UserID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("COUNT(*)");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
