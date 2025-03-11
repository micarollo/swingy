package com.swingy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;

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
            // dropHeroesTable();
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
                    "y INTEGER NOT NULL," +
                    "weaponBoost INTEGER DEFAULT 0," +
                    "armorBoost INTEGER DEFAULT 0," +
                    "helmBoost INTEGER DEFAULT 0" +
                    ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveHero(Hero hero) {
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO heroes (name, class, level, experience, attack, defense, hitPoints, x, y, weaponBoost, armorBoost, helmBoost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, hero.getName());
            statement.setString(2, hero.getHeroClass());
            statement.setInt(3, hero.getLevel());
            statement.setInt(4, hero.getExperience());
            statement.setInt(5, hero.getAttack());
            statement.setInt(6, hero.getDefense());
            statement.setInt(7, hero.getHitPoints());
            statement.setInt(8, hero.getX());
            statement.setInt(9, hero.getY());
            statement.setInt(10, hero.getWeapon() != null ? hero.getWeapon().getBoost() : 0);
            statement.setInt(11, hero.getArmor() != null ? hero.getArmor().getBoost() : 0);
            statement.setInt(12, hero.getHelm() != null ? hero.getHelm().getBoost() : 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHero(String name) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Heroes WHERE name = ?")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("Hero deleted!");
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
                        ", Weapon: " + resultSet.getInt("weaponBoost") +
                        ", Armor: " + resultSet.getInt("armorBoost") +
                        ", Helm: " + resultSet.getInt("helmBoost") +
                        ", x: " + resultSet.getInt("x") +
                        ", y: " + resultSet.getInt("y")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateHero(Hero hero) {
        String checkHeroSQL = "SELECT COUNT(*) FROM heroes WHERE name = ?";
        String updateHeroSQL = "UPDATE heroes SET level = ?, experience = ?, attack = ?, defense = ?, hitPoints = ?, x = ?, y = ? WHERE name = ?";
        String insertHeroSQL = "INSERT INTO heroes (name, class, level, experience, attack, defense, hitPoints, x, y) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkHeroSQL)) {
            checkStmt.setString(1, hero.getName());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            
            if (rs.getInt(1) > 0) {
                // Si el héroe existe, hacer un UPDATE
                try (PreparedStatement updateStmt = conn.prepareStatement(updateHeroSQL)) {
                    updateStmt.setInt(1, hero.getLevel());
                    updateStmt.setInt(2, hero.getExperience());
                    updateStmt.setInt(3, hero.getAttack());
                    updateStmt.setInt(4, hero.getDefense());
                    updateStmt.setInt(5, hero.getHitPoints());
                    updateStmt.setInt(6, hero.getX());
                    updateStmt.setInt(7, hero.getY());
                    updateStmt.setString(8, hero.getName());
                    updateStmt.executeUpdate();
                }
            } else {
                // Si el héroe no existe, hacer un INSERT
                try (PreparedStatement insertStmt = conn.prepareStatement(insertHeroSQL)) {
                    insertStmt.setString(1, hero.getName());
                    insertStmt.setString(2, hero.getHeroClass());
                    insertStmt.setInt(3, hero.getLevel());
                    insertStmt.setInt(4, hero.getExperience());
                    insertStmt.setInt(5, hero.getAttack());
                    insertStmt.setInt(6, hero.getDefense());
                    insertStmt.setInt(7, hero.getHitPoints());
                    insertStmt.setInt(8, hero.getX());
                    insertStmt.setInt(9, hero.getY());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHero(Hero hero) {
        String updateHeroSQL = "UPDATE heroes SET level = ?, experience = ?, attack = ?, defense = ?, hitPoints = ?, x = ?, y = ?, weaponBoost = ?, armorBoost = ?, helmBoost = ? WHERE name = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateHeroSQL)) {
            updateStmt.setInt(1, hero.getLevel());
            updateStmt.setInt(2, hero.getExperience());
            updateStmt.setInt(3, hero.getAttack());
            updateStmt.setInt(4, hero.getDefense());
            updateStmt.setInt(5, hero.getHitPoints());
            updateStmt.setInt(6, hero.getX());
            updateStmt.setInt(7, hero.getY());
            updateStmt.setInt(8, hero.getWeapon() != null ? hero.getWeapon().getBoost() : 0);
            updateStmt.setInt(9, hero.getArmor() != null ? hero.getArmor().getBoost() : 0);
            updateStmt.setInt(10, hero.getHelm() != null ? hero.getHelm().getBoost() : 0);
            updateStmt.setString(11, hero.getName());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHeroPos(int newX, int newY, String name) {
        String updateHeroSQL = "UPDATE heroes SET x = ?, y = ? WHERE name = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateHeroSQL)) {
            updateStmt.setInt(1, newX);
            updateStmt.setInt(2, newY);
            updateStmt.setString(3, name);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Hero getHeroById(int id) {
        String sql = "SELECT * FROM heroes WHERE id = ?";
        Hero hero = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Recupera los datos del héroe
                String name = rs.getString("name");
                String heroClass = rs.getString("class");
                int level = rs.getInt("level");
                int experience = rs.getInt("experience");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                int hitPoints = rs.getInt("hitPoints");
                int x = rs.getInt("x");
                int y = rs.getInt("y");

                // Crea el objeto Hero con los datos obtenidos
                switch (heroClass.toLowerCase()) {
                    case "warrior":
                        hero = new Warrior(name, level, attack, defense, hitPoints, experience, x, y);
                        break;
                    case "mage":
                        hero = new Mage(name, level, attack, defense, hitPoints, experience, x, y);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return hero;
    }

    public int[] getHeroBoosts(String heroName) {
        String sql = "SELECT weaponBoost, armorBoost, helmBoost FROM heroes WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, heroName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new int[]{
                        rs.getInt("weaponBoost"),
                        rs.getInt("armorBoost"),
                        rs.getInt("helmBoost")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void dropHeroesTable() {
        try (Statement statement = conn.createStatement()) {
            String sql = "DROP TABLE IF EXISTS heroes";
            statement.executeUpdate(sql);
            System.out.println("La tabla 'heroes' ha sido eliminada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}