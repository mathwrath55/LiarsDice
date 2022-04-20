package players;

import java.util.List;

import main.Utilities;

public class TrustBot extends Bot {
	//TrustBot assumes the immediately previous player has what they just called, if that is mathematically possible
	//It adjusts its expected value table based on that.
	//It calls only if a guess is impossible
	//It will make its highest available guess if this does not match the previous call, and will raise this by 1 die otherwise.
	//TrustBot was designed to beat NaiveBot, which it does approximately 81% of the time in a 1v1 with 5 dice, 99.7% with 10 dice
	public TrustBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		if(prevGuesses.size() == 0) {
			int myGuess = 0;
			for(int i = 1; i <= 6; i++) {
				int countVal = countDice(i);
				if(10*countVal + i > myGuess) {
					myGuess = 10*countVal + i;
				}
			}
			return myGuess;
		}
		int lastGuess = prevGuesses.get(prevGuesses.size()-1);
		int prevPlayerIndex = prevIndex(Players, stillIn);
		Player prevPlayer = Players.get(prevPlayerIndex);
		
		int lastNumOfDice = lastGuess / 10;
		int lastNumOnDice = lastGuess % 10;
		
		double[] Expected = new double[7];
		int myGuess = 0;
		int totalDiceInPlay = Utilities.countDice(Players);
		if(lastNumOfDice > totalDiceInPlay - numDice + countDice(lastNumOnDice)) {
			//challenge a mathematically impossible call
			//added after I realized two TrustBots could get stuck in an infinite loop of repeatedly incrementing
			return 0;
		}
		double expectedInOtherHands = (totalDiceInPlay - numDice-prevPlayer.numDice)/6.0 + (prevPlayer.numDice - lastNumOfDice)/5.0;
		for(int i = 1; i <= 6; i++) {
			if(i != lastNumOnDice) {
				Expected[i] = countDice(i) + expectedInOtherHands;
			}
			else {
				int unaccountedDice = totalDiceInPlay - numDice - prevPlayer.numDice;
				Expected[i] = unaccountedDice / 6.0 + countDice(i) + lastNumOfDice;
			}
		}
		for(int i = 1; i <= 6; i++) {
			int roundExpected = (int)Expected[i];
			int guessHere = roundExpected*10 + i;
			if(guessHere > myGuess) {
				myGuess = guessHere;
			}
		}
		if(myGuess == lastGuess) myGuess += 10;
		if(myGuess < lastGuess) myGuess = 0;
		return myGuess;
	}
}