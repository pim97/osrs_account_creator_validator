package accountCreator;

import java.util.ArrayList;

public class Config {

	/**
	 * Proxies
	 */
	public static ArrayList<ProxyAccount> proxies = new ArrayList<ProxyAccount>();

	static {
		proxies.add(new ProxyAccount("ip", "port"));
	}

	/**
	 * Protonmail Configuration
	 */
	public static String baseProtonEmailStart = "accountgenerator101";
	public static String baseProtonEmailEnding = "@protonmail.ch";
	public static String cookieStringFromProtonmail = "cookieExample";
	
	/**
	 * Protonmail PGP
	 */
	public static String privateKeyFileFromProtonmailPgpLocation = "C:/Users/name/Documents/privatekey.protonmailac.com.asc";
	public static String privateKeyPassword = "password";
	
	/**
	 * Captcha Configuration
	 */
	public static String captchaSolverUrl = "2captcha.com";
	public static String captchaApiKey = "key";

}
