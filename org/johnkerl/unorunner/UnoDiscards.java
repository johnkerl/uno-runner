package org.johnkerl.unorunner;

import java.util.LinkedList;
import java.util.List;

public class UnoDiscards
	extends UnoCardList
{
	public UnoDiscards() {
		_cards = new LinkedList<UnoCard>();
	}

	public UnoCard peek() {
		if (_cards.isEmpty())
			return null;
		else
			return _cards.get(0);
	}

	// For restore to deck after deck has been played out.
	// xxx to do:  exc on not enough.
	public UnoCard undiscard() {
		return _cards.remove(1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Discards[");
		for (UnoCard card : _cards)
			sb.append(card+",");
		sb.append("]");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// Dev entry point, aside from unit-test.
	public static void main(String[] args) {
		UnoDiscards discards = new UnoDiscards();
		System.out.println("("+discards.size()+ ") ");
	}
}
