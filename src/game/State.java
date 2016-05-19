package game;

import java.util.Map;


public interface State {
  
  //indicates which player is currently making a move
  public Player getActivePlayer();
  
  //returns a map of all legal moves and their corresponding next state
  public Map<Move, State> successors();
  
  //returns true if someone has won the game (and false otherwise)
  public boolean isTerminal();
  
  /* returns the "goodness" of a state from a particular player's perspective; 
   * the value should be positive if the game state favors [p], negative if it 
   * favors the other player, and 0 otherwise. */
      
  public int utility(Player p);

}
