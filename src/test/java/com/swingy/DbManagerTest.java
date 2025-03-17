package com.swingy;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.swingy.model.Hero;
import com.swingy.model.Warrior;

public class DbManagerTest {
    private DbManager dbManager;

    @BeforeEach
    public void setUp() {
        System.out.println("Ejecutando setUp...");
        dbManager = new DbManager("jdbc:sqlite:test.db"); //switch to memory?
        dbManager.createHeroesTable();
        assertNotNull(dbManager, "dbManager no debería ser null");
    }

    @Test
    public void testSaveHero() {
        System.out.println("Ejecutando testSaveHero...");
        Hero hero = new Warrior("TestHero", 1, 10, 5, 100, 0, 0, 0);
        dbManager.saveHero(hero);

        Hero retrievedHero = dbManager.getHeroById(1);
        assertNotNull(retrievedHero);
        assertEquals("TestHero", retrievedHero.getName());
    }

    @Test
    public void testDeleteHero() {
        Hero hero = new Warrior("DeleteMe", 1, 10, 5, 100, 0, 0, 0);
        dbManager.saveHero(hero);
        dbManager.deleteHero("DeleteMe");

        Hero deletedHero = dbManager.getHeroById(1);
        assertNull(deletedHero);
    }

    @Test
    public void testUpdateHero() {
        Hero hero = new Warrior("UpdateMe", 1, 10, 5, 100, 0, 0, 0);
        dbManager.saveHero(hero);

        hero.setLevel(2);
        dbManager.updateHero(hero);

        Hero updatedHero = dbManager.getHeroById(1);
        assertEquals(2, updatedHero.getLevel());
    }

    @Test
    public void testSaveOrUpdateHero_Insert() {
        Hero hero = new Warrior("InsertMe", 1, 10, 5, 100, 0, 0, 0);
        dbManager.saveOrUpdateHero(hero);

        Hero retrievedHero = dbManager.getHeroById(1);
        assertNotNull(retrievedHero);
        assertEquals("InsertMe", retrievedHero.getName());
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dbManager = null;
    }
}