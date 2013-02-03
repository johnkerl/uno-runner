package org.johnkerl.unorunner;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// Simple-minded as this is, this is the strategy I actually use when playing
// Uno.  I could be more clever and try to count cards, but usually I'm joking
// around with the family members I'm playing with, the game is only part of
// the conversation, and this is all I want.

public class SimpleUnoStrategy
	implements UnoStrategy
{
	private PriorityQueue<SuitCount> _suitCountQueue;
	private PriorityQueue<UnoCard>   _playableQueue;

	public SimpleUnoStrategy() {
		_suitCountQueue = new PriorityQueue<SuitCount>();
		_playableQueue = new PriorityQueue<UnoCard>(
			10, new ValueComparator());
	}

	// Cards:
	// Suit: RED, YELLOW, GREEN, BLUE, WILD.
	// Rank: ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
	// SKIP, DRAW_TWO, REVERSE, DRAW_FOUR, BARE.

	public UnoCard playTo(UnoHand hand,
		UnoCard topDiscard, UnoCard.Suit wildSuit)
	{
		List<UnoCard> cards = hand.getCards();

		_playableQueue.clear();
		for (UnoCard card : cards) {
			if (card == null)
				throw new IllegalStateException("Asked to play an empty hand -- game should be over!");
			if (card.playableOn(topDiscard, wildSuit)) {
				_playableQueue.add(card);
			}
		}
		UnoCard choice = _playableQueue.peek();
		if (choice != null)
			hand.remove(choice);
		return choice; // null if empty.
	}

	// Choose the suit we have the most of.
	public UnoCard.Suit chooseSuitForWild(UnoHand hand)
	{
		int redCount    = 0;
		int yellowCount = 0;
		int greenCount  = 0;
		int blueCount   = 0;

		for (UnoCard card : hand.getCards()) {
			if      (card.getSuit() == UnoCard.Suit.RED)    redCount++;
			else if (card.getSuit() == UnoCard.Suit.YELLOW) yellowCount++;
			else if (card.getSuit() == UnoCard.Suit.GREEN)  greenCount++;
			else if (card.getSuit() == UnoCard.Suit.BLUE)   blueCount++;
		}
		_suitCountQueue.clear();
		_suitCountQueue.add(new SuitCount(UnoCard.Suit.RED,    redCount));
		_suitCountQueue.add(new SuitCount(UnoCard.Suit.YELLOW, yellowCount));
		_suitCountQueue.add(new SuitCount(UnoCard.Suit.GREEN,  greenCount));
		_suitCountQueue.add(new SuitCount(UnoCard.Suit.BLUE,   blueCount));

		return _suitCountQueue.peek()._suit;
	}

	private static class SuitCount
		implements Comparable
	{
		public final UnoCard.Suit _suit;
		public final int _count;
		public SuitCount(UnoCard.Suit suit, int count) {
			_suit  = suit;
			_count = count;
		}
		public int compareTo(Object o) {
			SuitCount that = (SuitCount)o;
			return that._count - this._count;
		}
	}

	// This is made package-private for unit-test access.
	/*package private*/ static class ValueComparator
		implements Comparator<UnoCard>
	{
		// Strategy: make the opponent draw the most possible.
		// Else reverse/skip to mess with them somehow.
		// Else play a wild bare.
		// Else play a numbered card.
		@Override
		public int compare(UnoCard c1, UnoCard c2) {
			UnoCard.Rank c1rank = c1.getRank();
			UnoCard.Rank c2rank = c2.getRank();

			boolean drawFour1 = c1rank == UnoCard.Rank.DRAW_FOUR;
			boolean drawFour2 = c2rank == UnoCard.Rank.DRAW_FOUR;

			boolean drawTwo1 = c1rank == UnoCard.Rank.DRAW_TWO;
			boolean drawTwo2 = c2rank == UnoCard.Rank.DRAW_TWO;

			boolean reverse1 = c1rank == UnoCard.Rank.REVERSE;
			boolean reverse2 = c2rank == UnoCard.Rank.REVERSE;

			boolean skip1 = c1rank == UnoCard.Rank.SKIP;
			boolean skip2 = c2rank == UnoCard.Rank.SKIP;

			boolean bare1 = c1rank == UnoCard.Rank.BARE;
			boolean bare2 = c2rank == UnoCard.Rank.BARE;

			if (drawFour1 && drawFour2)
				return 0;
			else if (drawFour1 && !drawFour2)
				return 1;
			else if (!drawFour1 && drawFour2)
				return -1;

			else if (drawTwo1 && drawTwo2)
				return 0;
			else if (drawTwo1 && !drawTwo2)
				return 1;
			else if (!drawTwo1 && drawTwo2)
				return -1;

			else if (reverse1 && reverse2)
				return 0;
			else if (reverse1 && !reverse2)
				return 1;
			else if (!reverse1 && reverse2)
				return -1;

			else if (skip1 && skip2)
				return 0;
			else if (skip1 && !skip2)
				return 1;
			else if (!skip1 && skip2)
				return -1;

			else if (bare1 && bare2)
				return 0;
			else if (bare1 && !bare2)
				return 1;
			else if (!bare1 && bare2)
				return -1;

			else
				return 0;
		}

		@Override
		public boolean equals(Object o) {
			System.out.println("OBMO!");
			throw new IllegalStateException("OBMO!");
		}
	}
}
