package org.johnkerl.unorunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UnoCard {

	public static enum Suit {
		RED, YELLOW, GREEN, BLUE, WILD
	}
	public static enum Rank {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
		SKIP, DRAW_TWO, REVERSE, DRAW_FOUR, BARE
	}

	private static final Map<Suit, String> _suitsToNames;
	private static final Map<Rank, String> _ranksToNames;
	public static final List<Suit> COLOR_SUITS;

	static {
		_suitsToNames = new HashMap<Suit, String>();
		_suitsToNames.put(Suit.RED,    "red");
		_suitsToNames.put(Suit.YELLOW, "yellow");
		_suitsToNames.put(Suit.GREEN,  "green");
		_suitsToNames.put(Suit.BLUE,   "blue");
		_suitsToNames.put(Suit.WILD,   "wild");

		_ranksToNames = new HashMap<Rank, String>();
		_ranksToNames.put(Rank.ZERO,      "0");
		_ranksToNames.put(Rank.ONE,       "1");
		_ranksToNames.put(Rank.TWO,       "2");
		_ranksToNames.put(Rank.THREE,     "3");
		_ranksToNames.put(Rank.FOUR,      "4");
		_ranksToNames.put(Rank.FIVE,      "5");
		_ranksToNames.put(Rank.SIX,       "6");
		_ranksToNames.put(Rank.SEVEN,     "7");
		_ranksToNames.put(Rank.EIGHT,     "8");
		_ranksToNames.put(Rank.NINE,      "9");
		_ranksToNames.put(Rank.SKIP,      "skip");
		_ranksToNames.put(Rank.DRAW_TWO,  "draw-two");
		_ranksToNames.put(Rank.REVERSE,   "reverse");
		_ranksToNames.put(Rank.DRAW_FOUR, "draw-four");
		_ranksToNames.put(Rank.BARE,      "bare");

		COLOR_SUITS = new LinkedList<Suit>();
		COLOR_SUITS.add(Suit.RED);
		COLOR_SUITS.add(Suit.YELLOW);
		COLOR_SUITS.add(Suit.GREEN);
		COLOR_SUITS.add(Suit.BLUE);
	}

	private Suit _suit;
	private Rank _rank;

	// xxx to do: prohibit invalid combinations, e.g. wild+skip.
	public UnoCard(Suit suit, Rank rank) {
		_suit = suit;
		_rank = rank;
	}

	public Suit getSuit() {
		return _suit;
	}
	public Rank getRank() {
		return _rank;
	}

	// Cards:
	// red/green/blue/yellow 0-9,skip,reverse,draw-two
	// wild bare,draw-four

	//   that   RGBY                 wild
	// this   +--------------------+----------------
	// RGBY   | suit or rank match | wild-suit match
	// wild   | playable           | playable

	public boolean playableOn(UnoCard that, UnoCard.Suit wildSuit) {
		if (this._suit == Suit.WILD)
			return true;
		if (that._suit == Suit.WILD)
			return this._suit == wildSuit;
		if ((this._suit == that._suit) || (this._rank == that._rank))
			return true;
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UnoCard))
			return false;
		UnoCard that = (UnoCard)o;
		return (this._suit == that._suit) && (this._rank == that._rank);
	}
	@Override
	public String toString() {
		return _suitsToNames.get(_suit) + "/" + _ranksToNames.get(_rank);
	}
}
