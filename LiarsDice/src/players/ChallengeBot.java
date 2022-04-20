package players;
import java.util.List;

public class ChallengeBot extends Bot {
	//ChallengeBot always challenges.  If it starts a round, it guesses 1 1.
	public ChallengeBot(String n) {
		name = n;
	}
	public int Guess(List<Player> Players, List<Integer> stillIn, List<Integer> prevGuesses) {
		if(prevGuesses.size() > 0) {
			return 0;
		}
		return 11;
	}
}
