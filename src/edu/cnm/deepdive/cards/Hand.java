/**
 * 
 */
package edu.cnm.deepdive.cards;

import java.util.ArrayList;
import java.util.Arrays;

import edu.cnm.deepdive.cards.Card.Rank;

/**
 * @author natedaag
 *
 */
public class Hand implements Comparable<Hand> {

	private final Deck deck;
	private final int size;
	private final ArrayList<Card> cards;

	/**
	 * Creates a class that will declare a deck, and pull cards from that deck.
	 * 
	 * @param deck
	 *            Source of cards
	 * @param size
	 *            Declares the size of the hand.
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

	public String toString() {
		return cards.toString();
	}

	public Card[][] byRanks() {
		Card[][] table = new Card[Card.Rank.values().length][];
		for (Card.Rank rank : Card.Rank.values()) {
			ArrayList<Card> members = new ArrayList<>();
			for (Card card : cards) {
				if (card.rank == rank) {
					members.add(card);

				}

			}
			members.sort(null);
			table[rank.ordinal()] = members.toArray(new Card[] {});
		}
		return table;
	}

	public Card[][] bySuits() {
		Card[][] table = new Card[Card.Suit.values().length][];
		for (Card.Suit suit : Card.Suit.values()) {
			ArrayList<Card> members = new ArrayList<>();
			for (Card card : cards) {
				if (card.suit == suit) {
					members.add(card);

				}

			}
			members.sort(null);
			table[suit.ordinal()] = members.toArray(new Card[] {});
		}
		return table;
	}
	
	private boolean flush(Card[][] table) {
		for (Card[] suitedCards : table) {
			if (suitedCards.length > 0 && suitedCards.length < size) {
				return false;
			
			}
			
			if (suitedCards.length == size) {
				return true;
			}
		
		}
		return false;
		
	}
	
	private ArrayList<Card.Rank> sets(Card[][] table, int size) {
		ArrayList<Card.Rank>result = new ArrayList<>(); 
		for (Card.Rank rank : Card.Rank.values()) {
			if (table[rank.ordinal()].length == size) {
				result.add(rank);
			}
		}
		return result;
	}
	
	private Card.Rank
}

























