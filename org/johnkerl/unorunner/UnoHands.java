package org.johnkerl.unorunner;

import java.util.ArrayList;

public class UnoHands
{
	private ArrayList<UnoHand> _hands;
	private int _numHands;
	private int _currentIndex;
	private boolean _forward;

	public UnoHands(int numHands) {
		if (numHands < 2) {
			throw new IllegalArgumentException("numHands must be >= 2; got "
				+numHands+".");
		}
		_hands = new ArrayList<UnoHand>(numHands);
		_numHands = numHands;
		for (int i = 0; i < numHands; i++)
			_hands.add(new UnoHand());
		_currentIndex = 0;
		_forward = true;
	}

	public int size() {
		return _numHands;
	}

	public void push(int i, UnoCard card) {
		if ((i < 0) || (i >= _numHands))
			throw new IllegalArgumentException("index must be >= 0 and < "
			+_numHands+"; got " +i+".");
		_hands.get(i).push(card);
	}

	public void reverse() {
		_forward = !_forward;
	}

	public void advance() {
		if (_forward) {
			_currentIndex++;
			if (_currentIndex >= _numHands)
				_currentIndex = 0;
		}
		else {
			_currentIndex--;
			if (_currentIndex < 0)
				_currentIndex = _numHands - 1;
		}
	}

	public UnoHand getCurrentHand() {
		return _hands.get(_currentIndex);
	}

	public int getCurrentIndex() {
		return _currentIndex;
	}

	public int getHandSize(int i) {
		return _hands.get(i).size();
	}

	public boolean isAnyHandEmpty() {
		for (UnoHand hand : _hands)
			if (hand.isEmpty())
				return true;
		return false;
	}

	// ----------------------------------------------------------------
	// Dev entry point, aside from unit-test.
	public static void main(String[] args) {
		UnoHands hands = new UnoHands(4);
		System.out.println("("+hands.size()+ ") ");
	}
}
