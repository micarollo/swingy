package main.java.com.swingy.model;

import java.util.Random;

public class Villain {
    private String p_name;
    private int p_level;
    private int p_power; //determinate villain quality and points artifact 
    private int p_attack;
    private int p_defense;
    private int p_hitPoints;
    private Artifact artifact;

    public Villain(int heroLevel) {
        Random random = new Random();
        this.p_name = generateVillainName();
        this.p_level = Math.max(1, heroLevel + random.nextInt(3) - 1);
        this.p_power = (this.p_level * 10) + random.nextInt(10);
        this.p_attack = p_power / 2;
        this.p_defense = p_power / 3;
        this.p_hitPoints = p_power * 2;
    }

    private String generateVillainName() {
        String[] names = {"Goblin", "Orc", "Dark Knight", "Undead Warrior", "Demon"};
        return names[new Random().nextInt(names.length)];
    }

    public Artifact getArtifact() { 
        return artifact; 
    }
}