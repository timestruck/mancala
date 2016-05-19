package kalah;

import game.Game;
import game.Move;
import game.Player;
import game.State;

import java.util.Map;

public class KalahGame implements Game {
  
  private KalahState currentState;
  
  public KalahGame() {
	  /* note: we may want to update this behavior later on (i.e. include some
	   * code that allows you to choose how many seeds go in each house) */
	  currentState = KalahState.initialState(4);
  }
  
  /* Implementation of the Game interace *************************************/
  
  @Override
  public State currentState() {return this.currentState;}
  
  @Override
  public void applyMove(Move m) throws IllegalArgumentException {
    Map<Move,State> succs = currentState.successors();
    
    if (succs.keySet().contains(m)) {
      this.currentState = (KalahState) succs.get(m);
    } else {
      String msg;
      try {
        msg = "received this move: " + ((KalahMove) m).getHouseNumber()
            + " but only these were available: ";
        for (Move mv : succs.keySet()){
          msg = msg + ((KalahMove) mv).getHouseNumber() + ", ";
        }
      } catch (Exception dontCare) {
        msg = "the provided move isn't legal (are you sure you supplied"
            + " a KalahMove?)";
      } 
      
      throw new IllegalArgumentException(msg);
    }
  }
  
  //hmmm...
  public KalahState currentKalahState() {return this.currentState;}

  /* Additional utility functions ********************************************/
  public void printKalahGameInfo(){
    System.out.println("========");
    System.out.println("active player: " + this.currentState().getActivePlayer());
    for (int i = 1; i <= 6; i++){
      System.out.println(((KalahState) this.currentState()).getHouseCount(Player.ONE, i));
    }
    System.out.println("player 1 kalah: " + ((KalahState) this.currentState()).kalahCount(Player.ONE));
    for (int i = 1; i <= 6; i++){
      System.out.println(((KalahState) this.currentState()).getHouseCount(Player.TWO, i));
    }
    System.out.println("player 2 kalah: " + ((KalahState) this.currentState()).kalahCount(Player.TWO));
    System.out.println("========");
  }
	
  



}
