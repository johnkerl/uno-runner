package org.johnkerl.unorunner;

public class UnoGame
{
	private static final int NUM_CARDS_PER_HAND = 7;
	private UnoDeck      _deck;
	private UnoHands     _hands;
	private UnoDiscards  _discards;
	private UnoStrategy  _strategy;
	private UnoCard.Suit _wildSuit = null;
	private int          _numDrawsPending;
	private boolean      _done;
	private int          _numTurns;
	private UnoGameVisitor _visitor;

	// As presently coded, this uses the same strategy for each player.
	public UnoGame(int numHands, UnoStrategy strategy, UnoGameVisitor visitor) {
		_deck     = new UnoDeck();
		_hands    = new UnoHands(numHands);
		_discards = new UnoDiscards();
		_strategy = strategy;

		_deck.shuffle();

		for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
			for (int j = 0; j < numHands; j++)
				_hands.push(j, _deck.pop());
		}
		_discards.push(_deck.pop());
		UnoCard cardPlayed = _discards.peek();
		_wildSuit = cardPlayed.getSuit();

		if (cardPlayed.getRank() == UnoCard.Rank.REVERSE)
			_hands.reverse();
		if (cardPlayed.getRank() == UnoCard.Rank.SKIP)
			_hands.advance();

		_numDrawsPending = 0;
		_done = false;
		_numTurns = 0;

		_visitor = visitor;
	}

	public void play() {
		_visitor.doStartOfGame(this);
		while (!done())
			turn();
		_visitor.doEndOfGame(this);
	}


	// ----------------------------------------------------------------
	public int getNumTurns() {
		return _numTurns;
	}
	public int getDeckSize() {
		return _deck.size();
	}
	public int getDiscardSize() {
		return _discards.size();
	}
	public int getNumHands() {
		return _hands.size();
	}
	public int getHandSize(int whichHand) {
		// xxx to do: clarify what happens on out-of-bounds exception.
		return _hands.getHandSize(whichHand);
	}

	// ----------------------------------------------------------------
	private void turn() {
		UnoHand currentHand = _hands.getCurrentHand();
		UnoCard topDiscard = _discards.peek();
		UnoCard.Rank discardRank = topDiscard.getRank();

		_visitor.doStartOfTurn(this);

		if (_numDrawsPending > 0) {
			while (_numDrawsPending-- > 0) {
				currentHand.push(draw());
				_visitor.doDraw(this);
			}
		}
		else {
			UnoCard cardPlayed = _strategy.playTo(currentHand,
				topDiscard, _wildSuit);
			while (cardPlayed == null) {
				currentHand.push(draw());
				cardPlayed = _strategy.playTo(currentHand,
					topDiscard, _wildSuit);
			}

			if (cardPlayed.getSuit() == UnoCard.Suit.WILD)
				_wildSuit = _strategy.chooseSuitForWild(currentHand);
			else
				_wildSuit = cardPlayed.getSuit();

			_visitor.doCardPlayed(this, cardPlayed);

			_discards.push(cardPlayed);

			// Wild choice of suit ...
			UnoCard.Rank cardPlayedRank = cardPlayed.getRank();

			if (cardPlayedRank == UnoCard.Rank.REVERSE)
				_hands.reverse();
			if (cardPlayedRank == UnoCard.Rank.SKIP)
				_hands.advance();
			_hands.advance();

			if (cardPlayedRank == UnoCard.Rank.DRAW_TWO)
				_numDrawsPending = 2;
			else if (cardPlayedRank == UnoCard.Rank.DRAW_FOUR)
				_numDrawsPending = 4;
		}
		_visitor.doEndOfTurn(this);

		_numTurns++;
	}

	private UnoCard draw() {
		if (_deck.isEmpty()) {
			while (_discards.size() > 1)
				_deck.push(_discards.undiscard());
			_deck.shuffle();
		}
		return _deck.pop();
	}

	private boolean done() {
		return _hands.isAnyHandEmpty();
	}
}
