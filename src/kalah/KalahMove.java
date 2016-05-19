package kalah;

import game.Move;

/* represents the number of the pit from which the player begins sowing seeds */

/* note: it may seem silly to have KalahMove be an enum when it could easily be
 * a class with a single private int field, but I ran into some issues with 
 * equality checking in KalahGame (specifically, the applyMove(...) method 
 * generates a set of valid moves for the current state, and checks whether the
 * supplied move is one of those moves using contains(...) and consequently 
 * equals(...)). 
 * 
 *  I'm not sure exactly what went wrong when I tried to over-write equality 
 *  for the more natural, single-field class. I'm guessing it had to do with
 *  the fact that applyMove(...) expects a Move argument--not a kalah move--and
 *  therefore used the default equality comparison, pointer equality. I 
 *  then tried switching to an enum class (which is a reasonably painless way 
 *  to ensure that there is exactly one object for each type of move, and in
 *  turn means that whenever you see FOUR, its a reference to the same object).
 *  Things worked a lot more smoothly, though I'm not sure if the problem was
 *  triggered because the move was cast to Move--in which case, I could have 
 *  done a downcast to KalahMove and fixed the issue--or if the problem was 
 *  more nuanced. */
public enum KalahMove implements Move{
  ONE, TWO, THREE, FOUR, FIVE, SIX;

  public static KalahMove ofInt(int n){
    switch (n) {
    case 1: return ONE;
    case 2: return TWO;
    case 3: return THREE;
    case 4: return FOUR;
    case 5: return FIVE;
    case 6: return SIX;
    default:
      String msg = "KalahMove.ofInt(int n) received " + n 
                 + " but expected a number between 1 and 6, inclusive";
      throw new IllegalArgumentException(msg);
      
    }

  }

  /* returns which house the player will use when starting to sow seeds (from 
   * the player's perspective, house 1 will be the left-most pit, and house 6
   * will be  the right-most pit)*/
  public int getHouseNumber() {
    switch (this) {
    case ONE: return 1;
    case TWO: return 2;
    case THREE: return 3;
    case FOUR: return 4;
    case FIVE: return 5;
    case SIX: return 6;
    default:
      String msg = "unreachable case (KalahMove.getHouseNumber())";
      throw new IllegalArgumentException(msg);
    }

  }
  


}
