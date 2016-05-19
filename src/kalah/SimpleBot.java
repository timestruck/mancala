package kalah;

import java.util.Map.Entry;
import java.util.Random;

import game.Move;
import game.Player;
import game.State;

public class SimpleBot implements game.Bot {
  
  /* returns an integer representing the approximate "goodness" of a move*/
  private int simpleHeuristic(KalahMove move, 
                              KalahState nextState, 
                              Player botPlayer){
    /* weigh moves that increase your score heavily--if there's a way to capture 
     * an oponnent's stones or to score a point, this bot will seize that 
     * opportunity*/
    int scoreValue = nextState.kalahCount(botPlayer) * 1000;
    
    /* in the event of a tie between moves that could increase a player's score,
     * choose a move that grant's them a second turn. Notice that whenever  a 
     * player takes a move that grants a second turn, their score increases
     * too. Consequently, this rule and the previous one are designed to prefer
     * captures over moves that grant a second turn, and to prefer moves that
     * grant a second turn whenever captures aren't present */
    int secondTurnValue = (nextState.getActivePlayer() ==  botPlayer) ? 100 : 0;
    
    /* to help break close ties, we'll favor moves that either clear out a
     * player's right-most home, or maintain a higher level of free pits
     * (which, in turn makes it easier to get a capture later on the game). 
     * As a side note, these two heuristics aren't totally orthogonal, so 
     * we've given them significantly less weight than the first two, and we're
     * going to consider an alternate set of heuristics for breaking ties*/
    int distanceToRightValue = move.getHouseNumber();
    int emptyPitsValue = 0;
    
    for (int houseNum = 1; houseNum <= 6; houseNum++){
      if (nextState.getHouseCount(botPlayer, houseNum) == 0){
        emptyPitsValue++;
      }
    }
    
    /* following Professor Selman's recommendation, we've decided to add some
     * randomness to our bot's decision making process--that way, if too bots
     * play against one another repeatedly, you'll get slightly different 
     * results, and you'll be able to get a better sense for which bot is best
     * (in contrast to wondering whether one bot did better simply by a quirk
     * in the way each bot made decisions) */
    Random random = new Random();
    
    /* only involve a small amount of randomness--the scoreValue and 
     * secondTurnValue define the core of this bot's strategy and I think
     * it makes sense not to override that behavior. I'll only contest the 
     * choices introduces by the distanceToRightValue and emptyPitsValue, since 
     * those are somewhat arbitrary and kind of intertwined; as a side note, the
     * standard deviation for the noise
     * is 2, which I think is appropriately small given how heavy the tails
     * of the distribution are. */
    int noise = random.nextInt(7);
    
    int returnVal = scoreValue + secondTurnValue + distanceToRightValue
                  + emptyPitsValue + noise;
    
    return returnVal;
    
  }

  @Override
  public Move requestMove(State inputState) throws IllegalArgumentException{
    
    Move currentResultMove = null;
    int currentResultValue = Integer.MIN_VALUE;
    Player botPlayer = inputState.getActivePlayer();
    
    /* find the best move according to our simple heuristic, and return it */
    
    for (Entry<Move, State> e : inputState.successors().entrySet()) {
      KalahMove move = (KalahMove) e.getKey();
      KalahState state = (KalahState) e.getValue();
      int candidateMoveValue = simpleHeuristic (move, state, botPlayer);
      //int candidateMoveValue = state.utility(botPlayer);
      
      //System.out.println(move.getHouseNumber());
      //System.out.println(candidateMoveValue);
      
      if (candidateMoveValue >= currentResultValue) {
        currentResultMove = (Move) move;
        currentResultValue = candidateMoveValue;
      }
    }
    
    if (currentResultMove != null) {
      //System.out.println("> SimpleBot chose: " + ((KalahMove) currentResultMove).getHouseNumber());
      return currentResultMove;
    } else {
      String msg = "No legal moves (you may have provided a terminal state to "
                 + "this bot)";
      throw new IllegalArgumentException(msg);
    }
    
  }

}
