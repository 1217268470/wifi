package runnable;

import app.Connector;
import config.Profile;
import util.FileUtils;

public class GenThread implements Runnable {

	private String ssid = null;
	private String password = null;

//	private ConcurrentLinkedQueue<> remain = = new ConcurrentLinkedQueue<String>();
	public GenThread(String ssid, String password) {
		this.ssid = ssid;
		this.password = password;
	}

	@Override
	public void run() {
		String profileContent = Profile.PROFILE.replace(Profile.WIFI_NAME, ssid);
		profileContent = profileContent.replace(Profile.WIFI_PASSWORD, password);
		FileUtils.writeToFile(Connector.resource + "\\" + ssid + ".xml", profileContent);
	}
}