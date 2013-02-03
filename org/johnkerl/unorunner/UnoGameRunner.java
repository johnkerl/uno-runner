package org.johnkerl.unorunner;

// Main entry point.  Runs a game with CSV-printing visitor.
public class UnoGameRunner
{
	private static class UnoGameVisitorImpl
		implements UnoGameVisitor
	{
		public void doStartOfGame(UnoGame game) {
			// Header line
			StringBuilder sb = new StringBuilder();
			sb.append("nturn,");
			sb.append("nshuf,");
			int nh = game.getNumHands();
			for (int i = 0; i < nh; i++)
				sb.append("c").append(i).append(",");
			sb.append("ndraw,");
			sb.append("ndeck,");
			sb.append("ndis,");
			sb.append("card");
			System.out.println(sb.toString());

			// Zeroth-turn, start-of-game data line.
			sb = new StringBuilder();
			sb.append(game.getNumTurns()).append(",");
			sb.append(game.getNumShuffles()).append(",");
			for (int i = 0; i < nh; i++) {
				sb.append(game.getHandSize(i)).append(",");
			}
			sb.append(game.getLastNumDraws()).append(",");
			sb.append(game.getDeckSize()).append(",");
			sb.append(game.getDiscardSize()).append(",");
			sb.append("_");
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
			sb.append(game.getNumShuffles()).append(",");
			int nh = game.getNumHands();
			int lpi = game.getLastPlayerIndex();
			for (int i = 0; i < nh; i++) {
				if (i == lpi)
					sb.append(game.getHandSize(i)).append(",");
				else
					sb.append("_").append(",");
			}
			sb.append(game.getLastNumDraws()).append(",");
			sb.append(game.getDeckSize()).append(",");
			sb.append(game.getDiscardSize()).append(",");
			UnoCard lastCardPlayed = game.getLastCardPlayed();
			sb.append((lastCardPlayed == null ? "_" : lastCardPlayed.toString()));
			System.out.println(sb.toString());
		}

		public void doEndOfGame(UnoGame game) {
		}
	}

	// ----------------------------------------------------------------
	public static void main(String[] args) {
		int numHands = 4;
		if (args.length == 1)
			numHands = Integer.parseInt(args[0]);
		UnoGame game = new UnoGame(numHands, new SimpleUnoStrategy(),
			new UnoGameVisitorImpl());
		game.play();
	}
}
