package main;
import java.util.*;

import players.Player;
public class Utilities {
	public static Random rnd = new Random();
	//first index is total number of dice on table, second is number we wish to see
	public static double[][] ProbOfExact = generateProbOfExact();
	public static double[][] ProbOfAtLeast = generateProbOfAtLeast();
	public static int Roll1() {
		return rnd.nextInt(6)+1;
	}
	public static double[][] generateProbOfExact() {
		double[][] data = new double[51][51];
		data[0][0] = 1;
		for(int i = 1; i <= 50; i++) {
			for(int j = 0; j <= i; j++) {
				if(j == 0) {
					data[i][j] = 5.0/6.0 * data[i-1][j];
					continue;
				}
				data[i][j] = (5*data[i-1][j]+data[i-1][j-1])/6.0;
			}
		}
		return data;
	}
	public static double[][] generateProbOfAtLeast() {
		if(ProbOfExact[0][0] != 1) {
			ProbOfExact = generateProbOfExact();
		}
		double[][] data = new double[51][51];
		data[0][0] = 1;
		for(int i = 1; i <= 50; i++) {
			for(int j = 50; j >= 0; j--) {
				if(j == 50) {
					data[i][j] = 0;
					continue;
				}
				data[i][j] = data[i][j+1] + ProbOfExact[i][j];
			}
		}
		return data;
	}
	public static int countDice(List<Player> Players) {
		int count = 0;
		for(Player p : Players) {
			count += p.numDice;
		}
		return count;
	}

	public static boolean ThrowExceptionIfInvalid(int guess, List<Integer> prevGuesses) throws Exception{
		Exception e = new Exception("Invalid guess made by bot!");
		//returns True if the guess is valid, throws exception otherwise.
		//check challenge
		if(guess == 0) {
			//don't allow challenge on first guess
			if(prevGuesses.size() == 0) {
				throw(e);
			}
			return true;
		}
		//don't allow guess of 0 or below dice
		if(guess <= 10) {
			throw(e);
		}
		//don't allow guessing 7s, 8s, 9s, 0s
		if((guess-1)%10 >= 6) {
			throw(e);
		}
		//anything that reaches here is guaranteed to be a non-challenge guess
		//if no previous guesses have been made, it is valid
		if(prevGuesses.size() == 0) return true;
		//if we get here, there has been a previous guess
		int lastGuess = prevGuesses.get(prevGuesses.size()-1);
		//check if new guess is smaller than last guess
		if(guess <= lastGuess) throw(e);
		//if we get here, the guess is valid
		return true;
	}
	public static void checkValid(int guess, List<Integer> prevGuesses) {
		try {
			ThrowExceptionIfInvalid(guess, prevGuesses);
		} catch(Exception e) {
			System.out.println("Invalid guess made.  Program terminated.");
			System.exit(1);
		}
	}
	
}
