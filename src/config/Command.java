package config;

/**
 * windows下常用命令
 */
public class Command {

	/**
	 * 列出配置文件
	 * 
	 * @cmd netsh wlan show profile
	 */
	public static final String SHOW_PROFILE = "netsh wlan show profile";

	/**
	 * 导出配置文件
	 * 
	 * @cmd netsh wlan export profile key=clear
	 */
	public static final String EXPORT_PRIFILE = "netsh wlan export profile key=clear";

	/**
	 * 删除配置文件
	 * 
	 * @cmd netsh wlan delete profile name=FILE_NAME
	 */
	public static final String DELETE_PROFILE = "netsh wlan delete profile name=FILE_NAME";

	/**
	 * 添加配置文件
	 * 
	 * @cmd netsh wlan add profile filename=FILE_NAME
	 */
	public static final String ADD_PROFILE = "netsh wlan add profile filename=FILE_NAME";

	/**
	 * 列出接口
	 * 
	 * @cmd netsh wlan show interface
	 */
	public static final String SHOW_INTERFACE = "netsh wlan show interface";

	/**
	 * 开启接口
	 * 
	 * @cmd netsh interface set interface "Interface Name" enabled
	 */
	public static final String INTERFACEC_ENABLE = "netsh interface set interface \"Interface Name\" enabled";

	/**
	 * 列出所有可用wifi
	 * 
	 * @cmd netsh wlan show networks mode=bssid
	 */
	public static final String SHOW_NETWORKS = "netsh wlan show networks mode=bssid";
	/**
	 * 连接wifi
	 * 
	 * @cmd netsh wlan connect name=SSID_NAME
	 */
	public static final String CONNECT = "netsh wlan connect name=SSID_NAME";
}
