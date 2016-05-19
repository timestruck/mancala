package game.minimax;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Future;

import kalah.KalahMove;
import game.Bot;
import game.Move;
import game.Player;
import game.State;

public class MMBot implements Bot {
  
  /* fields ******************************************************************/
  
  /* indicates whether or not the bot uses alpha-beta pruning */
  boolean alphaBeta;
  
  /* represents how many moves ahead the bot thinks (assuming that the bot 
   * isn't timed) */
  int depth;
  
  /* indicates how many milliseconds the bot will think (if the bot is timed) */
  int time;
  
  /* indicates if the bot is timed */
  boolean timed;
  
/* constructors ************************************************************/
  
  private MMBot(boolean alphaBeta, int depth, int time, boolean timed){
    this.alphaBeta = alphaBeta;
    this.depth = depth;
    this.time = time;
    this.timed = timed;
  }
  
  /* factory methods *********************************************************/
  
  /* notice that both of these methods take in an int and a boolean--if we were
   * to use constructors instead of factory methods, Java would think we had
   * duplicated the constructor unless we had one constructor take an int first
   * and the other take a boolean first, which would make the interface 
   * inconsistent. Instead, we've decided to implement factory methods that 
   * have a consistent interface (and also are slightly more readable) */
  
  /* produces a bot that will go down to the specified depth, possibly using 
   * alpha-beta pruning to speed up the analysis */
  public static MMBot untimed(int depth, boolean alphaBeta){
    if (depth <= 0){
      String msg = "untimed MMBots expect a depth argument greater than 0, but "
                 + "received a depth of " + depth;
      throw new IllegalArgumentException(msg);
    }
    return new MMBot(alphaBeta, depth, 0, false);
  }
  
  /* returns a bot that runs for the specified time in ms--possibly using 
   * alpha-beta pruning and then returns a move. Out of necessity the bot uses
   * iterative deepening to ensure that it can always produce a move */
  public static MMBot timed(int time, boolean alphaBeta){
    if (time <= 0){
      String msg = "timed MMBots expect a time argument greater than 0, but "
                 + "received a time of " + time;
      throw new IllegalArgumentException(msg);
    }
    return new MMBot(alphaBeta, -1, time, true); 
  }
  
  /* private utility functions ***********************************************/
  
  private MMIntermediate fixedDepthMM(State state, int depth, int alpha, int beta){
    if (depth <= 0) {
      String msg = "minimax should be called with a positive depth";
      throw new IllegalArgumentException(msg);
    } else if (state.isTerminal()){
      String msg = "minimax should be called on non-terminal states";
      throw new IllegalArgumentException(msg);
      //return state.utility(state.getActivePlayer());
    } else if (state.successors().isEmpty()){
      String msg = "the supplied game's current state has no successors";
      throw new IllegalArgumentException(msg);
    } else if (depth == 1) {
      /* look for ways to tidy this up: */
      Player activePlayer = state.getActivePlayer();
      Map<Move, State> succs = state.successors();
      Move result = succs.keySet().iterator().next();
      int resultUtil = succs.get(result).utility(activePlayer);
      
      for (Move move : succs.keySet()) {
        int moveUtil = succs.get(move).utility(activePlayer);
        
        if (moveUtil > resultUtil) {
          result = move;
          resultUtil = moveUtil;
        }
        
      }
      
      return new MMIntermediate(result, resultUtil);
    } else {
      // TODO consider re-factoring code
      
      
      /* since none of the other if blocks were triggered, depth >= 2 and the
       * current state has successors */
      Player activePlayer = state.getActivePlayer();
      Map<Move, State> succs = state. successors();
      Move result = succs.keySet().iterator().next();
      
      /* clean up:*/
      int resultUtil;
      try {
        if (succs.get(result).getActivePlayer() == activePlayer) {
          resultUtil = fixedDepthMM(succs.get(result), depth - 1, alpha, beta).utility;
        } else {
          resultUtil = -fixedDepthMM(succs.get(result), depth - 1, alpha, beta).utility;
  
        }
      } catch (Exception dontCare) {
        resultUtil = succs.get(result).utility(activePlayer);
      }
      
      int newAlpha = alpha;
      int newBeta = beta;

      for (Entry<Move, State> e : succs.entrySet()){
        //System.out.println(((KalahMove) e.getKey()).getHouseNumber());
        /* if the player didn't change between turns */
        if (e.getValue().getActivePlayer() == activePlayer){
          try {
            MMIntermediate inter = fixedDepthMM(e.getValue(), depth - 1, newAlpha, newBeta);
            int moveUtil = inter.utility;
            newAlpha = Integer.max(newAlpha, moveUtil);
            
            if (moveUtil > resultUtil){
              //result = inter.move;
              result = e.getKey();
              resultUtil = moveUtil;
            }
          } catch (Exception dontCare) {
            int moveUtil = e.getValue().utility(activePlayer);
            newAlpha = Integer.max(newAlpha, moveUtil);
            
            if (moveUtil > resultUtil){
              result = e.getKey();
              resultUtil = moveUtil;
            }
            
          }
          
        } else {
          try {
            MMIntermediate inter = fixedDepthMM(e.getValue(), depth - 1, newBeta, newAlpha);
            int moveUtil = -inter.utility;
            newBeta = Integer.min(newBeta, moveUtil);
          
            if (moveUtil > resultUtil){
              //result = inter.move;
              result = e.getKey();
              resultUtil = moveUtil;
            }
          } catch (Exception dontCare){
            int moveUtil = e.getValue().utility(activePlayer);
            newBeta = Integer.min(newBeta, moveUtil);
            
            if (moveUtil > resultUtil){
              result = e.getKey();
              resultUtil = moveUtil;
            }
            
          }
          
        }
        
        if (alphaBeta && newBeta <= newAlpha){
          break;
        }
      }

      /* this seems a little silly--I should probably just keep a copy of the
       * best MMIntermediate I generate and just return that */
      return new MMIntermediate(result, resultUtil);
      
    }
  }
  
  /* Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT */
  private long getCurrentTime(){
    return (new Date()).getTime();
  }
  
  private Move timedMM(State state, int time) {
    long startTime = getCurrentTime();
    Iterator<Move> moveIter = state.successors().keySet().iterator();
    Move secondLastGeneratedMove = moveIter.next();
    try {
      Move lastGeneratedMove = moveIter.next();
      int targetDepth = 1;
      
      /* for simplicity's sake, I'm going to let the bot take slightly longer than
       * its alloted time, and then return the result that a more strictly timed 
       * bot would have returned. In the future, we should consider using some
       * concurrency magic to kill the process at exactly the right time. */
      while ((getCurrentTime() - startTime) <= time){
        secondLastGeneratedMove = lastGeneratedMove;
        MMIntermediate inter = 
            fixedDepthMM(state, targetDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //System.out.println(inter.utility);
        lastGeneratedMove = (Move) inter.move;
        targetDepth++;
      }
      
      System.out.println("depth reached: " + targetDepth);
      
    } catch (Exception dontCare) {}
    
    Random random = new Random();
    if (random.nextFloat() < .03) {
      return (Move) state.successors().keySet().toArray()[random.nextInt(state.successors().keySet().size())];
    } else {
      return secondLastGeneratedMove;
    }
  }
    @Override
  public Move requestMove(State s) throws IllegalArgumentException {
    if (timed) {
      return timedMM(s, time);
    } else {
      return fixedDepthMM(s, depth, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
    }
  }


}
