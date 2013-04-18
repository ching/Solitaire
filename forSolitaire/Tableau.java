package forSolitaire;

//Ching Yu

import javax.swing.*;

public class Tableau {
	public Deck myDeck;
	public Pile myPiles[];

	public Tableau(JLayeredPane theLPs[], JButton theButtons[][]) {
		myDeck = new Deck();
		myDeck.shuffle();
		myPiles = new Pile[4];
		for(int i=0; i<4; i++) {
			myPiles[i] = new Pile(theLPs[i], theButtons[i]);
		}//for
	}//Tableau constructor

	public boolean okayToMove(int source, int destination) {
		return !myPiles[source].isEmpty() && myPiles[destination].isEmpty();
	}//for okayToMove method

	public void move(int source, int destination) {
		CardWithImage cardBeingMoved = myPiles[source].getTopCard();
		myPiles[source].removeCard();
		myPiles[destination].acceptCard(cardBeingMoved);
	}//move method

	public boolean okayToRemove(int source) {
		CardWithImage cardToRemove = myPiles[source].getTopCard();
		for(int j = 0; j < 4 ; j ++) {
			CardWithImage cardToCompare = myPiles[j].getTopCard();
			if(cardToRemove.getSuit().equals(cardToCompare.getSuit()) 
					&& cardToRemove.getValue() < cardToCompare.getValue()) {
				return true;
				}//if
			}//else
		return false;
	}//okayToRemove method
	
	public void remove(int source){
		CardWithImage cardToRemove = myPiles[source].getTopCard();
		myPiles[source].removeCard();
	}//remove method

	public boolean decideVictory() {
		if(myDeck.cardsLeft()==0){
			for(int j=0; j<4; j++){
				if (myPiles[j].getTopCardPosition()== 1 && 
						(myPiles[j].getTopCard().getValue()== 14) && 
						(myPiles[j].getTopCard().getRank().equals("Ace") && 
						(myDeck.cardsLeft() == 0))) {
					return true;
				}//if
			}//for
		}//decideVictory
		return false;
	}//decideVictory method

	
	public boolean okayToDeal4() {
		for (int i=0; i<4;) {
			return myDeck.cardsLeft() >=4 
			&& !myPiles[i].isFull();
		}//for
		return false;
	}//okayToDeal4 method
	
	public void deal4FromDeckToPiles() {
		for (int i=0; i<4; i++) {
			myPiles[i].acceptCard(myDeck.dealOne(true));
		}//for i
	}//deal4FromDecktoPiles method

}//Tableau class
