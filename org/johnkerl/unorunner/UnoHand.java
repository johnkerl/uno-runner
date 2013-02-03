package org.johnkerl.unorunner;

import java.util.LinkedList;
import java.util.List;

public class UnoHand
	extends UnoCardList
{
	public UnoHand() {
		_cards = new LinkedList<UnoCard>();
	}

	public List<UnoCard> getCards() {
		return _cards;
	}

	public void remove(UnoCard card) {
		boolean removed = _cards.remove(card);
		if (!removed)
			throw new IllegalStateException("Asked to remove a card"
				+" we don't have: "+card);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Hand[");
		for (UnoCard card : _cards)
			sb.append(card+",");
		sb.append("]");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// Dev entry point, aside from unit-test.
	public static void main(String[] args) {
		UnoHand hand = new UnoHand();
		System.out.println("("+hand.size()+ ") ");
	}
}
