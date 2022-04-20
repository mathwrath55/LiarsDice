#README File for Liars' Dice
-Package main includes classes usable by entire project
	-Main.java includes the main() method to run the program
	-Game.java includes code to run a game
	-Utilities.java includes several useful methods for running a bot and running the game
	-CheatUtilities.java includes any methods needed for the game which require access to all dice
		-If writing a bot, no method in CheatUtilities is allowed.
-Package players includes all players
	-Player.java is a superclass containing useful values for all people and bots
		-This includes the dice themselves
		-I do not know how to restrict access to the Dice variable to just the local object.
			-Game.java also needs access to the dice
		-DO NOT USE Player.Dice for another bot!  That would be cheating!
			-Bots are obviously allowed to look at their own dice, List<Integer> Dice
	-Person.java implements a human player
	-Bot.java is a superclass for all bots.
	-All other classes define behavior for one bot.
	
Note that one bot is not strictly better than another:
-TrustBreakerBot > TrustBot 1 million/1 million times
-TrustBot > NaiveBot ~81% of the time
-NaiveBot > TrustBreakerBot ~99.8% of the time