package org.johnkerl.unorunner;

public interface UnoGameVisitor {
	public void doStartOfGame(UnoGame game);
	public void doStartOfTurn(UnoGame game);
	public void doDraw(UnoGame game);
	public void doCardPlayed(UnoGame game,UnoCard c);
	public void doEndOfTurn(UnoGame game);
	public void doEndOfGame(UnoGame game);
}
