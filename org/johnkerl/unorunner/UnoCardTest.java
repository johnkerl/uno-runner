// http://www.javabeat.net/2008/10/junit-4-0-example/
package org.johnkerl.unorunner;
import org.junit.*;
import static org.junit.Assert.*;

public class UnoCardTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Before // before each test
	public void setUp() throws Exception {
	}

	@Test
	public void testPlayableTo() {
		UnoCard c1;
		UnoCard c2;
		UnoCard.Suit wildSuit = null;

		c1 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.ONE);
		c2 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.TWO);
		assertTrue(c1.playableOn(c2, null));
		assertTrue(c2.playableOn(c1, null));

		c1 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.ONE);
		c2 = new UnoCard(UnoCard.Suit.BLUE,  UnoCard.Rank.ONE);
		assertTrue(c1.playableOn(c2, null));
		assertTrue(c2.playableOn(c1, null));

		c1 = new UnoCard(UnoCard.Suit.GREEN, UnoCard.Rank.ONE);
		c2 = new UnoCard(UnoCard.Suit.BLUE,  UnoCard.Rank.TWO);
		assertFalse(c1.playableOn(c2, null));
		assertFalse(c2.playableOn(c1, null));

		c1 = new UnoCard(UnoCard.Suit.WILD, UnoCard.Rank.BARE);
		c2 = new UnoCard(UnoCard.Suit.BLUE,  UnoCard.Rank.TWO);
		assertTrue(c1.playableOn(c2, null));
		assertFalse(c2.playableOn(c1, UnoCard.Suit.RED));
		assertTrue(c2.playableOn(c1, UnoCard.Suit.BLUE));

		// xxx to do:  more cases.
	}

	@After // after each test
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
}
