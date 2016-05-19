package kalah;

import game.Bot;
import game.Player;
import game.RandomBot;
import game.State;
import game.minimax.MMBot;

import java.util.Arrays;


public class Main {

  public static void main(String[] args) {
    
    KalahGame kg = new KalahGame();
    Bot p1 = new SimpleBot();
    Bot p2 = new SimpleBot();
    
    //kg.applyMove(p1.requestMove(kg.currentState()));
    
    while (!kg.currentKalahState().isTerminal()){
      kg.printKalahGameInfo();
      State currentState = kg.currentState();
      if (currentState.getActivePlayer() == Player.ONE){
        //System.out.println(((KalahMove) p1.requestMove(currentState)).getHouseNumber());
        kg.applyMove(p1.requestMove(currentState));
      } else{
        kg.applyMove(p2.requestMove(currentState));
      }
    }

    
    kg.printKalahGameInfo();
    kg.currentKalahState().report();
    
    //kg.printKalahGameInfo();
    //kg.applyMove(KalahMove.ofInt(6));
    //kg.printKalahGameInfo();

	  KalahGUI mankalah = new KalahGUI();
      mankalah.setVisible(true);
      int[] xs2 = new int[14];
      Arrays.fill(xs2, 1, 7, 4);
      Arrays.fill(xs2, 8, 14, 4);
      
      //for (int x: xs2) System.out.println(x);
      
      
  }

}
