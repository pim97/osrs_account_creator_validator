package accountCreator;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
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

import com.didisoft.pgp.PGPException;

public class Protonmail {

	public HttpComponentsClientHttpRequestFactory getFactory() {
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		return factory;
	}

	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/vnd.protonmail.v1+json");
		headers.set("Accept-Encoding", "gzip, deflate, br");
		headers.set("Accept-Language", "nl-NL,nl;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6,ru;q=0.5,bn;q=0.4");
//		headers.set("Content-Length", String.valueOf(postDataBytes.length));
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		headers.set("Cookie", Config.cookieStringFromProtonmail);
		headers.set("Host", "mail.protonmail.com");
		headers.set("Origin", "https://mail.protonmail.com");
		headers.set("Referer", "https://mail.protonmail.com/inbox");
		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.set("x-pm-apiversion", "3");
		headers.set("x-pm-appversion", "Web_3.16.23");
		headers.set("x-pm-uid", "51ca8bebd1d1dfbd83a7c893295df8d10741e6d4");
		headers.set("X-Requested-With", "XMLHttpRequest");

		return headers;
	}

	public String getMessagesFromProtonmail(String email) throws IOException, PGPException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		HttpEntity<MultiValueMap<String, String>> request2 = null;

		ResponseEntity<String> response2 = null;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(getFactory());

		request2 = new HttpEntity<>(map, getHeaders());
//		System.out.println(request2.toString());
		response2 = restTemplate.exchange("https://mail.protonmail.com/api/messages?LabelID=0&Limit=100&Page=0",
				HttpMethod.GET, request2, String.class);

		Document doc = Jsoup.parse(String.valueOf(response2.getBody()));

		JSONObject object = new JSONObject(doc.text());
		JSONArray messages = object.getJSONArray("Messages");

		for (int b = 0; b < messages.length(); b++) {
			JSONObject message = (JSONObject) messages.get(b);

			if (message.has("ToList")) {
				JSONArray emailTo = message.getJSONArray("ToList");
				for (int c = 0; c < emailTo.length(); c++) {
					JSONObject toList = (JSONObject) emailTo.get(c);
					if (toList.has("Address")) {
						String toListEmail = toList.getString("Address");
						if (toListEmail.equalsIgnoreCase(email)) {
							System.out.println("Found to list: " + toListEmail);

							if (message.has("ID")) {
								String id = message.getString("ID");
								System.out.println("Found email id: " + id);
								return getMessageBodyFromProtonmail(id);
							}

						}
					}
				}
			}

		}
		return null;
	}

	public String getMessageBodyFromProtonmail(String messageIdToClickOnEmail) throws PGPException, IOException {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		HttpEntity<MultiValueMap<String, String>> request2 = null;

		ResponseEntity<String> response2 = null;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(getFactory());

		request2 = new HttpEntity<>(map, getHeaders());
//		System.out.println(request2.toString());

		response2 = restTemplate.exchange(
				String.format("https://mail.protonmail.com/api/messages/%s", messageIdToClickOnEmail), HttpMethod.GET,
				request2, String.class);

		Document doc = Jsoup.parse(String.valueOf(response2.getBody()));

		JSONObject object = new JSONObject(doc.text());
		if (object.has("Message")) {
			JSONObject message = (JSONObject) object.get("Message");
			System.out.println(message.toString());
			if (message.has("Body")) {
				String body = (String) message.get("Body");
				System.out.println("Found body: " + body);
				PgbDescrypt pgb = new PgbDescrypt();

				return pgb.decryptPgpMessage(body);
			}
		}

		return null;
	}
}
