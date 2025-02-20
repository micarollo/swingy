package com.swingy;

public abstract class Character {
    protected String name;
    protected int level;
    protected int attack;
    protected int defense;
    protected int hitPoints;

    public Character(String name, int level, int attack, int defense, int hitPoints) {
        this.name = name;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.hitPoints = hitPoints;
    }

    public void takeDamage(int damage) {
        int reducedDamage = Math.max(0, damage - defense);
        hitPoints -= reducedDamage;
        System.out.println(name + " recibió " + reducedDamage + " de daño. HP restante: " + hitPoints);
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHitPoints() { return hitPoints; }
}