package main;

import java.util.List;

import players.Player;

public class CheatUtilities extends Utilities {
	public static int countDice(List<Player> Players, int toCount) {
		if(toCount == 0) return Utilities.countDice(Players);
		int count = 0;
		for(Player p : Players) {
			for(int d : p.getDice()) {
				if(d == toCount) {
					count++;
				}
			}
		}
		return count;
	}
}
