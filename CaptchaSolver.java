package accountCreator;

import java.io.IOException;

public class CaptchaSolver {

	public String solveActualCaptchaRecaptchaV2(CaptchaSolvingService service) {
		String responseToken = "";
		int attempts = 0;

		try {

			while (responseToken.length() <= 200 || responseToken.contains("ERROR")) {
				attempts++;
				System.out.println(String.format("Attempts solving captcha... %s", attempts));
				responseToken = service.solveCaptchaRecaptchaV2Ru();
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseToken;

	}

}
