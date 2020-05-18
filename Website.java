package accountCreator;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Website {

	public HttpComponentsClientHttpRequestFactory getFactory(ProxyAccount proxy) {
//		ProxyAccount proxy = account.getProxyForAccount();
		System.out.println(proxy.toString());

		final int proxyPortNum = Integer.parseInt(proxy.getPort());
		final CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(proxy.getIp(), proxyPortNum),
				new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));
		final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.useSystemProperties();
		clientBuilder.setProxy(new HttpHost(proxy.getIp(), proxyPortNum));
		clientBuilder.setDefaultCredentialsProvider(credsProvider);
		clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
		final CloseableHttpClient client = clientBuilder.build();
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setHttpClient(client);
		return factory;
	}

	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		headers.set("Accept-Encoding", "gzip, deflate, br");
		headers.set("Accept-Language", "nl-NL,nl;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6,ru;q=0.5,bn;q=0.4");
//		headers.set("Content-Length", String.valueOf(postDataBytes.length));
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		headers.set("Cookie",
				"slangpref=0; _vwo_uuid=B1EF8DEB9407287BAB3ABEC749BC6337; _ga=GA1.2.1307758959.1503485838; jagex_accept_cookies=true; JXDOB=19970626; serverlist_order=mpLWA; settings=wwGlrZHF5gKN6D3mDdihco3oPeYN2KFybL9hUUFqOvk; _vwo_uuid_v2=D20E38DBE4691939DC9DE2DD5DFD2B7A1|4365472ff4f92d51f65a0d10d1d50b3d; rs_langpref=de; global__user-visited=true; _gcl_au=1.1.686992308.1587216303; JXTRACKING=0152B025C40000017223F628D6; JXWEBUID=05CBFEED92D4E16E28D17D04E5993E485C711B12AB9E2EF4EDE230341C50E0098030A38F5F54707AF074FE1D69E8D288; JXFLOWCONTROL=167122867441854436");

		headers.set("Host", "secure.runescape.com");
		headers.set("Origin", "https://secure.runescape.com");
		headers.set("Referer", "https://secure.runescape.com/m=account-creation/l=1/create_account?theme=oldschool");
		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

		return headers;
	}

	public RunescapeAccount sendPostToCreateOnOldschoolPage() throws IOException {

		// Captcha Service
		CaptchaSolvingService service = new CaptchaSolvingService();
		service.setGoogleKey("6Lcsv3oUAAAAAGFhlKrkRb029OHio098bbeyi_Hv");
		service.setPageUrl("https://secure.runescape.com/m=account-creation/l=1/create_account");
//		String captchaResponse = "03AGdBq24eJf_8xTVRf5JqWC1bEi4vBtxBnEZ4yixKdV262yRK7_1vfYoMLEotAM8tgXPwWvUJbPbYqY4UEoBsqltxjF0OoV8qgZIA0h-7Wj2phH8baKNgZauJvvAhphTaWQw40x52fph7m--d0w34Bqhohpd1udDyrrze06u1TdXFRpoWh2SS93ExiwrehCTPzq-9s-j022XhW2OyerG1XWaVMeZERb8bln0Oe2YO1az-v7gJfa7CxfINZbp-zeYtIXEf1RvKM1OE0WexRcJt0s8VfiukCYOfj1Xgp6S4TJp0ZSBeCNxK173XZdAHsdzFLBRCspCC8J9dXN9Qym4z5s3VT4m0-JGtFs3JGHcBzOornv6KRpXh7754t7TId2Ij4yzv0N3d1cMy";
		String captchaResponse = new CaptchaSolver().solveActualCaptchaRecaptchaV2(service);

		RunescapeAccount account = RunescapeAccountGenerator.generateNewAccount();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		// Preparing data for body
		map.add("theme", "oldschool");
//		map.add("theme", "oldschool");
		map.add("email1", account.getEmail());
		map.add("onlyOneEmail", "" + 1);
		map.add("password1", account.getPassword());
		map.add("onlyOnePassword", "" + 1);
		map.add("day", account.getDay());
		map.add("month", account.getMonth());
		map.add("year", account.getYear());
		map.add("create-submit", "create");
		map.add("g-recaptcha-response", captchaResponse);

		HttpEntity<MultiValueMap<String, String>> request2 = null;

		ResponseEntity<String> response2 = null;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(getFactory(account.getProxyForAccount()));

		request2 = new HttpEntity<>(map, getHeaders());
		System.out.println(request2.toString());
		response2 = restTemplate.postForEntity("https://secure.runescape.com/m=account-creation/l=1/create_account",
				request2, String.class);

		Document doc = Jsoup.parse(String.valueOf(response2.getBody()));

		System.out.println("Found document");
		System.out.println(doc.toString());

		return account;
	}

	public int visitWebsiteWithLinkFromEmail(ProxyAccount proxy, String validationLink) throws IOException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		HttpEntity<MultiValueMap<String, String>> request2 = null;

		ResponseEntity<String> response2 = null;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(getFactory(proxy));

		request2 = new HttpEntity<>(map, getHeaders());
		response2 = restTemplate.exchange(validationLink, HttpMethod.GET, request2, String.class);

		return response2.getStatusCodeValue();
	}
}
