package accountCreator;

public class Util {

	public static int betweenTwo(int upper, int lower) {
		int r = (int) (Math.random() * (upper - lower)) + lower;
		return r;
	}
}
