package players;
import java.util.List;

import main.Main;
import main.Utilities;

public class Person extends Player {
	public Person(String n) {
		name = n;
		isPerson = true;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevCalls) {
		System.out.println(name + ": It is your turn.  You have " + numDice + " dice.");
		System.out.print("Your dice are:");
		for(int d : Dice) {
			System.out.print("  " + d);
		}
		System.out.println();
		System.out.print("Enter your guess: ");
		String input = Main.sc.nextLine();
		input = input.toUpperCase();
		if(input.charAt(0) == 'H') {
			Help();
			return Guess(Players, stillIn, prevCalls);
		}
		if(input.charAt(0) == 'C' || input.charAt(0) == '0') {
			if(prevCalls.size() == 0) {
				System.out.println("Cannot challenge on the first turn!  Try again.");
				return Guess(Players, stillIn, prevCalls);
			}
			System.out.println("You have challenged!");
			return 0;
		}
		if(input.charAt(0) == 'D') {
			System.out.println("There are " + Utilities.countDice(Players) + " total dice.");
			for(Player p : Players) {
				if(p == this) {
					System.out.println("You have " + numDice + " dice.");
				}
				else {
					System.out.println("Player " + p.name + " has " + p.numDice + " dice.");
				}
			}
			return Guess(Players, stillIn, prevCalls);
		}
		while(input.contains(" ")) {
			input = input.replace(" ", "");
		}
		if(input.charAt(0) < '1' || input.charAt(0) > '9') {
			System.out.println("Invalid input.  Try again.");
			return Guess(Players, stillIn, prevCalls);
		}
		int data = 0;
		try {
			data = Integer.parseInt(input);
		}
		catch (Exception e) {
			System.out.println("Invalid input.  Try again.");
			return Guess(Players, stillIn, prevCalls);
		}
		if(prevCalls.size() > 0 && data <= prevCalls.get(prevCalls.size() - 1)) {
			System.out.println("Must input a higher guess than the previous player.  Try again.");
			return Guess(Players, stillIn, prevCalls);
		}
		int numOnDice = data%10;
		int numOfDice = data/10;
		if(numOnDice == 0 || numOnDice > 6) {
			System.out.println("Must input a valid number on the dice.  Try again.");
			return Guess(Players, stillIn, prevCalls);
		}
		System.out.println("You guessed " + numOfDice + " " + numOnDice + "s.");
		System.out.println();
		return data;
	}
	private void Help() {
		System.out.println("Help Screen");
		System.out.println("-Input 'H' for Help.");
		System.out.println("-Input a guess of 2 5s as either '25' or '2 5'.");
		System.out.println("-Input '0' or 'C' to challenge the previous guess");
		System.out.println("-Input 'D' to see the total number of dice in play");
	}
}
