package players;

import java.util.List;

public abstract class Bot extends Player{
	public Bot( ) {
		isPerson = false;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		System.out.println("Check your code.  Guess method implemented incorrectly.");
		System.out.println("Guess method should have form");
		System.out.println("	'public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses)'");
		return -1;
	}
}
