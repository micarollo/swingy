package main.java.com.swingy;

//first idea
public class Swingy {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        Hero hero = new Hero("Mica", "Warrior", 1, 0, 10, 5, 100);
        GameController gameController = new GameController(hero, view);
        gameController.startGame();
    }
}