package com.swingy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.swingy.model.Hero;

public class DbManager{
    private Connection conn;
    
    public DbManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:swingy.db");
            //clean db
            // String dropTableSQL = "DROP TABLE IF EXISTS heroes";
            // Statement stmt = conn.createStatement();
            // stmt.executeUpdate(dropTableSQL);
            createHeroesTable();
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    private void createHeroesTable() {
        try (Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS heroes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "class TEXT NOT NULL," +
                    "level INTEGER NOT NULL," +
                    "experience INTEGER NOT NULL," +
                    "attack INTEGER NOT NULL," +
                    "defense INTEGER NOT NULL," +
                    "hitPoints INTEGER NOT NULL," +
                    "x INTEGER NOT NULL," +
                    "y INTEGER NOT NULL" +
                    ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveHero(Hero hero) {
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO heroes (name, class, level, experience, attack, defense, hitPoints, x, y) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, hero.getName());
            statement.setString(2, hero.getHeroClass());
            statement.setInt(3, hero.getLevel());
            statement.setInt(4, hero.getExperience());
            statement.setInt(5, hero.getAttack());
            statement.setInt(6, hero.getDefense());
            statement.setInt(7, hero.getHitPoints());
            statement.setInt(8, 7);
            statement.setInt(9, 7);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayHeroes() {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM heroes")) {
            System.out.println("Available Heroes:");
            while (resultSet.next()) {
                System.out.println(
                        "ID: " + resultSet.getLong("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Class: " + resultSet.getString("class") +
                        ", Level: " + resultSet.getInt("level") +
                        ", Experience: " + resultSet.getInt("experience") +
                        ", Attack: " + resultSet.getInt("attack") +
                        ", Defense: " + resultSet.getInt("defense") +
                        ", Hit Points: " + resultSet.getInt("hitPoints") +
                        ", x: " + resultSet.getInt("x") +
                        ", y: " + resultSet.getInt("y")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHero() {
        
    }
}