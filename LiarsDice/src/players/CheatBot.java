package players;
import java.util.List;

import main.CheatUtilities;

public class CheatBot extends Bot {
	//CheatBot looks at everyone's dice and makes the highest possible guess.
	//If the previous person made that exact guess, CheatBot raises it by 1 die
	//This is because CheatBot wants to start each round
	//CheatBot only calls if the previous guess was higher than the highest possible guess.
	//If CheatBot gets the first turn of the game, it is literally unbeatable- even by another CheatBot.
	public CheatBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		int maxGuess = 0;
		for(int i = 1; i <= 6; i++) {
			int num = CheatUtilities.countDice(Players, i);
			if(10*num + i > maxGuess) maxGuess = 10*num+i;
		}
		if(prevGuesses.size() == 0) return maxGuess;
		int prevGuess = prevGuesses.get(prevGuesses.size()-1);
		if(maxGuess > prevGuess) return maxGuess;
		if(maxGuess == prevGuess) return maxGuess+10;
		return 0;
	}
}
