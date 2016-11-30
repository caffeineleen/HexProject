package edu.up.cs301.hex;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * this is the primary activity for Counter game
 * 
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */
public class HexMainActivity extends GameMainActivity {
	
	public static final int PORT_NUMBER = 5213;

	/**
	 * a hex game is for two players. The default is human vs. computer
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		
		// red human GUI
		playerTypes.add(new GamePlayerType("Local Human Player (red player)") {
			public GamePlayer createPlayer(String name) {
				return new HexHumanPlayer1(name, R.layout.hex_human_player1);
			}
		});

		// game of 33
		playerTypes.add(new GamePlayerType("Local Human Player (game of 33)") {
			public GamePlayer createPlayer(String name) {
				return new HexHumanPlayer2(name);
			}
		});

		// dumb computer player
		playerTypes.add(new GamePlayerType("Computer Player (dumb blue player)") {
			public GamePlayer createPlayer(String name) {
				return new HexComputerPlayer1(name);
			}
		});
		
		// smarter computer player
		playerTypes.add(new GamePlayerType("Computer Player (smart blue player)") {
			public GamePlayer createPlayer(String name) {
				return new HexComputerPlayer2(name);
			}
		});

		// Create a game configuration class for Hex
		GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Hex", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
		defaultConfig.addPlayer("Computer", 3); // dumb computer player

		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI
		
		//done!
		return defaultConfig;
		
	}//createDefaultConfig


	/**
	 * createLocalGame
	 * 
	 * Creates a new game that runs on the server tablet,
	 * 
	 * @return a new, game-specific instance of a sub-class of the LocalGame
	 *         class.
	 */
	@Override
	public LocalGame createLocalGame() {
		return new HexLocalGame();
	}

}