// package com.swingy.model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// public class HeroTest {
//     private Hero hero;

//     @BeforeEach
//     public void setUp() {
//         hero = new Warrior("Test Hero", 1, 10, 5, 100, 0, 0, 0);
//     }

//     @Test
//     public void testInitialHeroAttributes() {
//         assertEquals("Test Hero", hero.getName());
//         assertEquals(1, hero.getLevel());
//         assertEquals(10, hero.getAttack());
//         assertEquals(5, hero.getDefense());
//         assertEquals(100, hero.getHitPoints());
//         assertEquals(0, hero.getExperience());
//         assertEquals(0, hero.getX());
//         assertEquals(0, hero.getY());
//     }

//     @Test
//     public void testGainExperience() {
//         hero.gainExperience(500);
//         assertEquals(500, hero.getExperience());
//     }

//     @Test
//     public void testLevelUp() {
//         hero.gainExperience(1500);
//         if (hero.needToLevelUp()) {
//             int requiredXP = hero.calculateLevelUp(hero.getLevel());
//             hero.levelUp(requiredXP);
//             assertEquals(2, hero.getLevel());
//         }
//     }

//     @Test
//     public void testEquipWeapon() {
//         Weapon sword = new Weapon(5);
//         hero.equipArtifact(sword);
//         assertEquals(sword, hero.getWeapon());
//     }

//     @Test
//     public void testEquipArmor() {
//         Armor plateArmor = new Armor(3);
//         hero.equipArtifact(plateArmor);
//         assertEquals(plateArmor, hero.getArmor());
//     }

//     @Test
//     public void testEquipHelm() {
//         Helm ironHelm = new Helm(2);
//         hero.equipArtifact(ironHelm);
//         assertEquals(ironHelm, hero.getHelm());
//     }

//     @Test
//     public void testEquipMultipleArtifacts() {
//         Weapon sword = new Weapon(5);
//         Armor plateArmor = new Armor(3);
//         Helm ironHelm = new Helm(2);
        
//         hero.equipArtifact(sword);
//         hero.equipArtifact(plateArmor);
//         hero.equipArtifact(ironHelm);
        
//         assertEquals(sword, hero.getWeapon());
//         assertEquals(plateArmor, hero.getArmor());
//         assertEquals(ironHelm, hero.getHelm());
//     }
// }
