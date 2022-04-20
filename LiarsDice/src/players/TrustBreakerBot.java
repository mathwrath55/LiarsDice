package players;

import java.util.List;

import main.Utilities;

public class TrustBreakerBot extends Bot {
	//TrustBreakerBot is designed to bully TrustBot
	//Its initial guess is always however many dice it has, in 6s.
	//If it gets a second turn, it always challenges.
	//With 5 dice each, it wins a 1v1 against TrustBot ~100% of the time
	//With 3 dice each, it wins 99.95% of the time
	
	public TrustBreakerBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		int numPrevGuesses = prevGuesses.size();
		if(numPrevGuesses < stillIn.size()) {
			int myGuess = 10*numDice + 6;
			if(numPrevGuesses > 0) {
				if(myGuess <= prevGuesses.get(numPrevGuesses-1)) {
					return 0;
				}
			}
			return myGuess;
		}
		return 0;
	}
}