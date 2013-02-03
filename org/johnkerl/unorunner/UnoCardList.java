package org.johnkerl.unorunner;
import java.util.List;

// Abstract class for deck, discards, hand.
public abstract class UnoCardList {
	protected List<UnoCard> _cards;

	public void push(UnoCard card) {
		_cards.add(0, card);
	}

	public UnoCard pop() {
		if (_cards.isEmpty())
			return null;
		else
			return _cards.remove(0);
	}

	public boolean isEmpty() {
		return _cards.isEmpty();
	}

	public int size() {
		return _cards.size();
	}
}
