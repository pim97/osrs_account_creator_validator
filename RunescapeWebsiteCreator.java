package accountCreator;

import java.io.IOException;

import com.didisoft.pgp.PGPException;

public class RunescapeWebsiteCreator {

	public static void main(String[] args) throws PGPException, IOException, InterruptedException {
		Website website = new Website();
		RunescapeAccount account = null;
		try {
			account = website.sendPostToCreateOnOldschoolPage();
			System.out.println(String.format("Creating account - %s", account.getEmail()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String linkToValidate = null;
		while (linkToValidate == null) {
			Protonmail mail = new Protonmail();
			try {
				linkToValidate = mail.getMessagesFromProtonmail(account.getEmail());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Waiting for e-mail to arrive...");
			Thread.sleep(1500);
		}
		System.out.println(String.format("Email arrived - validating account %s", account.getEmail()));

		int statusCode = website.visitWebsiteWithLinkFromEmail(account.getProxyForAccount(), linkToValidate);

		System.out.println(String.format("Account validated - %s", account.getEmail()));

		SavePlayers save = new SavePlayers();
		save.save(account);

		System.out.println(String.format("Saved account - %s", account.getEmail()));

		if (statusCode == 200) {
			System.out.println(String.format("Successfully created account - %s", account.getEmail()));
		}
	}
}
