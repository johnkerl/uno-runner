package org.johnkerl.unorunner;
// last/next player: _hands.getCurrentIndex()
// shufflecount
// drawcount

public class UnoGame
{
	private static final int NUM_CARDS_PER_HAND = 7;
	private UnoDeck        _deck;
	private UnoHands       _hands;
	private UnoDiscards    _discards;
	private UnoStrategy    _strategy;
	private UnoCard.Suit   _wildSuit = null;
	private int            _numDrawsPending;
	private int            _numTurns;
	private int            _numShuffles;
	private int            _lastPlayerIndex;
	private UnoCard        _lastCardPlayed;
	private int            _lastNumDraws;
	private boolean        _done;
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
		_numShuffles = 1;
		_lastPlayerIndex = _hands.getCurrentIndex();
		_lastCardPlayed = null;
		// xxx to do: distinguish between draws due to draw-2/draw-4 cards,
		// and draws due to unplayable hand?
		_lastNumDraws = 0;

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
	public int getNumShuffles() {
		return _numShuffles;
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
	public int getLastPlayerIndex() {
		return _lastPlayerIndex;
	}
	public UnoCard getLastCardPlayed() {
		return _lastCardPlayed;
	}
	public int getLastNumDraws() {
		return _lastNumDraws;
	}

	// ----------------------------------------------------------------
	private void turn() {
		UnoHand currentHand = _hands.getCurrentHand();
		UnoCard topDiscard = _discards.peek();
		UnoCard.Rank discardRank = topDiscard.getRank();

		_lastPlayerIndex = _hands.getCurrentIndex();

		_visitor.doStartOfTurn(this);

		_lastNumDraws = 0;
		if (_numDrawsPending > 0) {
			_lastCardPlayed = null;
			while (_numDrawsPending-- > 0) {
				currentHand.push(draw());
				_lastNumDraws++;
			}
		}
		else {
			UnoCard cardPlayed = _strategy.playTo(currentHand,
				topDiscard, _wildSuit);
			while (cardPlayed == null) {
				currentHand.push(draw());
				_lastNumDraws++;
				cardPlayed = _strategy.playTo(currentHand,
					topDiscard, _wildSuit);
			}
			_lastCardPlayed = cardPlayed;

			if (cardPlayed.getSuit() == UnoCard.Suit.WILD)
				_wildSuit = _strategy.chooseSuitForWild(currentHand);
			else
				_wildSuit = cardPlayed.getSuit();

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
		_numTurns++;
		_visitor.doEndOfTurn(this);
	}

	private UnoCard draw() {
		if (_deck.isEmpty()) {
			while (_discards.size() > 1)
				_deck.push(_discards.undiscard());
			_deck.shuffle();
			_numShuffles++;
		}
		return _deck.pop();
	}

	private boolean done() {
		return _hands.isAnyHandEmpty();
	}
}
