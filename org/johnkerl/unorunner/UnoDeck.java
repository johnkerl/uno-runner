package org.johnkerl.unorunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class UnoDeck
	extends UnoCardList
{
	private Random _random;

	// wikipedia: "The deck consists of 108 cards, of which there are 19 of
	// each color (red, green, blue, and yellow), each color having two of each
	// rank except zero. The ranks in each color are 0 through 9, "Skip", "Draw
	// Two", and "Reverse".  The remaining eight cards are "Wild" and "Wild
	// Draw Four", the deck having four of each."

	// Suits: RED, YELLOW, GREEN, BLUE, WILD.
	// Ranks: ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
	// SKIP, DRAW_TWO, REVERSE, DRAW_FOUR, BARE.

	public UnoDeck() {
		_cards = new LinkedList<UnoCard>();
		_random = new Random();

		for (UnoCard.Suit suit : UnoCard.COLOR_SUITS) {
			_cards.add(new UnoCard(suit, UnoCard.Rank.ZERO));
		}

		for (UnoCard.Suit suit : UnoCard.COLOR_SUITS) {
			for (int i = 0; i < 2; i++) {
				_cards.add(new UnoCard(suit, UnoCard.Rank.ONE));
				_cards.add(new UnoCard(suit, UnoCard.Rank.TWO));
				_cards.add(new UnoCard(suit, UnoCard.Rank.THREE));
				_cards.add(new UnoCard(suit, UnoCard.Rank.FOUR));
				_cards.add(new UnoCard(suit, UnoCard.Rank.FIVE));
				_cards.add(new UnoCard(suit, UnoCard.Rank.SIX));
				_cards.add(new UnoCard(suit, UnoCard.Rank.SEVEN));
				_cards.add(new UnoCard(suit, UnoCard.Rank.EIGHT));
				_cards.add(new UnoCard(suit, UnoCard.Rank.NINE));
				_cards.add(new UnoCard(suit, UnoCard.Rank.SKIP));
				_cards.add(new UnoCard(suit, UnoCard.Rank.DRAW_TWO));
				_cards.add(new UnoCard(suit, UnoCard.Rank.REVERSE));
			}
		}

		for (int i = 0; i < 4; i++)
			_cards.add(new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.BARE));
		for (int i = 0; i < 4; i++)
			_cards.add(new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.DRAW_FOUR));
	}

	public UnoCard peek() {
		if (_cards.isEmpty())
			return null;
		else
			return _cards.get(0);
	}

	public void shuffle() {
		int n = _cards.size();
		UnoCard[] array = new UnoCard[n];
		_cards.toArray(array);

		// Knuth shuffle.
		UnoCard temp;
		for (int i = 0; i < n; i++) {
			int j = i + _random.nextInt(n-i);
			temp     = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

		_cards.clear();
		for (UnoCard card : array)
			_cards.add(card);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Deck[");
		for (UnoCard card : _cards)
			sb.append(card+",");
		sb.append("]");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// Dev entry point, aside from unit-test.
	public static void main(String[] args) {
		UnoDeck deck = new UnoDeck();
		System.out.print("("+deck.size()+ ") ");
		deck.shuffle();
		System.out.println(deck);
	}
}
