// package com.swingy.model;

// import java.sql.SQLException;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.swingy.DbManager;

// public class HeroDbTest {

//     private DbManager dbManager;
//     private Hero hero;

//     @BeforeEach
//     public void setUp() throws SQLException {
//         // Crear una base de datos temporal en memoria para los tests
//         dbManager = new DbManager();

//         // Crear un héroe de prueba
//         hero = new HeroTest("TestHero", 1, 100, 10, 10, 100, 0, 0);
//     }

//     @Test
//     public void testSaveWeaponBoostToDB() {
//         // Asignar un arma con boost
//         Weapon weapon = new Weapon(20);
//         hero.equipArtifact(weapon);

//         // Guardar el héroe en la base de datos
//         dbManager.saveHero(hero);

//         // Obtener los boosts desde la DB
//         int[] boosts = dbManager.getHeroBoosts(hero.getName());

//         // Comprobar que weaponBoost se guardó correctamente
//         assertNotNull(boosts);
//         assertEquals(20, boosts[0]); // weaponBoost
//     }

//     @Test
//     public void testInitialAttackWithoutBoost() {
//         // Comprobar que el ataque inicial del héroe es el esperado (sin boost)
//         assertEquals(10, hero.getAttack());
//     }

//     @Test
//     public void testLoadHeroFromDBWithBoosts() {
//         // // Asignar un arma, armadura y casco
//         // hero.equipArtifact(new Weapon(20));
//         // hero.equipArtifact(new Armor(15));
//         // hero.equipArtifact(new Helm(10));

//         // // Guardar el héroe en la base de datos
//         // dbManager.saveHero(hero);

//         // // Cargar el héroe desde la DB
//         // int id = dbManager.getHeroIdByName("TestHero");
//         // Hero loadedHero = dbManager.getHeroById(id);

//         // // Verificar que los boosts se asignaron correctamente
//         // assertNotNull(loadedHero.getWeapon());
//         // assertNotNull(loadedHero.getArmor());
//         // assertNotNull(loadedHero.getHelm());
//         // assertEquals(20, loadedHero.getWeapon().getBoost());
//         // assertEquals(15, loadedHero.getArmor().getBoost());
//         // assertEquals(10, loadedHero.getHelm().getBoost());
//         int id = dbManager.getHeroIdByName("TestHero");
//         Hero loadedHero = dbManager.getHeroById(id);
//         assertNotNull(loadedHero);
//         assertNotNull(loadedHero.getWeapon());
//         assertEquals(30, loadedHero.getBoostAttack());
//     }
// }
