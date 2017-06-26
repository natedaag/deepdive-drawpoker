/**
 * 
 */
package edu.cnm.deepdive.cards;

/**
 * Creates cards that will be used throughout games - these will be drawn for our games.
 * 
 * @author natedaag
 */
public class Card implements Comparable<Card> {
	
	/**
	 * The suit of the cards.
	 */
	public final Suit suit;
	
	/**
	 * The number on the cards.
	 */
	public final Rank rank;
	
	/**
	 * Defines the cards' suits and ranks that will be chosen.
	 * 
	 * @param suit 	Clubs, diamonds, hearts, spades.
	 * @param rank	1,2,3,4,5,6,7,8,9,10,J,Q,K
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	@Override
	public String toString() {
		return rank.toString() + suit.toString();
	}
	
	@Override
	public int compareTo(Card card) {
		int suitComparison = this.suit.compareTo(card.suit );
		if (suitComparison != 0) {
			return suitComparison;
		}
		return this.rank.compareTo(card.rank);
	}
	
	/**
	 * Declares the suits for the cards.
	 * 
	 * @author natedaag
	 */
	public enum Suit {
		CLUBS, 
		DIAMONDS, 
		HEARTS, 
		SPADES;
		
		@Override
		public String toString() {
			String value = null;
			switch (this) {
			case CLUBS:
				value = "\u2663";
				break;
			case DIAMONDS:
				value = "\u2666";
				break;
			case HEARTS:
				value = "\u2665";
				break;
			case SPADES:
				value = "\u2660";
				break;
			}
			return value;
		}

	}

	/**
	 * The value of the cards.
	 * 
	 * @author natedaag
	 */
	public enum Rank {
		ACE(1, 'A'), 
		TWO(2, '2'), 
		THREE(3, '3'), 
		FOUR(4, '4'), 
		FIVE(5, '5'), 
		SIX(6, '6'), 
		SEVEN(7, '7'), 
		EIGHT(8, '8'), 
		NINE(9, '9'), 
		TEN(10, 'T') {
			public String toString() {
				return "10";
			}
		},
		JACK(10, 'J'), 
		QUEEN(10, 'Q'), 
		KING(10, 'K');

		/**
		 * Value of cards - lowest to highest.
		 */
		public final int value;
		
		/**
		 * Representation of the suit
		 */
		public final char symbol;
		
		private Rank(int value, char symbol) {
			this.value = value;
			this.symbol = symbol;
		}
		

	
		
		@Override
		public String toString() {
			return new StringBuilder().append(symbol).toString();
			}
		}
		
	}


	


