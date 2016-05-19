package game;

import java.util.Map;

public class Minimax {
  /* returns the move that the active player should make based of a minimax
   * analysis [depth] levels deep 
   * 
   * fails if [depth] <= 0 or if the game's current state is a terminal one*/
  
  /* idea: at some point, I should consider building a tree as I search the 
   * space, and mark up the tree and re-order the nodes so that I can avoid
   * duplicated work and try to make alpha-beta pruning more effective. */
  
  public Move minimax(State state, int depth){
    if (depth <= 0) {
      String msg = "minimax should be called with a positive depth";
      throw new IllegalArgumentException(msg);
    } else if (state.isTerminal()){
      String msg = "minimax should be called on non-terminal states";
      throw new IllegalArgumentException(msg);
    } else if (state.successors().isEmpty()){
      String msg = "the supplied game's current state has no successors";
      throw new IllegalArgumentException(msg);
    } else if (depth == 1) {
      Player activePlayer = state.getActivePlayer();
      Map<Move, State> succs = state.successors();
      Move result = succs.keySet().iterator().next();
      
      for (Move move : succs.keySet()) {
        int moveUtil = succs.get(move).utility(activePlayer);
        int resultUtil = succs.get(result).utility(activePlayer);
        
        if (moveUtil > resultUtil) {
          result = move;
        }
        
      }
      
      return result;
    } else return null; } }
//    } else {
      /* since none of the other if blocks were triggered, depth >= 2 and the
       * current state has successors */
//      Player activePlayer = state.getActivePlayer();
//      Map<Move, State> succs = state. successors();
//      Move result = succs.keySet().iterator().next();
      
//      for (Move move : succs.KeySet()) {
//        Move nextMove = minimax(succs.get(move), depth -1);
//        int nextMoveUtil = succs.get(nextMove).utility(activePlayer);
//        
//        
//        
//        Move naldkm
//        int resultUtil = succs.get(result).utility(activePlayer);
//        
//        if (move))
//        
//        
//        minimax()
//      }
//    }
      
//    }
  
//  function minimax(node, depth, maximizingPlayer)
//  if depth = 0 or node is a terminal node
//      return the heuristic value of node
//  if maximizingPlayer
//      bestValue := -∞
//      for each child of node
//          val := minimax(child, depth - 1, FALSE)
//          bestValue := max(bestValue, val)
//      return bestValue
//  else
//      bestValue := +∞
//      for each child of node
//          val := minimax(child, depth - 1, TRUE)
//          bestValue := min(bestValue, val)
//      return bestValue

//(* Initial call for maximizing player *)
//minimax(origin, depth, TRUE)
  

  /* TODO consider which methods we should actually include in the interface */

//}
