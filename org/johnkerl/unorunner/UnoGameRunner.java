package org.johnkerl.unorunner;

// Main entry point.  Runs a game with CSV-printing visitor.
public class UnoGameRunner
{
	// ----------------------------------------------------------------
	private static class UnoGameTurnVisitorImpl
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
	private static class UnoGameSummaryVisitorImpl
		implements UnoGameVisitor
	{
		public void printHeader() {
			StringBuilder sb = new StringBuilder();
			sb.append("nturn,");
			sb.append("nshuf");
			System.out.println(sb.toString());
		}
		public void doStartOfGame(UnoGame game) {
		}
		public void doStartOfTurn(UnoGame game) {
		}

		public void doEndOfTurn(UnoGame game) {
		}

		public void doEndOfGame(UnoGame game) {
			StringBuilder sb = new StringBuilder();
			sb.append(game.getNumTurns()).append(",");
			sb.append(game.getNumShuffles());
			System.out.println(sb.toString());
		}
	}

	// ----------------------------------------------------------------
	public static void main(String[] args) {
		int numHands = 4;
		int numGames = 1;
		if (args.length == 1) {
			numHands = Integer.parseInt(args[0]);
			UnoGame game = new UnoGame(numHands, new SimpleUnoStrategy(),
				new UnoGameTurnVisitorImpl());
			game.play();
		}
		else if (args.length == 2) {
			numHands = Integer.parseInt(args[0]);
			numGames = Integer.parseInt(args[1]);
			UnoGameSummaryVisitorImpl visitor = new UnoGameSummaryVisitorImpl();
			visitor.printHeader();
			for (int i = 0; i < numGames; i++) {
				UnoGame game = new UnoGame(numHands, new SimpleUnoStrategy(),
					visitor);
				game.play();
			}
		}
		else {
			System.err.println("Usage: UnoGameRunner {numHands} [numGames]");
			System.exit(1);
		}
	}
}
