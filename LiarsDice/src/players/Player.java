package players;
import java.util.*;

import main.Utilities;
public abstract class Player {
	public int Wins = 0;
	public int numDice;
	protected List<Integer> Dice;
	public String name = "";
	public boolean isPerson;
	Random rnd = Utilities.rnd;
	
	public void Roll() {
		Dice = new ArrayList<Integer>();
		for(int i = 0; i < numDice; i++) {
			Dice.add(Utilities.Roll1());
		}
	}
	
	public int countDice(int val) {
		int count = 0;
		for(int i = 0; i < Dice.size(); i++) {
			if(Dice.get(i) == val) count++;
		}
		return count;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		//Players is the list of all players who started the game.
		//stillIn is the list of indices within Players of the players remaining in the game.
		//prevGuesses is the list of all previous guesses made in this round.
		//Return a guess in the form 25 = 2 5s, return 0 to challenge.
		return 0;
	}
	
	public List<Integer> getDice() {
		return Dice;
	}
	
	public int myIndex(List<Player> Players, List<Integer> stillIn) {
		//returns the index of this Player in Players
		return Players.indexOf(this);
	}
	public int prevIndex(List<Player> Players, List<Integer> stillIn) {
		//returns the index in Players of the player who played before this player
		int myIndex = myIndex(Players, stillIn);
		int indexInStillIn = stillIn.indexOf(myIndex);
		int numPlayers = stillIn.size();
		int prevIndex = (indexInStillIn + numPlayers - 1)%numPlayers;
		return stillIn.get(prevIndex);
	}

}
