// http://www.javabeat.net/2008/10/junit-4-0-example/
package org.johnkerl.unorunner;
import org.junit.*;
import static org.junit.Assert.*;

public class SimpleUnoStrategyTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Before // before each test
	public void setUp() throws Exception {
	}

	@Test
	public void testValueComparator() {
		SimpleUnoStrategy s = new SimpleUnoStrategy();
		UnoCard c1;
		UnoCard c2;
		SimpleUnoStrategy.ValueComparator cmp
			= new SimpleUnoStrategy.ValueComparator();

		// Cards: red/green/blue/yellow 0-9,skip,reverse,draw-two; wild bare,draw-four.

		c1 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.ONE);
		c2 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.TWO);
		assertEquals(cmp.compare(c1, c2), 0);

		c1 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.DRAW_TWO);
		c2 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.ONE);
		assertEquals(cmp.compare(c1, c2),  1);
		assertEquals(cmp.compare(c2, c1), -1);

		c1 = new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.DRAW_FOUR);
		c2 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.DRAW_TWO);
		assertEquals(cmp.compare(c1, c2),  1);
		assertEquals(cmp.compare(c2, c1), -1);

		c1 = new UnoCard(UnoCard.Suit.RED, UnoCard.Rank.REVERSE);
		c2 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.SKIP);
		assertEquals(cmp.compare(c1, c2),  1);
		assertEquals(cmp.compare(c2, c1), -1);

		c1 = new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.DRAW_FOUR);
		c2 = new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.BARE);
		assertEquals(cmp.compare(c1, c2),  1);
		assertEquals(cmp.compare(c2, c1), -1);
	}

	@After // after each test
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
}
