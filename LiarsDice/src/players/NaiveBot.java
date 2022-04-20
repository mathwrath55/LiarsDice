package players;
import java.util.List;

public class NaiveBot extends Bot {
	//NaiveBot will always guess the highest guess including only its own dice if that is available.
	//If something higher is guessed, it will always challenge.
	//If the previous call matches its calculated call, it will raise it by 1 die 
	public NaiveBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		int lastGuess = 0;
		if(prevGuesses.size() > 0) {
			lastGuess = prevGuesses.get(prevGuesses.size()-1);
		}
		int myGuess = 0;
		for(int i = 1; i <= 6; i++) {
			int countVal = countDice(i);
			if(10*countVal + i > myGuess) {
				myGuess = 10*countVal + i;
			}
		}
		if(myGuess > lastGuess) return myGuess;
		if(myGuess < lastGuess) return 0;
		return myGuess+10;
	}
}
