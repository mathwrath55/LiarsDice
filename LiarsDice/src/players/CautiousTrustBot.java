package players;

import java.util.List;

import main.Utilities;

public class CautiousTrustBot extends Bot {
	//CautiousTrustBot assumes everyone had exactly one of what they called.
	//Wins 98.9% against TrustBreakerBot
	//Wins 71% against NaiveBot
	//Wins 57% against TrustBot
	//Loses 97% against SatanBot
	public CautiousTrustBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		if(prevGuesses.size() == 0) {
			int myGuess = 0;
			int totalUnknown = Utilities.countDice(Players)-numDice;
			for(int i = 1; i <= 6; i++) {
				int countVal = countDice(i);
				if(rnd.nextInt(10) < totalUnknown) countVal++;
				if(10*countVal + i > myGuess) {
					myGuess = 10*countVal + i;
				}
			}
			return myGuess;
		}
		int lastGuess = prevGuesses.get(prevGuesses.size()-1);
		int prevPlayerIndex = prevIndex(Players, stillIn);
		int nextPlayerIndex = nextIndex(Players, stillIn);
		Player prevPlayer = Players.get(prevPlayerIndex);
		Player nextPlayer = Players.get(nextPlayerIndex);
		
		int lastNumOfDice = lastGuess / 10;
		int lastNumOnDice = lastGuess % 10;
		
		double[] Known = new double[7];
		int myGuess = 0;
		int totalDiceInPlay = Utilities.countDice(Players);
		if(lastNumOfDice > totalDiceInPlay - numDice + countDice(lastNumOnDice)) {
			//challenge a mathematically impossible call
			//added after I realized two TrustBots could get stuck in an infinite loop of repeatedly incrementing
			return 0;
		}
		int unknownDice = totalDiceInPlay - numDice;
		int numPrevGuesses = prevGuesses.size();
		int numPlayers = stillIn.size();
		for(int i = 0; i < numPrevGuesses; i++) {
			if((numPrevGuesses-i)%numPlayers == 0) continue;
			int guessDie = prevGuesses.get(i)%10;
			Known[guessDie]++;
			unknownDice--;
		}
		int nextPlayerLastGuess = -1;
		if(numPrevGuesses >= numPlayers-1) {
			nextPlayerLastGuess = prevGuesses.get(numPrevGuesses-numPlayers+1);
		}
		if(unknownDice < 0) return 0;
		double[] Value = new double[7];
		for(int i = 1; i <= 6; i++) {
			Known[i] += countDice(i);
			Value[i] = Known[i] + unknownDice/6.0 + ((i==nextPlayerLastGuess%10)?0.5:0);
			//System.out.println(i + ", " + Value[i]);
		}
		double maxValue = 0;
		int bestDie = 0;
		for(int i = 1; i <= 6; i++) {
			if(Value[i] > maxValue) {
				maxValue = Value[i];
				bestDie = i;
			}
		}
		myGuess = 10*(int)Value[bestDie] + bestDie;
		if(myGuess == lastGuess) myGuess += 10;
		if(myGuess < lastGuess) myGuess = 0;
		return myGuess;
	}
}