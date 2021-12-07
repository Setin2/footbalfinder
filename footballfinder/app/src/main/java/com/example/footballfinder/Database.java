package com.example.footballfinder;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

    private Connection connection;

    // private final String host = "ssprojectinstance.csv2nbvvgbcb.us-east-2.rds.amazonaws.com"  // For Amazon Postgresql
    private final String host = "abul.db.elephantsql.com";  // For Google Cloud Postgresql
    private final String database = "jgjbpaon";
    private final int port = 5432;
    private final String user = "jgjbpaon";
    private final String pass = "Q6aaQ2H4LOx2DStdTxa5DkPoVKvpoBXs";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    static ArrayList<Field> getFields(Connection connection){
        ArrayList<Field> fields = new ArrayList<>();
        try {
            String sql = "SELECT * FROM fields";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                float lat = resultSet.getFloat("lat");
                float lon = resultSet.getFloat("lon");
                String type = resultSet.getString("type");
                String owner = resultSet.getString("owner");
                fields.add(new Field(id, description, location, lat, lon, type, owner));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return fields;
    }
}