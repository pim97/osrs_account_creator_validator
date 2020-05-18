package accountCreator;

public class RunescapeAccount {

	public RunescapeAccount(String password, String day, String month, String year, String email,
			ProxyAccount proxyForAccount) {
		super();
		this.password = password;
		this.day = day;
		this.month = month;
		this.year = year;
		this.email = email;
		this.proxyForAccount = proxyForAccount;
	}

	private String password, day, month, year, email;

	private ProxyAccount proxyForAccount;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ProxyAccount getProxyForAccount() {
		return proxyForAccount;
	}

	public void setProxyForAccount(ProxyAccount proxyForAccount) {
		this.proxyForAccount = proxyForAccount;
	}

}
