package players;
import java.util.Arrays;
import java.util.List;

import main.CheatUtilities;
import main.Utilities;

public class SatanBot extends Bot {
	//SatanBot attempts to win as many games as possible against the cheating bot in a 1v1.
	//It must play first, and it must guess the exact maximum guess every time to win.
	//It is impossible to win every time
	//It picks whichever number it has the most of, then predicts the number of that roll which is most likely to win.
	//In a 5-dice 1v1 with GodBot, it wins ~0.5% of the time if it goes first
	//In a 3-dice 1v1, it wins ~5.4% of the time.
	public SatanBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		//always challenge if this bot gets a second turn
		if(prevGuesses.size() >= stillIn.size()) return 0;
		//if bot does not go first, increment number
		//this forces opponent to challenge, giving this bot the turn again
		if(prevGuesses.size() > 0) {
			return prevGuesses.get(prevGuesses.size()-1) + 10;
		}
		//if bot goes first, calculate the most likely maximum roll
		int[] myDice = new int[7];
		for(int i = 1; i <= 6; i++) {
			myDice[i] = countDice(i);
		}
		int myDie = 0;
		int numMyDie = 0;
		for(int i = 1; i <= 6; i++) {
			if(myDice[i] >= numMyDie) {
				myDie = i;
				numMyDie = myDice[i];
			}
		}
		//calculate probabilities
		//probability a given number of myDie is the best call =
		//(probability of exactly that many of myDie) * (prob of no other die beating it)
		double bestProb = -1;
		int bestGuessNum = 0;
		int numOfOtherDice = Utilities.countDice(Players) - numDice;
		//n is number of myDie on other people's dice
		for(int n = 0; n <= numOfOtherDice; n++) {
			double probThatManyRolled = Utilities.ProbOfExact[numOfOtherDice][n];
			int numToBeat = numMyDie + n;
			int numRandomDice = numOfOtherDice - n;
			int countWins = 0;
			int numSims = 1000;
			for(int i = 1; i <= numSims; i++) {
				int[] simDice = Arrays.copyOf(myDice, 7);
				for(int j = 1; j <= numRandomDice; j++) {
					simDice[roll1Random(myDie)]++;
				}
				boolean isBest = true;
				for(int j = 1; j < myDie; j++) {
					if(simDice[j] > numToBeat) isBest = false;
				}
				for(int j = myDie+1; j <= 6; j++) {
					if(simDice[j] >= numToBeat) isBest = false;
				}
				if(isBest) countWins++;
			}
			double probThisCountWins = 1.0*countWins/numSims;
			double overallProb = probThatManyRolled*probThisCountWins;
			if(overallProb > bestProb) {
				bestProb = overallProb;
				bestGuessNum = numToBeat;
			}
			else {
				break;
			}
		}
		return bestGuessNum*10 + myDie;
	}
	private static int roll1Random(int toAvoid) {
		int base = rnd.nextInt(5) + 1;
		if(base == toAvoid) base = 6;
		return base;
	}
}
