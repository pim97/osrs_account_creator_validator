package accountCreator;

public class RunescapeAccountGenerator {

	public static RunescapeAccount generateNewAccount() {
		String randomPassword = new PasswordGenerator(15).nextString();
		int randomValueForEmail = (int) (Integer.MAX_VALUE * Math.random());
		int day = (int) (24 * Math.random()) + 1;
		int month = (int) (11 * Math.random()) + 1;
		int year = Util.betweenTwo(1950, 1999);
		ProxyAccount proxy = ProxyManager.getRandomProxyAccount();
		String email = String.format("%s+%s%s", Config.baseProtonEmailStart, randomValueForEmail,
				Config.baseProtonEmailEnding);

		RunescapeAccount account = new RunescapeAccount(randomPassword, "" + day, "" + month, "" + year, email, proxy);
		return account;
	}
}
