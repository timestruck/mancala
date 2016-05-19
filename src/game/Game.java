package game;

public interface Game {
  
  //returns the current state
  public State currentState();
  
  //applies a move to the current state
  public void applyMove(Move m) throws IllegalArgumentException;
 
}
