/**
 * 
 */
package edu.cnm.deepdive.cards;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Container class for all distinct playing {@link Card Card} instances in a 
 * standard deck of playing cards.
 * 
 * @author natedaag
 *
 */
public class Deck {

	private ArrayList<Card> cards; 
	private boolean shuffled; 
	private Random rng = null;
	private int position;
	
	/**
	 * Creates the deck from which cards will be drawn for games, such as poker.
	 */
	public Deck() {
		super();
		cards = new ArrayList<>();
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}
		shuffled = false;
		position = 0;
	}
	
	/**
	 * Creates a  method that will shuffle the cards, so that they will be randomized to 
	 * put into hands.
	 * 
	 * @throws NoSuchAlgorithmException 	Possibly throws exception, which we have handled.
	*/
	public void shuffle() 
		throws NoSuchAlgorithmException {
			
		if (rng == null) {
			rng = new SecureRandom();
		}
		Collections.shuffle(cards, rng);
		shuffled = true;
		position = 0;
	}
	
	/**
	 * Return an array containing all cards in the <code>Deck</code>, in 
	 * the current order.
	 *  
	 * @return Returns cards to the array from which they'll be drawn.
	 */
	public Card[] toArray() {
		return cards.toArray(new Card[] {});
	}
	
	/**
	 * Method to draw a card or number of cards to begin the game.
	 * 
	 * @return Returns cards to array.
	 * @throws IndexOutOfBoundsException 	If the deck is out of cards.
	 */
	public Card draw () 
			throws IndexOutOfBoundsException {
		return cards.get(position++);
	}
	
	/**
	 * Draw and return top numCards from the shuffled deck.
	 * 
	 * @param numCards 						From user - this is how many cards they'd like.
	 * @return								Specified number of cards from a specified deck.
	 * @throws IndexOutOfBoundsException	If the deck is out of cards
	 */
	public Card[] draw (int numCards)
		throws IndexOutOfBoundsException {
		Card[] hand = new Card[numCards];
		for (int i = 0; i < hand.length; i++) {
			hand[i] = draw();
		}
		return hand;
	}
	
}










