package com.swingy;

import com.swingy.controller.GameController;

public class App 
{
	public static void main( String[] args )
	{
		if (args.length != 1)
		{
			System.out.println("Usage: java -jar swingy.jar <console/gui>");
			return;
		}
		if (args[0].equals("console")) 
		{
			GameController gameController = new GameController();
			gameController.startGame();
		}
		else if (args[0].equals("gui"))
		{
			GameController gameController = new GameController();
			gameController.startGuiModeGame();
		}
		else
			System.out.println("Must be: console o gui");
	}
}