package org.johnkerl.unorunner;

// These are the two things you have control over in Uno.  The rest is random.
public interface UnoStrategy
{
	public UnoCard.Suit chooseSuitForWild(UnoHand hand);
	public UnoCard playTo(UnoHand hand,
		UnoCard topDiscard, UnoCard.Suit wildSuit);
}
