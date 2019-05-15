package com.qinhan.videoblog.web.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <h4>���ڹ�����</h4>
 * <ul>
 * <li>��ʽ������ / ��������</li>
 * <li>���ڼ��㣬���ռ��㣬���¼���</li>
 * <li>�����ж�</li>
 * </ul>
 * 
 * @author Administrator
 *
 */
public class DateUtil {

	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * �� yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * �� yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 * @return
	 */
	public static String formatLong(Date date) {
		return format(date, "yyyyMMddHHmmssSSS");
	}

	public static Date parse(String dateStr, String pattern) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("���ڽ�������", e);
		}
		return date;
	}

	/**
	 * �� yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * �� yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseLong(String dateStr) {
		return  parse(dateStr,"yyyyMMddHHmmssSSS");
	}

	public static Date plusDay(Date date, int days) {
		Calendar cal =Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static boolean before(Date d1, Date d2) {
		return d1.compareTo(d2)<0;
	}

	public static boolean after(Date d1, Date d2) {
		return before(d2, d1);
	}

}
