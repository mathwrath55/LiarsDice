package players;
import java.util.List;

import main.Utilities;

public class ExpectedBot extends Bot {
	//ExpectedBot calculates the expected number of each value on the table based on its dice and the number of remaining dice.
	//It rounds down whatever has the highest expected number.
	//If its highest guess is unavailable, it raises count by 1.
	//It challenges if the expected number is lower than the guessed number.
	public ExpectedBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		int lastGuess = 0;
		if(prevGuesses.size() > 0) {
			lastGuess = prevGuesses.get(prevGuesses.size()-1);
		}
		double[] Expected = new double[7];
		int[] roundExpected = new int[7];
		int myGuess = 0;
		int totalDiceInPlay = Utilities.countDice(Players);
		double expectedInOtherHands = (totalDiceInPlay - numDice)/6.0;
		for(int i = 1; i <= 6; i++) {
			Expected[i] = countDice(i) + expectedInOtherHands;
			roundExpected[i] = (int)(Expected[i] + 0.01);
		}
		for(int i = 1; i <= 6; i++) {
			int guessHere = roundExpected[i]*10 + i;
			if(guessHere > myGuess) {
				myGuess = guessHere;
			}
		}
		if(myGuess == lastGuess) myGuess += 10;
		if(myGuess < lastGuess) myGuess = 0;
		return myGuess;
	}
}
