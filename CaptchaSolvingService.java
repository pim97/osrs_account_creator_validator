package accountCreator;

import java.io.IOException;

import org.openqa.selenium.Proxy.ProxyType;

import com.twocaptcha.http.HttpWrapper;

public class CaptchaSolvingService {

	private String googleKey;
	private String pageUrl;
	private String proxyIp;
	private String proxyPort;
	private String proxyUser;
	private String proxyPw;
	private ProxyType proxyType;
	private HttpWrapper hw = new HttpWrapper();
	private String userAgent;

	public String solveCaptchaRecaptchaV2Ru() throws InterruptedException, IOException {
		// 35% //%65
		String ip = getAvailableMachineAddress();

		System.out.println("Sending recaptcha challenge to 2captcha.com");
		String parameters = "key=" + this.getApiKey() + "&method=userrecaptcha" + "&googlekey=" + this.googleKey
				+ "&pageurl=" + this.pageUrl + "&soft_id=2134592";

		System.out.println("params: " + parameters);

		this.hw.get("http://" + ip + "/in.php?" + parameters);
		String captchaId = this.hw.getHtml().replaceAll("\\D", "");
		int timeCounter = 0;
		System.out.println(this.hw.getHtml() + " " + getPageUrl());

		try {
			do {
				this.hw.get("http://" + ip + "/res.php?key=" + this.getApiKey() + "&action=get" + "&id=" + captchaId);
				Thread.sleep(1000L);
				++timeCounter;
				System.out.println("Waiting for captcha to be solved");
			} while (this.hw.getHtml().contains("NOT_READY") && timeCounter < 300);

			if (timeCounter >= 300) {
				return "ERROR_TOOK_TOO_LONG";
			}
		} catch (Exception e) {
			System.out.println("ERROR WHILE SOLVING CAPTCHA");
			return "ERROR_SOLVING_CAPTCHA";
		}

		System.out.println(this.hw.getHtml() + " " + getPageUrl());

		System.out.println("It took " + timeCounter + " seconds to solve the captcha");
		String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
		return gRecaptchaResponse;
	}

	private String getAvailableMachineAddress() throws InterruptedException {
		return Config.captchaSolverUrl;
	}

	public String getGoogleKey() {
		return this.googleKey;
	}

	public void setGoogleKey(String googleKey) {
		this.googleKey = googleKey;
	}

	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getProxyIp() {
		return this.proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getProxyPort() {
		return this.proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return this.proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPw() {
		return this.proxyPw;
	}

	public void setProxyPw(String proxyPw) {
		this.proxyPw = proxyPw;
	}

	public ProxyType getProxyType() {
		return this.proxyType;
	}

	public void setProxyType(ProxyType proxyType) {
		this.proxyType = proxyType;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getApiKey() {
		return Config.captchaApiKey;
	}

}
