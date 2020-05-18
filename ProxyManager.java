package accountCreator;

public class ProxyManager {

	public static ProxyAccount getRandomProxyAccount() {
		int randomValue = (int) (Math.random() * Config.proxies.size() - 1);
		return Config.proxies.get(randomValue);
	}
}
