package com.qmx.strutils;

import static com.zl.util.MethodFactory.print;

import java.util.Random;

public class StringUtils {

	/**
	 * 产生指定长度的随机字符串
	 *
	 * @param i
	 * @return String
	 */
	public static String getRandomString(int i) {
		Random randGen = new Random();
		char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();

		if (i < 1)
			return null;
		char ac[] = new char[i];
		for (int j = 0; j < ac.length; j++)
			ac[j] = numbersAndLetters[randGen.nextInt(61)];

		return new String(ac);
	}

	/**
	 * @param idCardNumber
	 *            18位身份证号码
	 * @return
	 */
	private boolean checkIdCard(String idCardNumber) {
		int[] power = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		int[] check = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
		// String sql = "select DQ from idcard where BM="
		// + idCardNumber.substring(0, 6);
		int sum = 0;
		for (int i = 0; i < idCardNumber.length() - 1; i++)
			sum += (Integer.parseInt(idCardNumber.charAt(i) + "") * power[i]);
		int remainder = sum % 11;
		if (Integer.parseInt(idCardNumber.charAt(idCardNumber.length() - 1)
				+ "") == check[remainder])
			return true;
		else
			return false;
	}

	/**
	 * 汉字/英文字符长度判断--JS版与Java方法版
	 *
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 *
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int chineseLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		// String.prototype.lenB = function(){
		// return this.replace(/[^\x00-\xff]/g, "**").length;
		// }
		return valueLength;
	}

	/**
	 * 取字符串的前toCount个字符
	 *
	 * @param str
	 *            被处理字符串
	 * @param toCount
	 *            截取长度
	 * @param more
	 *            后缀字符串
	 * @return String
	 * @throws Exception
	 */
	public static String substring(String str, int toCount, String more)
			throws Exception {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = str.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1))
			reStr += more;
		return reStr;
	}

	/**
	 * 方法一 用Java实现按字节长度截取中英文数字字符串的方法总结
	 *
	 * jdk1.4.2.05
	 *
	 * @author cn
	 * @param s
	 *            要截取的字符串
	 * @param length
	 *            要截取字符串的长度->是字节一个汉字2个字节 return 返回length长度的字符串（含汉字）
	 */
	private static String getTitleToTen(String s, int length) throws Exception {

		String title = "";
		s = s.trim();
		byte[] bytes = s.getBytes("Unicode");
		int n = 0;
		int i = 2;
		int chineseNum = 0;
		int englishNum = 0;
		for (; i < bytes.length && n < length; i++) {
			if (i % 2 == 0) {
				n++;
			} else {
				if (bytes[i] != 0) {
					n++;
					chineseNum++;
				} else {
					englishNum++;
				}
			}
		}
		/*
		 * if (i % 2 == 1){ if (bytes[i - 1] == 0) i = i - 1; else i = i + 1; }
		 */
		// 将截一半的汉字要保留
		if (i % 2 == 1) {
			i = i + 1;
		}
		// 最后一个为非汉字则英文字符加一
		if (bytes[i - 1] == 0) {
			englishNum++;

		} else if (englishNum % 2 != 0) {// 如果英文字符mod 2 ！= 0 代表有奇数个英文字符，所以汉字个数加一
			chineseNum++;
		}
		String eside = ".................................................................";
		String str = new String(bytes, 0, i, "Unicode");
		StringBuffer ssss = new StringBuffer(str);
		ssss.append(eside);
		byte[] byteTitle = ssss.toString().getBytes("Unicode");
		int lll = (length * 4 - 4) - 2 * chineseNum;// length截取字符串字节数（length*2）*（length*2）[length*2]代表参数s,和length转换成bytes[]
													// 的字节数
		title = new String(byteTitle, 0, lll, "Unicode");
		return title;
	}

	/**
	 * 方法一 用Java实现按字节长度截取中英文数字字符串的方法总结
	 *
	 * jdk1.6.0.06
	 *
	 * @author cn
	 * @param s
	 *            要截取的字符串
	 * @param length
	 *            要截取字符串的长度->是字节一个汉字2个字节 return 返回length长度的字符串（含汉字）
	 */
	public static String bSubstring(String s, int length) throws Exception {

		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}

		}
		// 如果i为奇数时，处理成偶数
		/*
		 * if (i % 2 == 1){ // 该UCS2字符是汉字时，去掉这个截一半的汉字 if (bytes[i - 1] != 0) i =
		 * i - 1; // 该UCS2字符是字母或数字，则保留该字符 else i = i + 1; }
		 */
		// 将截一半的汉字要保留
		if (i % 2 == 1) {
			i = i + 1;
		}
		return new String(bytes, 0, i, "Unicode");
	}

	/**
	 * 方法二 用Java实现按字节长度截取中英文数字字符串的方法总结
	 *
	 * 字符串按字节截取
	 *
	 * @param str
	 *            原字符
	 * @param len
	 *            截取长度
	 * @return String
	 * @author kinglong
	 * @since 2006.07.20
	 */
	public static String splitString(String str, int len) {
		return splitString(str, len, ".......");
	}

	/**
	 * 方法二 用Java实现按字节长度截取中英文数字字符串的方法总结
	 *
	 * 字符串按字节截取
	 *
	 * @param str
	 *            原字符
	 * @param len
	 *            截取长度
	 * @param elide
	 *            省略符
	 * @return String
	 * @author kinglong
	 * @since 2006.07.20
	 */
	public static String splitString(String str, int len, String elide) {
		if (str == null) {
			return "";
		}
		byte[] strByte = str.getBytes();
		int strLen = strByte.length;
		// int elideLen = (elide.trim().length() == 0) ? 0 :
		// elide.getBytes().length;
		if (len >= strLen || len < 1) {
			return str;
		}
		/*
		 * if (len - elideLen > 0) { len = len - elideLen; }
		 */
		int count = 0;
		for (int i = 0; i < len; i++) {
			int value = (int) strByte[i];
			if (value < 0) {
				count++;
			}
		}
		if (count % 2 != 0) {
			len = (len == 1) ? len + 1 : len - 1;
		}
		return new String(strByte, 0, len) + elide.trim();
	}

	/**
	 * 人民币小写转大写(截取2位小数位)
	 *
	 * @param num
	 *            1234567.899
	 * @return 壹佰貳拾三萬肆仟伍佰陸拾柒元捌毛玖分
	 */
	public static String toChineseDigit(String num) {
		if (num == null || "".equals(num.trim())) {
			return "";
		}
		String num1[] = { "零", "壹", "貳", "三", "肆", "伍", "陸", "柒", "捌", "玖", };
		String num2[] = { "", "拾", "佰", "仟", "萬", "億", "兆", "吉", "太", "拍", "艾" };

		String tem[] = num.split("\\.");
		String n = tem[0];
		int len = n.length();

		String ret = "";
		//
		if (len <= 5) {
			for (int i = 0; i < len; i++) {
				if (n.charAt(i) == '0') {
					int j = i + 1; // 用来判断0后面一位是不是0
					// 如果0后面一位不是0,则j不会++,则i的值沒有变化(即i=i+1-1)
					// 如果0后面也是0,后面有连续的n个0,则j++ n次,i也++ n次(即i要跳过n位)
					while (j < len && n.charAt(j) == '0')
						++j;
					if (j < len)
						ret += "零";
					i = j - 1;
				} else {
					ret += num1[Integer.parseInt(n.substring(i, i + 1))]
							+ num2[len - i - 1];
				}
			}

		} else if (len <= 8) {
			ret = toChineseDigit(n.substring(0, len - 4)); // 先得到万级的数字
			if (ret.length() != 0)
				ret += num2[4]; // 帶上单位 '萬'
			ret += toChineseDigit(n.substring(len - 4)); // 再得到万级后面的数字
		} else {
			ret = toChineseDigit(n.substring(0, len - 8)); // 先得到亿级的数字
			if (ret.length() != 0)
				ret += num2[5]; // 帶上單位 '亿'
			ret += toChineseDigit(n.substring(len - 8)); // 再得到亿级后面的数字
		}

		//
		if (tem.length > 1) {
			String m = tem[1];
			print(m.length() > 2);
			if (m.length() > 2) {
				m = m.substring(0, 2);
			}
			int mao = Integer.parseInt(m) / 10;
			int fen = Integer.parseInt(m) % 10;

			String mao_s = num1[mao] + "毛";
			String fen_s = num1[fen] + "分";

			if (mao == 0) {
				mao_s = "零";
			}
			if (fen == 0) {
				fen_s = "";
			}
			if (mao == 0 && fen == 0) {
				mao_s = "";
				fen_s = "";
			}
			String dec_str = mao_s + fen_s;
			ret += "元" + dec_str;
		}
		return ret;
	}
}
