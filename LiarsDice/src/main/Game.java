package main;
import java.util.*;

import players.Player;
public class Game {
	public boolean hasPerson = false;
	public int playerCount;
	public List<Player> Players;
	public List<Player> PlayersStillIn = new ArrayList<Player>();
	public List<Integer> stillIn;
	public int prevPlayer = -1;
	public int curPlayer = -1;
	public Game(List<Player> ps, int eachDice) {
		playerCount = 0;
		Players = ps;
		stillIn = new ArrayList<Integer>();
		for(Player p : Players) {
			p.numDice = eachDice;
			stillIn.add(playerCount);
			PlayersStillIn.add(p);
			playerCount++;
			if(p.isPerson) hasPerson = true; 
		}
		
	}
	public int doGame() {
		while(stillIn.size() > 1) {
			doRound();
		}
		return stillIn.get(0);
		//return Players.get(stillIn.get(0));
	}
	
	public void printResult(int curGuess, Player loser) {
		int callVal = curGuess%10;
		int callNumOfDice = curGuess/10;
		int actualDice = CheatUtilities.countDice(Players, callVal);
		for(int index : stillIn) {
			Player p = Players.get(index);
			System.out.printf("Player %10s had:", p.name);
			for(int d : p.getDice()) {
				System.out.print("  " + d);
			}
			System.out.printf(" (%2d %1ds)\n", p.countDice(callVal), callVal);
		}
		System.out.println("The challenged guess was " + callNumOfDice + " " + callVal + "s.");
		System.out.println("There were actually " + actualDice + " " + callVal + "s.");
		System.out.println("Player " + loser.name + " loses a die and has " + loser.numDice + " remaining.");
	}
	public void doRound() {
		if(hasPerson) {
			System.out.println();
			System.out.println("____________________________________________________");
			System.out.println("A new round begins.  There are " + Utilities.countDice(Players) + " dice remaining.");
			System.out.println();
		}
		List<Integer> prevGuesses = new ArrayList<Integer>();
		for(Player p : Players) {
			p.Roll();
		}
		int curGuess = 1;
		while(curGuess > 0) {
			prevPlayer = curPlayer;
			curPlayer++;
			while(!stillIn.contains(curPlayer)) {
				if(curPlayer == playerCount) {
					curPlayer = 0;
					continue;
				}
				curPlayer++;
			}
			Player curTurn = Players.get(curPlayer);
			curGuess = curTurn.Guess(Players, stillIn, prevGuesses);
			Utilities.checkValid(curGuess, prevGuesses);
			if(hasPerson && !curTurn.isPerson) {
				if(curGuess == 0) {
					System.out.println("Player " + curTurn.name + " challenged!");
					System.out.println();
				}
				else {
					int numOnDice = curGuess%10;
					int numOfDice = curGuess/10;
					System.out.println("Player " + curTurn.name + " guessed " + numOfDice + " " + numOnDice + "s.");
					System.out.println();
				}
			}
			prevGuesses.add(curGuess);
		}
		int calledGuess = prevGuesses.get(prevGuesses.size() - 2);
		int callVal = calledGuess%10;
		int callNumOfDice = calledGuess/10;
		int actualDice = CheatUtilities.countDice(PlayersStillIn, callVal);
		
		int loserIndex = prevPlayer;
		if(callNumOfDice <= actualDice) {
			loserIndex = curPlayer;
		}
		Player losingPlayer = Players.get(loserIndex);
		losingPlayer.numDice--;
		if(hasPerson) printResult(calledGuess, losingPlayer);
		if(losingPlayer.numDice == 0) {
			stillIn.remove(stillIn.indexOf(loserIndex));
			PlayersStillIn.remove(losingPlayer);
			if(hasPerson) {
				if(stillIn.size() > 1) {
					System.out.println("Player " + losingPlayer.name + " is eliminated!  There are " + stillIn.size() + " players remaining.");
				}
				else {
					System.out.println("Player " + losingPlayer.name + " is eliminated!");
					System.out.println();
					System.out.println(Players.get(stillIn.get(0)).name + " has won the game!");
				}
			}
			if(stillIn.size() == 1) {
				Players.get(stillIn.get(0)).Wins++;
			}
		}
		/*prevPlayer = curPlayer;
		do {
			curPlayer++;
			if(curPlayer == playerCount) {
				curPlayer = 0;
			}
		} while(!stillIn.contains(curPlayer));*/
	}
}
