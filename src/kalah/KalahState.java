package kalah;

import java.util.*;

import game.*;


public class KalahState implements State {

  //The typical number of stones found in each pit at the start of a kalah game
  private static final int DEFAULT_STONE_COUNT = 4;

  private Player activePlayer;

  /*represents the pits on a kalah board--pit[0] is the kalah on the
    left-hand-side of the board, pits[1..6] are player 1's houses 1..6, pit[7]
    is the the large kalah on the right-hand-side of the board (i.e. player 1's
    kalah, and pits[8..13] are player 2's houses 1..6. As a side note, the
    number of stones in a pit can never be negative, and the total number of
    stones in the pits must always equal the number of starting stones */
  private int[] pits;

/* Constructors ***************************************************************/

  public KalahState(int[] pits, Player p) {
    this.activePlayer = p;
    this.pits = pits;
  }

/* Factory Methods *******************************************************1*****/

  public static KalahState initialState() {
    return initialState(DEFAULT_STONE_COUNT);
  }

  public static KalahState initialState(int stonesPerPit){
    int[] pits = new int[14];
    //note: no need to set the kalahs to 0--they'll be set to 0 by default
    Arrays.fill(pits, 1, 7, stonesPerPit);
    Arrays.fill(pits, 8, 14, stonesPerPit);

    return new KalahState(pits, Player.ONE);
  }

/* Private Utility Functions *************************************************/


  /*checks whether all of the pits between [start], inclusive, and [stop],
    exclusive do not contain stones */
  private boolean pitsInRangeEmpty(int start, int stop) {
    boolean result = true;
    for (int i = start; i < stop; i++){
      if (pits[i] != 0 ) {result = false;}
    }
    return result;
  }

  //checks whether all of a player's houses no longer contains stones
  private boolean housesEmpty(Player p) {
    if (p == Player.ONE) {
      return pitsInRangeEmpty(1, 7);
    } else {
      return pitsInRangeEmpty(8,14);
    }
  }

  /* returns the numbers associated with pits in the range [start], inclusive, to 
     [stop], exclusive that contain stones */
  private Set<Integer> stockedPits(int start, int stop) {
    HashSet<Integer> pitNums = new HashSet<Integer>();

    for (int i = start; i < stop; i++){
      if (pits[i] > 0) {
        pitNums.add(i);
      }
    }

    return pitNums;
  }
  
  /* returns a set of moves that the active player could legally make*/
  private Set<KalahMove> validMoves(){
    HashSet<KalahMove> result = new HashSet<KalahMove>();
    
    if (activePlayer == Player.ONE) {
      for (int i : stockedPits(1, 7)){
        result.add(KalahMove.ofInt(i));
      }
    } else {
      for (int i : stockedPits(8, 14)) {
        result.add(KalahMove.ofInt(i - 7));
      }
    }
    
    return result;
  }
  
  /* returns the index of the pit opposite the provided index [n] */
  private int opposingPitNum(int n) throws IllegalArgumentException {
    if (n <= 0 || n == 7 || n > 13 ){
      String msg = "opposingPitCountNum expects an integer argument corresponding "
                 + "to a house, but received " + n;
      throw new IllegalArgumentException(msg);
    } else {
      /* oopsie, this is kind of difficult to understand--maybe this diagram of
       * a kalah board with the pits numbered according to our indexing scheme
       * will help make sense of it:
       * 
       *    | 13 12 11 10 09 08 |
       * 00 |                   | 07
       *    | 01 02 03 04 05 06 | 
       * 
       * try finding the pit opposite of 2, and then try finding the one 
       * opposite of 13.
       * */
      int diff = n - 7;
      return 7 - diff;
    }
  }
  
  /* given the index of the last seed sown and the active player, checks whether
   * a capture should occur  */
  private boolean captureTriggered(int[] board, int lastSeedSownLoc, Player activePlayer){
    int activePlayerKalahNum = (activePlayer == Player.ONE) ? 7 : 0;
    
    boolean InEmptyPit = board[lastSeedSownLoc] == 1;
    boolean NotInOwnKalah = lastSeedSownLoc != activePlayerKalahNum;
    
    boolean NotInOpposingKalah = 
      (activePlayer == Player.ONE && lastSeedSownLoc >= 1 && lastSeedSownLoc <= 6)
      || (activePlayer == Player.TWO && lastSeedSownLoc >= 8 && lastSeedSownLoc <= 13);

    boolean opposingKalahHasSeeds = NotInOwnKalah && board[opposingPitNum(lastSeedSownLoc)] != 0;
    
    //System.out.println(InEmptyPit);
    //System.out.println(NotInOwnKalah);
    //System.out.println(NotInOpposingKalah);
    //System.out.println(opposingKalahHasSeeds);
    
    return InEmptyPit && NotInOwnKalah && NotInOpposingKalah && opposingKalahHasSeeds;
  }
  
  private KalahState sow(KalahMove m){
    int houseNum = m.getHouseNumber();
    int startingPitNum = 
      (activePlayer == Player.ONE) ? houseNum : 7 + houseNum;
    int currentPitNum = startingPitNum;
    int stones = pits[startingPitNum];
    
    /* fresh variable since I'd like to avoiding manipulating the current 
     * state: */
    /* oh wow--I made a really bad error eariler. I mistakenly thought that
     * doing int[] pits_copy = this.pits would have created a new array that
     * was a copy of the old one. Instead, it looks like I ended up having 
     * pits_copy point to the original pits... woo Java.*/
    int[] pits_copy = Arrays.copyOf(this.pits, this.pits.length);
    
    int playerKalahNum = (activePlayer == Player.ONE) ? 7 : 0;
    int opponentKalahNum = (activePlayer == Player.ONE) ? 0 : 7;
    Player nextActivePlayer;
    
    /* remove the seeds from the appropriate pit */
    pits_copy[startingPitNum] = 0;
    
    //System.out.println(">>=== Begining to sow seeds ===<<");
    
    //System.out.println(pits_copy);
    //System.out.println(currentPitNum);
    //System.out.println(stones);
    
    /* sow those seeds across the board */
    while (stones > 0){
      
      if (currentPitNum == 13){
        currentPitNum = 0;
      } else {
        currentPitNum++;
      }
      if (currentPitNum != opponentKalahNum){
        pits_copy[currentPitNum]++;
        stones--;
      }
      
      //System.out.println(pits_copy);
      //System.out.println(currentPitNum);
      //System.out.println(stones);
    }
    
    //System.out.println(">>=== No longer sowing seeds ===<<");
    
    /* rule for capturing opposing kalah's stones */
    
    if(captureTriggered(pits_copy, currentPitNum, activePlayer)) {
      /* move the last seed sown, and the seeds in the opposing house to 
       * current player's Kalah */
      
      pits_copy[currentPitNum] = 0;
  
      int opposingPitSeeds = pits_copy[opposingPitNum(currentPitNum)];
      pits_copy[opposingPitNum(currentPitNum)] = 0;
      
      pits_copy[playerKalahNum] = pits_copy[playerKalahNum] + 1 + opposingPitSeeds;
      
    }
    
    nextActivePlayer = 
        (currentPitNum == playerKalahNum) ? activePlayer : activePlayer.next();
    
    return new KalahState(pits_copy, nextActivePlayer);
    
  }
  


/* Implementation of State Interface *****************************************/

  @Override
  public Player getActivePlayer() {
    return this.activePlayer;
  }

  @Override
  public Map<Move, State> successors() {
    Hashtable<Move, State> result = new Hashtable<Move, State>();
    Set<KalahMove> validMoves = this.validMoves();
    
    for (KalahMove move : validMoves){
      result.put(move, this.sow(move));
    }
    return result;
  }


  @Override
  public boolean isTerminal() {
    return housesEmpty(activePlayer);
  }

  @Override
  public int utility(Player p) {
    return this.kalahCount(p) - this.kalahCount(p.next());
    //return simpleHeuristic(KalahMove.ofInt(6), this, p) - simpleHeuristic(KalahMove.ofInt(6), this, p.next());
  }
  
/* Getters *******************************************************************/
  
  /* returns the number of seeds in a given player's kalah */
  public int kalahCount(Player p){
    if (p == Player.ONE){
      return pits[7];
    } else {
      return pits[0];
    }
  }
 
  public int getHouseCount(Player p, int num) {
    if (p == Player.ONE){
      return pits[num];
    } else {
      return pits[7 + num];
    }
  }

/* Other useful things *******************************************************/
  public void report(){
    int p1Score = pits[1] + pits[2] + pits[3] + pits[4] + pits[5] + pits[6] + pits[7];
    int p2Score = pits[8] + pits[9] + pits[10] + pits[11] + pits[12] + pits[13] + pits[0];
    System.out.println("P1: " + p1Score + " | P2: " + p2Score);
  }
  
  /* returns an integer representing the approximate "goodness" of a move*/
  public static int simpleHeuristic(KalahMove move, 
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
  
}
