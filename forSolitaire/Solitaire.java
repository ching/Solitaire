package forSolitaire;

//Ching Yu

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Solitaire extends JFrame {

	//for array of layered panes
	private JLayeredPane myLPs[]; 
	private JTextField messageTF;
	//for array of arrays of buttons
	private JButton cButton[][], 
							   dealB, 
							   doneB; 
	private Container myCP; 
	//for determining what to do on second click
	private boolean processingSecond; 
	private Tableau myTableau; 
	private Pile myPile; 
	private int firstPileClicked;

	public Solitaire () {
		super ("Solitaire"); 
		setSize (350, 600); 
		
		myCP = getContentPane(); 
		myCP.setLayout(null); 

		myLPs = new JLayeredPane [4];
		for (int i = 0; i < 4; i++) { 
			myLPs[i] = new JLayeredPane(); 
			myLPs[i].setLocation(20 + 60 * i, 10); 
			myLPs[i].setSize(50, 400); 
			myLPs[i].setLayout(null); 
			myCP.add(myLPs[i]); 
		}// for 4 layered panes

		//cButton points to an array of 14 JButtons
		cButton= new JButton [4][14];
		//parameters to construct 4 layered panes
		for (int j = 0; j < 4; j++) {
			CardBHandler cBH = new CardBHandler (j); 
			for (int i = 0; i < 14; i++) { 
				cButton[j][i] = new JButton(); 
				cButton[j][i].setLocation(0, 25 * i); 
				cButton[j][i].setSize( 49, 71); 
				cButton[j][i].setIcon(null); 
				cButton[j][i].addActionListener(cBH);
			}// for 14 buttons (i)
			
			 // button for empty pile
			cButton[j][0].setLocation(0,25);
			// add empty pile button to layered pane
			myLPs[j].add(cButton[j][0], 0); 
		}// for 4 layered panes (j)
		
		myTableau = new Tableau (myLPs, cButton);

		dealB = new JButton ("Deal a Card"); 
		dealB.setLocation(10, 440); 
		dealB.setSize( 140, 30); 
		myCP.add(dealB);
		dealB.addActionListener(new DealBHandler());  

		doneB = new JButton ("Done"); 
		doneB.setLocation(170, 440); 
		doneB.setSize( 140, 30); 
		myCP.add(doneB); 
		doneB.addActionListener(new DoneBHandler()); 
										
		messageTF = new JTextField (); 
		messageTF.setLocation(10, 500); 
		messageTF.setSize(300, 60);
		messageTF.setEditable(false); 
		myCP.add(messageTF); 
		
		processingSecond = false;
		firstPileClicked = 27;

		setVisible(true); 
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}//windowClosing
		}); //end of definition of WindowAdapter 
		
	}//Solitaire constructor

	public class CardBHandler implements ActionListener {
		private int myPileNumber;
		
		public CardBHandler (int thePileNumber) {
			myPileNumber = thePileNumber;
		}//constructor
		
		public void actionPerformed(ActionEvent e) {
			if(!processingSecond) {
				firstPileClicked = myPileNumber;
				processingSecond = true;
			} else {
				processingSecond = false;
				if (firstPileClicked == myPileNumber) {
					if (myTableau.okayToRemove(firstPileClicked)) {
						myTableau.remove(firstPileClicked);
						messageTF.setText("");
					} else {
						messageTF.setText("You cannot remove this card!");
					}//else
				} else {
					if (myTableau.okayToMove(firstPileClicked, myPileNumber)) {
						myTableau.move(firstPileClicked, myPileNumber);
						messageTF.setText("");
					} else {
						messageTF.setText("You cannot move this card!");
					}//inner else
				}//inner else
			}//outer else
		}//actionPerformed
	}//CardBHandler

	public class DealBHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(processingSecond){
				messageTF.setText("You shouldn't be dealing when processing clicks");
			} else if(myTableau.okayToDeal4()){
				myTableau.deal4FromDeckToPiles();
				messageTF.setText("You just dealt 4 cards");
			} else{
				messageTF.setText("You cannot deal any more cards.");
			}//else
		}//actionPerformed
	}//DealBHandler
	
	public class DoneBHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (myTableau.decideVictory()){
				messageTF.setText("You've won the game...FINALLY!");
			} else {
				messageTF.setText("You've lost. Don't lose hope though...");
			}//else
			for (int j=0; j<4; j++){
				for (int i=0; i<14; i++) {
					cButton[j][i].setEnabled(false);
				}//for i
			}//for j
			doneB.setEnabled(false);
			dealB.setEnabled(false);
		}//actionPerformed
	}//DoneBHandler

	public static void main (String args[]) {
		Solitaire myAppF = new Solitaire();
	}//main
} //Solitaire class