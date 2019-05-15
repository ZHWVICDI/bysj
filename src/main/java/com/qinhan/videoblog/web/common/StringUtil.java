package com.qinhan.videoblog.web.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isNotBlank(String src) {
		return src != null && src.length() > 0 && src.trim().length() > 0;
	}

	public static boolean inRange(String src, int min, int max) {
		return src.length() >= min && src.length() <= max;
	}

	public static boolean isNumeric(String src) {
		return src.matches("^\\d+\\.{0,1}\\d+$");
	}

	/**
	 * 将多个字符串转换为一个,号分隔的字符串 a b c-> a,b,c
	 * 
	 * @param strings
	 * @return
	 */
	public static String formatArrString(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 将,分隔的字符串 a,b,c->a b c
	 * 
	 * @param longstr
	 * @return
	 */
	public static String[] parseArrString(String longstr) {
		String[] strArrs = longstr.split(",");
		return strArrs;
	}

	/**
	 * 将00:50:51.24的信息转换为 00:50:51的格式
	 * 
	 * @param timestr
	 * @return
	 */
	public static String parseTimeString(String timestr) {
		if (timestr != null || timestr.length() > 0) {
			System.out.println(timestr);
			return timestr.substring(0, timestr.lastIndexOf('.'));
		}
		return "";
	}

	public static void main(String[] args) {
		String a = "a";
		String b = "b";
		System.out.println(formatArrString(a, b));
		String c = "a,b";
		String[] arr = parseArrString(c);
		for (String as : arr) {
			System.out.println(as);
		}
	}
	//邮箱验证
	public static boolean isValidEmail(String email) {
        // 1、\\w+表示@之前至少要输入一个匹配字母或数字或下划线 \\w 单词字符：[a-zA-Z_0-9]
        // 2、(\\w+\\.)表示域名. 如新浪邮箱域名是sina.com.cn
        // {1,3}表示可以出现一次或两次或者三次.
        String reg = "\\w+@(\\w+\\.){1,3}\\w+";
        Pattern pattern = Pattern.compile(reg);
        boolean flag = false;
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            flag = matcher.matches();
        }
        return flag;
    }
 } 


