import static java.lang.Math.*; // gratuitous test of static import
import static java.lang.System.out; // ditto
import java.util.*;
class TestJSR201 {
    enum Color { red, green, blue ; };
    public static void main(String... args/* varargs */) {
	/* for each on multi-dimensional array */
	int[][] iaa = new int[10][10];
	for (int ia[] : iaa) {
	    for (int i : ia)
		out.print(i); // use static import.
	    out.println();
	}
	/** alternate syntax: */
	for each (int ia[] in iaa)
	    for each (int i in ia) {
		out.println(i);
	    }
	/* */
	for (Color c : Color.VALUES) {
	    switch(c) {
	    case Color.red: out.print("R"); break;
	    case Color.green: out.print("G"); break;
	    case Color.blue: out.print("B"); break;
	    default: assert false;
	    }
	}
	out.println();
    }
    // complex enum declaration, from JSR-201
    public static enum Coin {
	penny(1), nickel(5), dime(10), quarter(25);
	Coin(int value) { this.value = value; }
	private final int value;
	public int value() { return value; }
    }
    public static class Card implements Comparable, java.io.Serializable {
	public enum Rank { deuce, three, four, five, six, seven, eight, nine,
			   ten, jack, queen, king, ace }
	public enum Suit { clubs, diamonds, hearts, spades }

	private final Rank rank;
	private final Suit suit;

	private Card(Rank rank, Suit suit) {
	    if (rank == null || suit == null)
		throw new NullPointerException(rank + ", " + suit);
	    this.rank = rank;
	    this.suit = suit;
	}

	public Rank rank() { return rank; }
	public Suit suit() { return suit; }

	public String toString() { return rank + " of " + suit; }

	public int compareTo(Object o) {
	    Card c = (Card)o;
	    int rankCompare = rank.compareTo(c.rank);
	    return rankCompare != 0 ? rankCompare : suit.compareTo(c.suit);
	}
	
	private static List sortedDeck = new ArrayList(52);
	/* BROKEN IN PROTOTYPE 2.0 */
	static {
	    for (Rank rank : Rank.VALUES)
		for (Suit suit : Suit.VALUES)
		    sortedDeck.add(new Card(rank, suit));
	}
	/* */
	// Returns a shuffled deck
	public static List newDeck() {
	    List result = new ArrayList(sortedDeck);
	    Collections.shuffle(result);
	    return result;
	}
    }
    // sophisticated example:
    public static abstract enum Operation {
	plus {
	    double eval(double x, double y) { return x + y; }
	},
	minus {
	    double eval(double x, double y) { return x - y; }
	},
	times {
	    double eval(double x, double y) { return x * y; }
	},
	divided_by {
	    double eval(double x, double y) { return x / y; }
	};

	// Perform arithmetic operation represented by this constant
	abstract double eval(double x, double y);

	public static void main(String args[]) {
	    double x = Double.parseDouble(args[0]);
	    double y = Double.parseDouble(args[1]);

	    for (Operation op : VALUES)
		out.println(x + " " + op + " " + y + " = " + op.eval(x, y));
	}
    }
}
