package accountCreator;

public class ProxyAccount {

	private String ip, port, username = "", password = "";

	public ProxyAccount(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public ProxyAccount(String ip, String port, String username, String password) {
		super();
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "ProxyAccount [ip=" + ip + ", port=" + port + ", username=" + username + ", password=" + password + "]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
