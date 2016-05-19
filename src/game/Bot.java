package game;

public interface Bot {
  
  /* Returns a suggested move for the active player in the provided state;
   * Throws an IllegalArgumentException if there are no valid moves from [s] */
  public Move requestMove(State s) throws IllegalArgumentException;
  
}
