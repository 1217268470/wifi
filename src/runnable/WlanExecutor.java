package runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.Connector;
import config.Command;
import dto.SSID;

/**
 * wlan 命令行执行器
 */
public class WlanExecutor {

	/**
	 * 校验WLAN配置文件是否正确
	 * <p>
	 * 校验步骤为： ---step1 添加配置文件 ---step3 连接wifi ---step3 ping校验
	 */
	public synchronized boolean check(String ssid, String password) {
		System.out.println("check : " + password);
		try {
			String profileName = password + ".xml";
			if (addProfile(profileName)) {
				if (connect(ssid)) {
					Thread.sleep(50);
					if (ping()) {
						return true;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 列出所有信号较好的ssid
	 *
	 * @return 所有ssid
	 */
	public static List<SSID> listSsid() {
		List<SSID> ssidList = new ArrayList<SSID>();
		String cmd = Command.SHOW_NETWORKS;
		List<String> result = execute(cmd, null);
		if (result != null && result.size() > 0) {
			// todo 整合信息
		}
		return ssidList;
	}

	/**
	 * 添加配置文件
	 *
	 * @param profileName 添加配置文件
	 */
	private static boolean addProfile(String profileName) {
		String cmd = Command.ADD_PROFILE.replace("FILE_NAME", profileName);
		List<String> result = execute(cmd, Connector.resource);
		if (result != null && result.size() > 0) {
			if (result.get(0).contains("添加到接口")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 连接wifi
	 *
	 * @param ssid 添加配置文件
	 */
	public static boolean connect(String ssid) {
		boolean connected = false;
		String cmd = Command.CONNECT.replace("SSID_NAME", ssid);
		List<String> result = execute(cmd, null);
		if (result != null && result.size() > 0) {
			if (result.get(0).contains("已成功完成")) {
				connected = true;
			}
		}
		return connected;
	}

	/**
	 * ping 校验
	 */
	private static boolean ping() {
		boolean pinged = false;
		String cmd = "ping " + Connector.PING_DOMAIN;
		List<String> result = execute(cmd, null);
		if (result != null && result.size() > 0) {
			for (String item : result) {
				if (item.contains("来自")) {
					pinged = true;
					break;
				}
			}
		}
		return pinged;
	}

	/**
	 * 执行器
	 *
	 * @param cmd      CMD命令
	 * @param filePath 需要在哪个目录下执行
	 */
//	SSID 1 : OpenWrt
//    Network type	: 结构
//    身份验证			: WPA2 - 个人
//    加密			: CCMP 
//    BSSID 1       : 14:3d:f2:c6:96:05
//    信号              	    : 100%
//    无线电类型               : 802.11n
//    频道               		: 4 
//    基本速率(Mbps)	: 1 2 5.5 11
//    其他速率(Mbps)	: 6 9 12 18 24 36 48 54
//
//	SSID 2 : CU_E2cX
//    Network type	: 结构
//    身份验证                  : WPA2 - 个人
//    加密                    	: CCMP 
//    BSSID 1       : 80:b0:7b:0d:af:0d
//    信号               		: 100%
//    无线电类型         	: 802.11n
//    频道               		: 4 
//    基本速率(Mbps)  : 1 2 5.5 11
//    其他速率(Mbps)  : 6 9 12 18 24 36 48 54

	public static List<String> execute(String cmd, String filePath) {
		Process process = null;
		List<String> result = new ArrayList<String>();
		try {
			if (filePath != null) {
				process = Runtime.getRuntime().exec(cmd, null, new File(filePath));
			} else {
				process = Runtime.getRuntime().exec(cmd);
			}
			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				result.add(line);
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}