package accountCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SavePlayers {

	public static void main(String[] args) throws IOException {
//		String password, String day, String month, String year, String email,
//		ProxyAccount proxyForAccount
		SavePlayers p = new SavePlayers();
		p.save(new RunescapeAccount("abc", "1", "12", "1992", "test@test.nl", new ProxyAccount("test", "test")));
	}

	public void save(RunescapeAccount account) throws IOException {
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String ebayPath = jarDir.getAbsolutePath() + "/accounts.txt";

		File ebayFile = new File(ebayPath);

		System.out.println(ebayFile.getPath());
		if (!ebayFile.exists()) {
			ebayFile.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(ebayFile, true));
		writer.append(String.format("%s:%s:%s:%s", account.getProxyForAccount().getIp(),
				account.getProxyForAccount().getPort(), account.getEmail(), account.getPassword()));
		writer.append('\n');
		writer.close();
	}
}
