package runnable;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import app.Connector;
import util.FileUtils;

/**
 * 配置文件生成器
 */
public class ProfileGenerator {

	private String ssid = null;
	private String dictionary = null;
	private ThreadPoolExecutor executor;
	private int core;

	public ProfileGenerator(String ssid, String dictionary) {
		this.ssid = ssid;
		this.dictionary = dictionary;

		core = Runtime.getRuntime().availableProcessors();
		executor = new ThreadPoolExecutor(core * 10, core * 11, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		executor.allowCoreThreadTimeOut(true);
	}

	/**
	 * 生成配置文件
	 */
	public void genProfile() {
		List<String> passwordList = null;
		int counter = 0;
		outer: while (true) {
			int start = counter * Connector.BATH_SIZE;
			int end = (counter + 1) * Connector.BATH_SIZE - 1;
			passwordList = FileUtils.readLine(dictionary, start, end);
			if (passwordList != null && passwordList.size() > 0) {
				// 生成配置文件
				for (String password : passwordList) {
					GenThread genThread = new GenThread(ssid, password);
					executor.execute(genThread);
				}
			} else {
				break outer;
			}
			counter++;
		}
	}
}