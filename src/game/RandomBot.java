package game;

import java.util.Random;
import java.util.Set;

public class RandomBot implements Bot {

  public Move requestMove(State s) {
    Random random = new Random();
    Set<Move> keys = s.successors().keySet();
    return keys.toArray(new Move[0])[random.nextInt(keys.size())];
  }

}
