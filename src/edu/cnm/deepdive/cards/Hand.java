/**
 * 
 */
package edu.cnm.deepdive.cards;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author natedaag
 *
 */
public class Hand implements Comparable<Hand> {
	
	private final Deck deck;
	private final int size;
	private final ArrayList<Card> cards;
	
	/**Creates a class that will declare a deck, and pull cards from that deck.
	 * 
	 * @param deck 	Source of cards
	 * @param size 	Declares the size of the hand.
	 */
	public Hand(Deck deck, int size) {
		this.deck = deck;
		this.size = size;
		cards = new ArrayList<>(Arrays.asList(deck.draw(size)));
	}

	@Override
	public int compareTo(Hand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
