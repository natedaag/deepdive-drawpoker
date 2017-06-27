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

	public static final int DEFAULT_HAND_SIZE = 5;
	private static final int HIGH_CARD_VALUE = 0;
	private static final int PAIR_VALUE = 1;
	private static final int TWO_PAIR_VALUE = 2;
	private static final int THREE_OF_A_KIND_VALUE = 3;
	private static final int STRAIGHT_START_VALUE = 4;
	private static final int FLUSH_VALUE = 5;
	private static final int FULL_HOUSE_VALUE = 6;
	private static final int FOUR_OF_A_KIND_VALUE = 7;
	private static final int STRAIGHT_FLUSH_VALUE = 8;
	
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
	protected Hand(Deck deck, int size) {
		this.deck = deck;
		this.size = size;
		cards = new ArrayList<>(Arrays.asList(deck.draw(size)));
	} // end Hand
	
	/**
	 * Sets a default hand size
	 * 
	 * @param deck Imports deck to be used in this method
	 */
	public Hand(Deck deck) {
		this(deck, DEFAULT_HAND_SIZE);
	}

	@Override
	public int compareTo(Hand otherHand) {
		int[] value = this.value();
		int[] otherValue = otherHand.value();
		for (int i = 0; i < Math.min(value.length, otherValue.length); i++) {
			int comparison = Integer.compare(value[i], otherValue[i]);
			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;
	}

	@Override
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
		ArrayList<Card.Rank> result = new ArrayList<>();
		for (Card.Rank rank : Card.Rank.values()) {
			if (table[rank.ordinal()].length == size) {
				result.add(rank);
			}
		} // end for loop
		return result;
	} // end sets

	private Card.Rank run(Card[][] table, boolean lowHigh) {
		Card.Rank runStart = null;
		int runLength = 0;
		boolean inRun = false;
		for (Card.Rank rank : Card.Rank.values()) {
			if (table[rank.ordinal()].length == 1) {
				if (!inRun) {
					runStart = rank;
					runLength = 0;
					inRun = true;
				}
				runLength++;
			} else if (runLength != size) {
				inRun = false;
				runStart = null;
				runLength = 0;
			} else {
				inRun = false;
			}
		} // end for

		if (runStart != null && lowHigh && table[0].length == 1) {
			runLength++;
		} // end if
		return (runLength == size) ? runStart : null;
	} // end run

	private Card.Rank run(Card[][] table) {
		return run(table, true);
	} // end private run

	private int[] value() {
		Card[][] byRanks = this.byRanks();
		Card[][] bySuits = this.bySuits();
		boolean flush = this.flush(bySuits);
		Card.Rank straightStart = this.run(byRanks);
		boolean straightFlush = flush && (straightStart != null);
		ArrayList<Card.Rank> fourOfAKind = this.sets(byRanks, 4);
		ArrayList<Card.Rank> threeOfAKind = this.sets(byRanks, 3);
		ArrayList<Card.Rank> twoOfAKind = this.sets(byRanks, 2);
		ArrayList<Card.Rank> oneOfAKind = this.sets(byRanks, 1);
		boolean fullHouse = (threeOfAKind.size() > 0) && (twoOfAKind.size() > 0);
		boolean twoPair = (twoOfAKind.size() == 2);

		// Check for straight Flush
		if (straightFlush) {
			return straightFlush(straightStart);
		} // end straightFlush
		
		// Check for four of a kind
		if (fourOfAKind.size() > 0) {
			return fourOfAKind(fourOfAKind, oneOfAKind);
		} // end if
		
		// Check for full house
		if (fullHouse) {
			return fullHouseValue(threeOfAKind, twoOfAKind);
		} // end if

		// Check for flush
		if (flush) {
			return flushValue();
		} // end if
		
		// Check for Straight
		if (straightStart != null) {
			return straightStartValue(straightStart);
		}
		if (threeOfAKind.size() > 0) {
			return threeOfAKind(threeOfAKind);
		} // end if 3ofakind
		
		// Check for 2pair
		if (twoPair) {
			return twoPairValue(twoOfAKind, oneOfAKind);
		} // end if2pair
		
		// Check for pair
		if (twoOfAKind.size() > 0) {
			return twoOfAKindValue(twoOfAKind, oneOfAKind);

		} // end 2ofakind
		
		// EVERYTHING ELSE - Check for high card.
		
		return highCardValue(twoOfAKind, oneOfAKind);

	} // end value

	private int[] straightFlush(Card.Rank straightStart) {
		return new int[] { STRAIGHT_FLUSH_VALUE, straightStart.ordinal() };
	}

	private int[] fourOfAKind(ArrayList<Card.Rank> fourOfAKind, ArrayList<Card.Rank> oneOfAKind) {
		int setRank = fourOfAKind.get(0).ordinal();
		if (setRank == 0) {
			setRank = Card.Rank.values().length;
		}
		return new int[] { FOUR_OF_A_KIND_VALUE, setRank, oneOfAKind.get(0).ordinal() };
	}

	private int[] fullHouseValue(ArrayList<Card.Rank> threeOfAKind, ArrayList<Card.Rank> twoOfAKind) {
		int overSetRank = threeOfAKind.get(0).ordinal();
		if (overSetRank == 0) {
			overSetRank = Card.Rank.values().length;
		}
		int underSetRank = twoOfAKind.get(0).ordinal();
		if (underSetRank == 0) {
			underSetRank = Card.Rank.values().length;
		}
		return new int[] { FULL_HOUSE_VALUE, overSetRank, underSetRank };
	}

	private int[] flushValue() {
		int[] ranks = new int[size];
		for (int i = 0; i < size; i++) {
			int cardRank = cards.get(i).rank.ordinal();
			if (cardRank == 0) {
				cardRank = Card.Rank.values().length;
			}
			ranks[i] = cardRank;
		}
		Arrays.sort(ranks);
		int[] score = new int[size + 1];
		score[0] = FLUSH_VALUE;
		for (int i = size - 1; i >= 0; i--) {
			score[size - i] = ranks[i];
		} // end for
		return score;
	}

	private int[] straightStartValue(Card.Rank straightStart) {
		return new int[] { STRAIGHT_START_VALUE, straightStart.ordinal() };
	}

	private int[] threeOfAKind(ArrayList<Card.Rank> threeOfAKind) {
		int setRank = threeOfAKind.get(0).ordinal();
		if (setRank == 0) {
			setRank = Card.Rank.values().length;
		}
		return new int[] { THREE_OF_A_KIND_VALUE, setRank };
	}

	private int[] twoPairValue(ArrayList<Card.Rank> twoOfAKind, ArrayList<Card.Rank> oneOfAKind) {
		int firstRank = twoOfAKind.get(0).ordinal();
		int secondRank = twoOfAKind.get(1).ordinal();
		int otherRank = oneOfAKind.get(0).ordinal();
		if (firstRank == 0) {
			return new int[] { TWO_PAIR_VALUE, Card.Rank.values().length, secondRank, otherRank };
		} else {
			return new int[] { TWO_PAIR_VALUE, secondRank, firstRank, otherRank };
		} // end else
	}

	private int[] twoOfAKindValue(ArrayList<Card.Rank> twoOfAKind, ArrayList<Card.Rank> oneOfAKind) {
		int setRank = twoOfAKind.get(0).ordinal();
		if (setRank == 0) {
			setRank = Card.Rank.values().length;
		}
		int[] ranks = new int[size - 2];
		for (int i = 0; i < oneOfAKind.size(); i++) {
			int cardRank = oneOfAKind.get(i).ordinal();
			if (cardRank == 0) {
				cardRank = Card.Rank.values().length;
			}
			ranks[i] = cardRank;
		}
		Arrays.sort(ranks);
		int[] score = new int[size];
		score[0] = PAIR_VALUE;
		score[1] = setRank;
		for (int i = ranks.length - 1; i >= 0; i--) {
			score[ranks.length - i + 1] = ranks[i];
		} // end for
		return score;
	}

	private int[] highCardValue(ArrayList<Card.Rank> twoOfAKind, ArrayList<Card.Rank> oneOfAKind) {
		int[] ranks = new int[size];
		for (int i = 0; i < size; i++) {
			int cardRank = cards.get(i).rank.ordinal();
			if (cardRank == 0) {
				cardRank = Card.Rank.values().length;
			}
			ranks[i] = cardRank;
		}
		Arrays.sort(ranks);
		int[] score = new int[size + 1];
		score[0] = HIGH_CARD_VALUE;
		for (int i = size - 1; i >= 0; i--) {
			score[size - i] = ranks[i];
		} // end for
		return score;
	}
} // end class
