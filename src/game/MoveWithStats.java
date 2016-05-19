package game;

public class MoveWithStats {
  
  /* fields ******************************************************************/
  
  /* indicates how many future turns the bot considered. For example, many bots
   * only look 1 turn ahead, so those bots would return a maxDepth of 1 */
  public final int maxDepth;
  
  /* */
  public final Move move;
  
  /* the anticipated "goodness" of the move according to the bot */
  public final int utility;
  
  /* constructor *************************************************************/
  public MoveWithStats(int maxDepth, Move move, int utility){
    this.maxDepth = maxDepth;
    this.move = move;
    this.utility = utility;
  }

}
