package kalah;

import game.Player;

public class EvalFunction {
	// constructor
	//public static KalahState m = (KalahState) KalahGUI.kg.currentState();
	
	public EvalFunction() {
		
	}
	
	// how to evaluate. x = current player, y = opponent, k(x) = number of stones in x's kalah, 
	// h(x) = total stones in x's houses, b(x) = any bonuses for x (+1) **
	// **ex having perfect number of stones in a house to land in x's kalah, 
	// **ex having perfect number of stones in a house to land in an empty x house
	// f(x) = [k(x) - k(y)] + [h(x) - h(y)] + b(x)
	
	// from player one's point of view, negate for player two
//	public static int currentEval() {
//		if (KalahGUI.start1) {
//		int k = m.kalahCount(KalahGUI.p1) - m.kalahCount(KalahGUI.p2);
//		int h = 0;
//		// TODO implement bonuses
//		int b = 0;
//		int c = 0;
//		while (c<6) {
//			if ((6-c) == m.getHouseCount(KalahGUI.p1, c+1)) {
//				b = b + 1;
//			}
//			if ((6-c) == m.getHouseCount(KalahGUI.p2,  c+1)){
//				b = b - 1;
//			}
//			c++;
//		}
//		int i = 0;
//		while (i<6) {
//			h = h + m.getHouseCount(KalahGUI.p1, i+1) - m.getHouseCount(KalahGUI.p2, i+1);
//			i++;
//		}
//		
//		int result = k + h + b;
//		
//		System.out.println("State of Board " + result);
//		
//		return result; } else {return 0;}
//	}
	
}
