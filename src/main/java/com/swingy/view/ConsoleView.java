package com.swingy.view;

import java.util.Scanner;

import com.swingy.model.Artifact;
import com.swingy.model.Hero;
import com.swingy.model.Map;
import com.swingy.model.Villain;

public class ConsoleView {
    Scanner scanner = new Scanner(System.in);
    
    public enum UnicodeSymbol {
        HEART("\u2665"),
        PUMPKIN("\uD83C\uDF83"),
        SKULL("\u2620"),
        SWORD("\u2694"),
        SHIELD("\u1F6E1"),
        VILLAINS("\uD83D\uDC3A \uD83D\uDC7F \uD83E\uDDDB ");

        private final String symbol;

        UnicodeSymbol(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }   

    public int displayWelcomeMessage() {
        System.out.println("Welcome to Swingy RPG!");
        System.out.println("1. Play with an existing Hero");
        System.out.println("2. Create a new one");
        int choice = 0;
        boolean valid = false;
        while (!valid) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
            
        }
        return choice;
    }

    public int chooseHeroClass() {
        System.out.println("Choose your Hero class:");
        System.out.println("1. Warrior [Attack:10 | Defense:8 | HP:40]");
        System.out.println("2. Mage [Attack:8 | Defense:10 | HP:40]");

        int choice = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter the number of the class you want: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    public String chooseHeroName() {
        System.out.print("Enter your hero's name: ");
        return scanner.nextLine();
    }

    public int displayMenu() {
        // int choice;
        String input;
        System.out.println("\nWhat do you want to do now?");
        System.out.println("1. Move North");
        System.out.println("2. Move South");
        System.out.println("3. Move East");
        System.out.println("4. Move West");
        System.out.println("5. View Hero Stats");
        System.out.println("6. Exit Game");
        // choice = scanner.nextInt();
        // scanner.nextLine();
        input = scanner.nextLine();
        try {
            int option = Integer.parseInt(input);
            if (option >= 1 && option <= 6) {
                return option;
            } else {
                System.out.println("Error: enter a number between 1 and 6");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: enter a valid number.");
            return -1; // Valor de error
        }
    }

    public int displayFightorRun() {
        int choice;
        System.out.println("A villain is here! Choose your action:");
        System.out.println("1. Fight");
        System.out.println("2. Run");
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void displayExitMessage() {
        System.out.println("Exiting game. Goodbye!");
    }

    public void displayHeroCreation(String heroClass, String heroName) {
        System.out.println("Successfully created a " + heroClass + ": " + heroName);
    }
    
    public void displayHeroStats(Hero hero) {
        System.out.println();
        System.out.println(hero.getHeroClass() + " Stats for " + hero.getName() + ":");
        System.out.println("Level: " + hero.getLevel());
        System.out.println("HitPoints: " + hero.getHitPoints());
        System.out.println("Attack: " + hero.getAttack());
        System.out.println("Defense: " + hero.getDefense());
        System.out.println("Experience: " + hero.getExperience());
        System.out.println();
    }

    public void displayExperienceGain(Hero hero, int xp) {
        System.out.println(hero.getName() + " won " + xp + " XP. Total: " + hero.getExperience());
        System.out.println("Current Level: " + hero.getLevel());
    }

    public void displayMap(Map map) {
        int size = map.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int cell = map.getCell(i, j);
                switch (cell) {
                    case 0:
                        System.out.print("[ ]");
                        break;
                    case 1:
                        System.out.print("[V]");
                        break;
                    case 2:
                        System.out.print("[P]");
                        break;
                }
            }
            System.out.println();
        }
    }

    public void runMsg() {
        System.out.println();
        System.out.println(UnicodeSymbol.PUMPKIN + "You runned like a coward!");
        System.out.println();
    }

    // public void displayExperienceGained(int xpGained, Hero hero) {
    //     System.out.println("You gained " + xpGained + " experience.");
    //     System.out.println("Total Experience: " + hero.getExperience());
    //     System.out.println("Current Level: " + hero.getLevel());
    // }

    public void gameOver() {
        System.out.println("\uD83D\uDC80 The villain won. GAME OVER");
        System.exit(0);
    }

    public void displayVillainStats(Villain villain) {
        System.out.println("-------------------------------");
        System.out.println("VILLAIN STATS");
        System.out.print("HP: " + villain.getHitPoints() + "| ");
        System.out.print("Attack: " + villain.getAttack() + "| ");
        System.out.println("Defense: " + villain.getDefense());
        System.out.println("-------------------------------");
    }

    public void villainAppears(Villain villain) {
        System.out.println();
        System.out.println(UnicodeSymbol.VILLAINS + "A " + villain.getName() + " appears!!" + UnicodeSymbol.VILLAINS);
        System.out.println();
    }

    public void displayHeroBattleStats(Hero hero) {
        System.out.println("-------------------------------");
        System.out.println("HERO STATS");
        System.out.print("HP: " + hero.getHitPoints() + "| ");
        System.out.print("Attack: " + hero.getAttack() + "| ");
        System.out.println("Defense: " + hero.getDefense());
        System.out.println("-------------------------------");
    }

    public int artifactMsg(Artifact artifact) {
        int choice;
        System.out.println("New artifact: " + artifact.getType() + " | Boost: " + artifact.getBoost());
        System.out.println("What do you want to do:");
        System.out.println("1. Keep it");
        System.out.println("2. Leave it");
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public void displayActualArtifacts(Hero hero) {
        System.out.println();
        System.out.println("Actual artifacts boost");
        if (hero.getWeapon() != null)
            System.out.print("Attack: +" + hero.getWeapon().getBoost());
        else
            System.out.print("Attack: +0");
        if (hero.getHelm() != null)
            System.out.print(" | HP: +" + hero.getHelm().getBoost());
        else
            System.out.print(" | HP: +0");
        if (hero.getArmor() != null)
            System.out.println(" | Defense: +" + hero.getArmor().getBoost());
        else
            System.out.println(" | Defense: +0");
        System.out.println();
    }
}