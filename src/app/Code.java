package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Code {

	// 密码可能会包含的字符集合
	private static char[] fullCharSource = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	// { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd',
	// 'e','f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
	// 't', 'u', 'v', 'w', 'x', 'y', 'z'};
//			,'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
//			'V', 'W', 'X', 'Y', 'Z'};
//	, '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=',
//			'{', '}', '|', '\\', ':', ';', '"', '\'', '<', '>', '?', ',', '.', '/' };
	// 将可能的密码集合长度
	private static int fullCharLength = fullCharSource.length;

	// maxLength：生成的字符串的最大长度
	public static void generate(int len) throws FileNotFoundException, UnsupportedEncodingException {
		// 计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
		int counter = 0;
		StringBuilder buider = new StringBuilder();

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(Connector.resource + "resources/" + "gencode.txt"), "utf-8"));

		while (buider.toString().length() <= len) {
			buider = new StringBuilder(len * 2);
			int index = counter;
			// 10进制转换成26进制
			while (index >= fullCharLength) {
				// 获得低位
				buider.insert(0, fullCharSource[index % fullCharLength]);
				index = index / fullCharLength;
				// 处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
				index--;
			}
			// 最高位
			buider.insert(0, fullCharSource[index]);
			counter++;

			pw.write(buider.toString() + "\n");
//			System.out.println(buider.toString());
		}
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//		System.out.print("生成的字典位置：D://密码字典.txt" + "\n" + "请输入你需要生成的字典位数：");
//		Scanner sc = new Scanner(System.in);
//		int x = sc.nextInt();

		generate(4);

	}
}
