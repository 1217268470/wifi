package runnable;

import java.util.concurrent.Callable;

public class CheckTask implements Callable<Boolean> {

	private String ssid;
	private String password;

	public CheckTask(String ssid, String password) {
		this.ssid = ssid;
		this.password = password;
	}

	@Override
	public Boolean call() throws Exception {
		WlanExecutor execute = new WlanExecutor();
		boolean checked = execute.check(ssid, password);
		return checked;
	}
}
