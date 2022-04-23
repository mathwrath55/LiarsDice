package main;
import java.util.*;

import players.*;
//import players.ChallengeBot;
//import players.ExpectedBot;
//import players.NaiveBot;
//import players.Person;
//import players.Player;
public class Main {
	public static Random rnd = Utilities.rnd;
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		Player Me = new Person("Graham");
		Player NB1 = new NaiveBot("NaiveBot1");
		Player NB2 = new NaiveBot("NaiveBot2");
		Player NB3 = new NaiveBot("NaiveBot3");
		Player GB1 = new GodBot("GodBot1");
		Player GB2 = new GodBot("GodBot2");
		Player SB1 = new SatanBot("SatanBot1");
		Player SB2 = new SatanBot("SatanBot2");
		Player EB1 = new ExpectedBot("EBot1");
		Player CTB1 = new CautiousTrustBot("CTBot1");
		Player TB1 = new TrustBot("TBot1");
		Player TB2 = new TrustBot("TBot2");
		Player TBB1 = new TrustBreakerBot("TBBot1");
		List<Player> Players = new ArrayList<Player>();
		//List<Player> Players = Arrays.asList(EB1, NB1);
		//Players.add(Me);
		Players.add(TB1);
		Players.add(NB1);
		Players.add(TBB1);
		Players.add(CTB1);
		Players.add(SB1);
		//Players.add(GB1);
		//Players.add(GB2);
		//SimulateOne(Players, 5);
		//testOneRound(Players, 15, 100000);
		SimulateMany(Players, 5, 100000);
	}
	public static void testOneRound(List<Player> Players, int numDice, int numGames) {
		int[] countWins = new int[Players.size()];
		for(int i = 1; i <= numGames; i++) {	
			//Collections.shuffle(Players);
			Game newGame = new Game(Players, numDice);
			//newGame.hasPerson = true;
			newGame.doRound();
			for(int k = 0; k < Players.size(); k++) {
				if(Players.get(k).numDice == numDice) {
					countWins[k]++;
					if(numDice > 1 || Players.size() > 2) {
						Players.get(k).Wins++;
					}
				}
			}
			if(i % 1000 == 0) {
				System.out.println(i + " games completed.");
				if(i % 10000 == 0) {
					System.out.println("Current record: ");
					for(int j = 0; j < Players.size(); j++) {
						Player p = Players.get(j);
						System.out.println(p.name + " won: " + p.Wins);
						//System.out.print(countWins[i]);
						//if(i != Players.size()-1) System.out.print("-");
					}
				}
			}
		}
		System.out.println();
		System.out.println("Results");
		for(int i = 0; i < Players.size(); i++) {
			Player p = Players.get(i);
			System.out.print(p.name + " won: " + p.Wins);
			double winProb = 1.0*p.Wins/numGames;
			double winError = Math.sqrt(winProb * (1-winProb)/numGames);
			System.out.printf(" (%2.3f%% " + (char)177 + " %2.3f%%)\n", 100.0*winProb, 100.0*winError);
			//System.out.print(countWins[i]);
			//if(i != Players.size()-1) System.out.print("-");
		}
	}
	public static void SimulateMany(List<Player> Players, int numDice, int numGames) {
		int[] countWins = new int[Players.size()];
		for(int i = 1; i <= numGames; i++) {	
			Collections.shuffle(Players);
			Game newGame = new Game(Players, numDice);
			//newGame.hasPerson = true;
			countWins[newGame.doGame()]++;
			if(i % 1000 == 0) {
				System.out.println(i + " games completed.");
				if(i % 10000 == 0) {
					System.out.println("Current record: ");
					for(int j = 0; j < Players.size(); j++) {
						Player p = Players.get(j);
						System.out.println(p.name + " won: " + p.Wins);
						//System.out.print(countWins[i]);
						//if(i != Players.size()-1) System.out.print("-");
					}
				}
			}
		}
		System.out.println();
		System.out.println("Results");
		for(int i = 0; i < Players.size(); i++) {
			Player p = Players.get(i);
			System.out.print(p.name + " won: " + p.Wins);
			double winProb = 1.0*p.Wins/numGames;
			double winError = Math.sqrt(winProb * (1-winProb)/numGames);
			System.out.printf(" (%2.3f%% " + (char)177 + " %2.3f%%)\n", 100.0*winProb, 100.0*winError);
			//System.out.print(countWins[i]);
			//if(i != Players.size()-1) System.out.print("-");
		}
	}
	public static void SimulateOne(List<Player> Players, int numDice) {
		Game newGame = new Game(Players, numDice);
		newGame.hasPerson = true;
		newGame.doGame();
	}
	
	public static void printProbTable(int max) {
		System.out.print("   ");
		for(int j = 0; j <= max; j++) {
			System.out.printf("%2d      ", j);
		}
		System.out.println();
		for(int i = 0; i <= max; i++) {
			System.out.printf("%2d ", i);
			for(int j = 0; j <= max; j++) {
				System.out.printf("%1.5f ", Utilities.ProbOfAtLeast[i][j]);
			}
			System.out.println();
		}
		
	}

}
