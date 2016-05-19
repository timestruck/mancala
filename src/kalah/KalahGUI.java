package kalah;

import game.Move;
import game.Player;
import game.RandomBot;
import game.minimax.MMBot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.util.Arrays;
import java.util.Random;


public class KalahGUI extends JFrame implements MouseListener {
	private int kalahSize = 80;
	private int ballSize = 20;
	private static final long serialVersionUID = 1L;
	public static boolean start1 = false;
	
	
	public static Player p1 = Player.ONE;
	public static Player p2 = Player.TWO;

	private KalahGame kg = new KalahGame();
	private SimpleBot interBot = new SimpleBot();
	private RandomBot randomBot = new RandomBot();
	private MMBot mmBot = MMBot.timed(500, false);
	Random rand = new Random();
	Random randomN = new Random();
	//private KalahState m = (KalahState) kg.currentState();
	// Pits and houses
	Rectangle2D house0 = new Rectangle2D.Double(50, 150, kalahSize, kalahSize*2+20);
	Ellipse2D pit13 = new Ellipse2D.Double(150, 150, kalahSize, kalahSize);
	Ellipse2D pit12 = new Ellipse2D.Double(250, 150, kalahSize, kalahSize);
	Ellipse2D pit11 = new Ellipse2D.Double(350, 150, kalahSize, kalahSize);
	Ellipse2D pit10 = new Ellipse2D.Double(450, 150, kalahSize, kalahSize);
	Ellipse2D pit9 = new Ellipse2D.Double(550, 150, kalahSize, kalahSize);
	Ellipse2D pit8 = new Ellipse2D.Double(650, 150, kalahSize, kalahSize);
	
	Rectangle2D house7 = new Rectangle2D.Double(750, 150, kalahSize, kalahSize*2+20);
	Ellipse2D pit1 = new Ellipse2D.Double(150, 250, kalahSize, kalahSize);
	Ellipse2D pit2 = new Ellipse2D.Double(250, 250, kalahSize, kalahSize);
	Ellipse2D pit3 = new Ellipse2D.Double(350, 250, kalahSize, kalahSize);
	Ellipse2D pit4 = new Ellipse2D.Double(450, 250, kalahSize, kalahSize);
	Ellipse2D pit5 = new Ellipse2D.Double(550, 250, kalahSize, kalahSize);
	Ellipse2D pit6 = new Ellipse2D.Double(650, 250, kalahSize, kalahSize);

	private Ellipse2D[] drawn = new Ellipse2D[14];
	
	// first player
	JPanel panel2 = new JPanel();
	JLabel txt1 = new JLabel("PLAYER 1");
	JRadioButton r1 = new JRadioButton("MiniMax");
	JRadioButton r2 = new JRadioButton("Intermediate");
	JRadioButton r3 = new JRadioButton("Random");
	JRadioButton r4 = new JRadioButton("Human");
	ButtonGroup g = new ButtonGroup();
	// Choose 2ND Player
	JPanel p3 = new JPanel();
	JLabel txt2 = new JLabel("PLAYER 2");
	JRadioButton rr1 = new JRadioButton("MiniMax");
	JRadioButton rr2 = new JRadioButton("Intermediate");
	JRadioButton rr3 = new JRadioButton("Random");
	JRadioButton rr4 = new JRadioButton("Human");
	ButtonGroup g2 = new ButtonGroup();
	
	
	public KalahGUI() {
		super("Kalah");
		setSize(900,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
		//mancalaGame = new KalahState(pits, p1);
		// how do I initialize using Kalah state??
		//Arrays.fill(pits, 1, 7, 4);
	    //Arrays.fill(pits, 8, 14, 4);

	    // add drawn pits to array
	   drawn[1] = pit1;
	   drawn[2] = pit2;
	   drawn[3] = pit3;
	   drawn[4] = pit4;
	   drawn[5] = pit5;
	   drawn[6] = pit6;
	   
	   drawn[8] = pit8;
	   drawn[9] = pit9;
	   drawn[10] = pit10;
	   drawn[11] = pit11;
	   drawn[12] = pit12;
	   drawn[13] = pit13;

		// Choose 1ST Player
		g.add(r1);
		g.add(r2);
		g.add(r3);
		g.add(r4);
		panel2.add(txt1);
		panel2.add(r1);
		panel2.add(r2);
		panel2.add(r3);
		panel2.add(r4);
		
		add(panel2, BorderLayout.LINE_START);
		
		//2nd player
		g2.add(rr1);
		g2.add(rr2);
		g2.add(rr3);
		g2.add(rr4);
		p3.add(txt2);
		p3.add(rr1);
		p3.add(rr2);
		p3.add(rr3);
		p3.add(rr4);
		
		add(p3, BorderLayout.LINE_END);
		
		// Start End game
		JPanel p = new JPanel();
		JButton b1 = new JButton("Start Game");
		JButton b2 = new JButton("Quit");
		//JLabel eval = new JLabel("Eval: " + this.currentEval());
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				if (r1.isSelected()&&rr4.isSelected()) {
					System.out.println("Minimax vs Human");
					start1 = true;
					kg.applyMove(randomBot.requestMove(kg.currentState()));
					repaint();
					// initialize game
				} else if (r4.isSelected()&&rr1.isSelected()) {
					System.out.println("Human vs Minimax");
					start1 = true;
				} else if (r2.isSelected()&&rr4.isSelected()) {
					System.out.println("Intermediate vs Human");
					start1 = true;
					kg.applyMove(interBot.requestMove(kg.currentState()));
					repaint();
					// initialize game
				} else if (r4.isSelected()&&rr2.isSelected()) {
					System.out.println("Human vs Intermediate");
					start1 = true;
				} else if (r3.isSelected()&&rr4.isSelected()) {
					System.out.println("Random vs Human");
					start1 = true;
					kg.applyMove(randomBot.requestMove(kg.currentState()));
					repaint();
					// initialize game
				} else if (r4.isSelected()&&rr3.isSelected()) {
					System.out.println("Human vs Random");
					start1 = true;
				} else if (r4.isSelected()&&rr4.isSelected()) {
					System.out.println("Human vs Human");
					start1 = true;
				} else {
					JOptionPane.showMessageDialog(null, "Please pick your players. Also, GUI does not support two robots.");
				}
				
				
			}
		});
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("end game");	
				// end game
				System.exit(0);
				//JOptionPane.showMessageDialog(null, "You lost?");
			}
		});
		p.add(b1);
		p.add(b2);
		//p.add(eval);
		add(p, BorderLayout.SOUTH);

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	    int i = 0;
	    if ((((KalahState) kg.currentState()).getHouseCount(p1,1)==0) && (((KalahState) kg.currentState()).getHouseCount(p1,2)==0) &&
				(((KalahState) kg.currentState()).getHouseCount(p1,3)==0) && (((KalahState) kg.currentState()).getHouseCount(p1,4)==0) &&
				(((KalahState) kg.currentState()).getHouseCount(p1,5)==0) && (((KalahState) kg.currentState()).getHouseCount(p1,6)==0) && start1) {
			JOptionPane.showMessageDialog(null, "Game over");
		} else if ((((KalahState) kg.currentState()).getHouseCount(p2,1)==0) && (((KalahState) kg.currentState()).getHouseCount(p2,2)==0) &&
				(((KalahState) kg.currentState()).getHouseCount(p2,3)==0) && (((KalahState) kg.currentState()).getHouseCount(p2,4)==0) &&
				(((KalahState) kg.currentState()).getHouseCount(p2,5)==0) && (((KalahState) kg.currentState()).getHouseCount(p2,6)==0) && start1){
			JOptionPane.showMessageDialog(null, "Game over");
		}
		
	    if (r4.isSelected()&&rr4.isSelected()) {
			System.out.println("Human vs Human");
			start1 = true;
			
		    while (i<14) {
				if ((i>0) && (i<7) && (((KalahState) kg.currentState()).getActivePlayer() == p1) ) {
			    	if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
				    	//start1 = false;
			    	  System.out.println("clicked pit " + i);
		    		  kg.applyMove(KalahMove.ofInt(i));
			    	  repaint();
				    }
				} else if  ((i>7) && (i<14) && (((KalahState) kg.currentState()).getActivePlayer() == p2) ) {
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
			    	  System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i-7));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r3.isSelected()&&rr4.isSelected()) {
	    	System.out.println("Random Bot vs Human");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
//			    	  kg.applyMove(randomBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>7) && (i<14) && (((KalahState) kg.currentState()).getActivePlayer() == p2) ) {		
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i-7));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r4.isSelected()&&rr3.isSelected()) {
	    	System.out.println("Human vs Random");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
//			    	  kg.applyMove(randomBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>0) && (i<7) && (((KalahState) kg.currentState()).getActivePlayer() == p1) ) {
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r2.isSelected()&&rr4.isSelected()) {
	    	System.out.println("Intermediate Bot vs Human");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
//			    	  kg.applyMove(interBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>7) && (i<14) && (((KalahState) kg.currentState()).getActivePlayer() == p2) ) {		
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i-7));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r4.isSelected()&&rr2.isSelected()) {
	    	System.out.println("Human vs Random");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
//			    	  kg.applyMove(interBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>0) && (i<7) && (((KalahState) kg.currentState()).getActivePlayer() == p1) ) {
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r1.isSelected()&&rr4.isSelected()) {
	    	System.out.println("Intermediate Bot vs Human");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
//			    	  kg.applyMove(mmBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>7) && (i<14) && (((KalahState) kg.currentState()).getActivePlayer() == p2) ) {		
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i-7));
			    	  repaint();
				    }
				}
				i++;
		   }
	    } else if (r4.isSelected()&&rr1.isSelected()) {
	    	System.out.println("Human vs Random");
			start1 = true;
			while (i<14) {
//				if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
//			    	  kg.applyMove(mmBot.requestMove(kg.currentState()));
//			    	  repaint();
//				} else 
				if ((i>0) && (i<7) && (((KalahState) kg.currentState()).getActivePlayer() == p1) ) {
					if ((e.getButton() == 1) && drawn[i].contains(e.getX(), e.getY()) ) {
						System.out.println("clicked pit " + i);
			    	  kg.applyMove(KalahMove.ofInt(i));
			    	  repaint();
				    }
				}
				i++;
		   }
	    }


	   
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.draw(pit1);
		g2d.draw(pit2);
		g2d.draw(pit3);
		g2d.draw(pit4);
		g2d.draw(pit5);
		g2d.draw(pit6);
		g2d.draw(house7);
		
		g2d.setColor(Color.BLUE);
		g2d.draw(pit8);
		g2d.draw(pit9);
		g2d.draw(pit10);
		g2d.draw(pit11);
		g2d.draw(pit12);
		g2d.draw(pit13);
		g2d.draw(house0);

		// int randomNum = rand.nextInt((max - min) + 1) + min; 56, 206
		
		if (start1) {
			int n = 0;
			int i = 0;
			while (n<14) {

				System.out.println(((KalahState) kg.currentState()).getHouseCount(p1,n));

				while (i<((KalahState) kg.currentState()).getHouseCount(p1,n)) {
					g2d.setColor(randomColor());
					if (n==0) {
						g2d.fillOval(60+randomN.nextInt(40), 170+randomN.nextInt(120), ballSize, ballSize);
						g2d.setColor(Color.BLACK);
						g2d.drawString(""+((KalahState) kg.currentState()).getHouseCount(p1,n), 60, 140);
					} else if (n==7) {
						g2d.fillOval(760+randomN.nextInt(40), 170+randomN.nextInt(120), ballSize, ballSize);
						g2d.setColor(Color.BLACK);
						g2d.drawString(""+((KalahState) kg.currentState()).getHouseCount(p1,n), 760, 140);
					} else if (n>7) {
						g2d.fillOval(160+randomN.nextInt(40)+100*(13-n), 160+randomN.nextInt(40), ballSize, ballSize);
						g2d.setColor(Color.BLACK);
						g2d.drawString(""+((KalahState) kg.currentState()).getHouseCount(p1,n), 160+100*(13-n), 140);
					} else {
						g2d.fillOval(160+randomN.nextInt(40)+100*(n-1), 160+randomN.nextInt(40)+100, ballSize, ballSize);
						g2d.setColor(Color.BLACK);
						g2d.drawString(""+((KalahState) kg.currentState()).getHouseCount(p1,n), 160+100*(n-1), 360);
					}
					i++;
				}
				i=0;
				n++;
			}
			currentEval();
			g2d.setColor(Color.BLACK);
			g2d.drawString("State Evaluation (of player 1) (0-4900): "+ KalahState.simpleHeuristic(KalahMove.ofInt(1), kg.currentKalahState(), kg.currentState().getActivePlayer()), 10, 475);
			g2d.drawString("Player: "+ kg.currentState().getActivePlayer(), 10, 450);
			g2d.drawString("Player TWO", 300, 100);
			g2d.drawString("Player ONE", 300, 400);
			
			if (r3.isSelected()&&rr4.isSelected()) {
		    	System.out.println("Random Bot vs Human");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
				    	  kg.applyMove(randomBot.requestMove(kg.currentState()));
				    	  repaint();
					}
					i++;
			   }
		    } else if (r4.isSelected()&&rr3.isSelected()) {
		    	System.out.println("Human vs Random");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
				    	  kg.applyMove(randomBot.requestMove(kg.currentState()));
				    	  repaint();
					} 
					i++;
			   }
		    } else if (r2.isSelected()&&rr4.isSelected()) {
		    	System.out.println("Intermediate Bot vs Human");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
				    	  kg.applyMove(interBot.requestMove(kg.currentState()));
				    	  repaint();
					} 
					i++;
			   }
		    } else if (r4.isSelected()&&rr2.isSelected()) {
		    	System.out.println("Human vs Random");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
				    	  kg.applyMove(interBot.requestMove(kg.currentState()));
				    	  repaint();
					} 
					i++;
			   }
		    } else if (r1.isSelected()&&rr4.isSelected()) {
		    	System.out.println("Intermediate Bot vs Human");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p1) {
				    	  kg.applyMove(mmBot.requestMove(kg.currentState()));
				    	  repaint();
					} 
					i++;
			   }
		    } else if (r4.isSelected()&&rr1.isSelected()) {
		    	System.out.println("Human vs Random");
				//start1 = true;
				while (i<14) {
					if (((KalahState) kg.currentState()).getActivePlayer() == p2) {
				    	  kg.applyMove(mmBot.requestMove(kg.currentState()));
				    	  repaint();
					} 
					i++;
			   }
		    }
		}
	}
	
	public Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomC = new Color(r, g, b);
		return randomC;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private int currentEval() {
		if (KalahGUI.start1) {
			int k = ((KalahState) kg.currentState()).kalahCount(KalahGUI.p1) - ((KalahState) kg.currentState()).kalahCount(KalahGUI.p2);
			int h = 0;
			int b = 0;
			int c = 0;
			while (c<6) {
				if ((6-c) == ((KalahState) kg.currentState()).getHouseCount(KalahGUI.p1, c+1)) {
					b = b + 1;
				}
				if ((6-c) == ((KalahState) kg.currentState()).getHouseCount(KalahGUI.p2,  c+1)){
					b = b - 1;
				}
				c++;
			}
			int i = 0;
			while (i<6) {
				h = h + ((KalahState) kg.currentState()).getHouseCount(KalahGUI.p1, i+1) - ((KalahState) kg.currentState()).getHouseCount(KalahGUI.p2, i+1);
				i++;
			}
			
			int result = k + h + b;
			
			System.out.println("State of Board " + result);
			
			return result; } else {return 1000;}
	}
	

	
	
}