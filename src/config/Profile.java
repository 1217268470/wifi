package config;

/**
 * wifi 配置文件
 */
public class Profile {

	/**
	 * wifi 名称
	 */
	public static String WIFI_NAME = "WIFI_NAME";

	/**
	 * wifi 密码
	 */
	public static String WIFI_PASSWORD = "WIFI_PASSWORD";

//<?xml version="1.0"?>
//<WLANProfile xmlns="http://www.microsoft.com/networking/WLAN/profile/v1">
//<name>SSID_NAME</name>
//<SSIDConfig>
//    <SSID>
//        <name>SSID_NAME</name>
//    </SSID>
//</SSIDConfig>
//<connectionType>ESS</connectionType>
//<connectionMode>auto</connectionMode>
//<MSM>
//    <security>
//        <authEncryption>
//            <authentication>AUTH_TYPE</authentication>
//            <encryption>AES</encryption>
//            <useOneX>false</useOneX>
//        </authEncryption>
//        <sharedKey>
//            <keyType>passPhrase</keyType>
//            <protected>false</protected>
//            <keyMaterial>PASSWORD</keyMaterial>
//        </sharedKey>
//    </security>
//</MSM>
//<MacRandomization xmlns="http://www.microsoft.com/networking/WLAN/profile/v3">
//    <enableRandomization>false</enableRandomization>
//</MacRandomization>
//</WLANProfile>

	/**
	 * 配置文件
	 */
	public static final String PROFILE = "<?xml version=\"1.0\"?>\n"
			+ "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\n"
			+ "    <name>WIFI_NAME</name>\n" + "    <SSIDConfig>\n" + "        <SSID>\n" + "            <name>"
			+ WIFI_NAME + "</name>\n" + "        </SSID>\n" + "    </SSIDConfig>\n"
			+ "    <connectionType>ESS</connectionType>\n" + "    <connectionMode>auto</connectionMode>\n"
			+ "    <MSM>\n" + "        <security>\n" + "            <authEncryption>\n"
			+ "                <authentication>WPA2PSK</authentication>\n"
			+ "                <encryption>AES</encryption>\n" + "                <useOneX>false</useOneX>\n"
			+ "            </authEncryption>\n" + "            <sharedKey>\n"
			+ "                <keyType>passPhrase</keyType>\n" + "                <protected>false</protected>\n"
			+ "                <keyMaterial>" + WIFI_PASSWORD + "</keyMaterial>\n" + "            </sharedKey>\n"
			+ "        </security>\n" + "    </MSM>\n"
			+ "    <MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\n"
			+ "        <enableRandomization>false</enableRandomization>\n" + "    </MacRandomization>\n"
			+ "</WLANProfile>";
}
