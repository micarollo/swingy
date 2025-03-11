package com.swing.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.swingy.model.Armor;
import com.swingy.model.Hero;
import com.swingy.model.Weapon;

public class HeroTest extends Hero {
    public HeroTest(String name, int level, int experience, int attack, int defense, int hitPoints, int x, int y) {
        super(name, level, experience, attack, defense, hitPoints, x, y);
    }
    
    @Override
    public String getHeroClass() {
        return "TestHero";
    }

    @Test
    public void testEquipArtifactWeapon() {
        Hero hero = new HeroTest("TestHero", 1, 100, 10, 10, 100, 0, 0);
        Weapon weapon = new Weapon(20);
        hero.equipArtifact(weapon);
        assertEquals(weapon, hero.getWeapon());
    }

    @Test
    public void testEquipArtifactArmor() {
        Hero hero = new HeroTest("TestHero", 1, 100, 10, 10, 100, 0, 0);
        Armor armor = new Armor(15);
        hero.equipArtifact(armor);
        assertEquals(armor, hero.getArmor());
    }

}
