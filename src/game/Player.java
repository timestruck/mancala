package game;

public enum Player {
  ONE, 
  TWO;
  
  public Player next(){
    return (this == ONE) ? TWO : ONE;
  }
  
}
