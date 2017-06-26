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
 * @author natedaag
 *
 */
public class Deck {

	private ArrayList<Card> cards; 
	private boolean shuffled; 
	private Random rng = null;
	private int position;
	
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
	
	public void shuffle() 
		throws NoSuchAlgorithmException {
			
		if (rng == null) {
			rng = SecureRandom.getInstanceStrong();
		}
		Collections.shuffle(cards, rng);
		shuffled = true;
		position = 0;
	}
	
	public Card[] toArray() {
		return cards.toArray(new Card[] {});
	}
	
	public Card draw () 
			throws IndexOutOfBoundsException {
		return cards.get(position++);
	}
	
	public Card[] draw (int numCards)
		throws IndexOutOfBoundsException {
		Card[] hand = new Card[numCards];
		for (int i = 0; i < hand.length; i++) {
			hand[i] = draw();
		}
		return hand;
	}
	
}










