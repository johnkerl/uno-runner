package org.johnkerl.unorunner;

// Main entry point.  Runs a game with CSV-printing visitor.
public class UnoGameRunner
{
	private static class UnoGameVisitorImpl
		implements UnoGameVisitor
	{
		public void doStartOfGame(UnoGame game) {
			StringBuilder sb = new StringBuilder();
			sb.append("nt,");
			int nh = game.getNumHands();
			for (int i = 0; i < nh; i++)
				sb.append("c").append(i).append(",");
			sb.append("ndck,");
			sb.append("ndis");
			System.out.println(sb.toString());
		}
		public void doStartOfTurn(UnoGame game) {
		}
		public void doDraw(UnoGame game) {
		}
		public void doCardPlayed(UnoGame game, UnoCard c) {
		}

		public void doEndOfTurn(UnoGame game) {
			StringBuilder sb = new StringBuilder();
			sb.append(game.getNumTurns()).append(",");
			int nh = game.getNumHands();
			for (int i = 0; i < nh; i++) {
				sb.append(game.getHandSize(i)).append(",");
			}
			sb.append(game.getDeckSize()).append(",");
			sb.append(game.getDiscardSize());
			System.out.println(sb.toString());
		}

		public void doEndOfGame(UnoGame game) {
		}
	}

	// ----------------------------------------------------------------
	// Dev entry point, aside from unit-test.
	public static void main(String[] args) {
		int numHands = 4;
		if (args.length == 1)
			numHands = Integer.parseInt(args[0]);
		UnoGame game = new UnoGame(numHands, new SimpleUnoStrategy(),
			new UnoGameVisitorImpl());
		game.play();
	}
}
