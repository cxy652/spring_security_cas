package com.egeinfo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilString {

	private static final Log log = LogFactory.getLog(UtilString.class);

	public static boolean isDigital(String str) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isEmpty(String str) {
		if (null == str || "null".equals(str.trim()) || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转化成带 引号的字符串,结果形式: 如: "'a','b','c'"
	 * 
	 * @param set
	 * @return
	 */
	public static String convertToWithQuotationMarksStr(Set<String> set) {
		String result = "";
		if (set != null && set.size() > 0) {
			String[] strArr = set.toArray(new String[0]);
			boolean flag = false;
			for (int i = 0; i < strArr.length; i++) {
				if (flag) {
					result += ",";
				}
				result += "'" + strArr[i] + "'";
				if (i == 0) {
					flag = true;
				}
			}
		}
		return result;
	}

	/**
	 * 转化成带 引号的字符串,结果形式: 如: "'a','b','c'"
	 * 
	 * @param list
	 * @return
	 */
	public static String convertToWithQuotationMarksStr(List<String> list) {
		String result = "";
		if (list != null && list.size() > 0) {
			String[] strArr = list.toArray(new String[0]);
			boolean flag = false;
			for (int i = 0; i < strArr.length; i++) {
				if (flag) {
					result += ",";
				}
				result += "'" + strArr[i] + "'";
				if (i == 0) {
					flag = true;
				}
			}
		}
		return result;
	}

	/**
	 * 用 ","连接成字符串, 最后，形如"1,2,3"
	 * 
	 * @param list
	 * @return
	 */
	public static String connectWithComma(List<String> list) {
		String result = "";
		if (list != null && list.size() > 0) {
			boolean flag = false;
			for (int i = 0; i < list.size(); i++) {
				if (flag) {
					result += ",";
				}
				result += list.get(i);
				if (i == 0) {
					flag = true;
				}
			}
		}
		return result;
	}

	public static String strNull(String str, String rpt) {
		if (isEmpty(str)) {
			return rpt;
		} else {
			return str.trim();
		}
	}

	public static String strNull(String str) {
		return strNull(str, "");
	}

	/**
	 * 判断target数组是source数组的子集<br>
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean isSubArray(String[] source, String[] target) {
		if (target.length == 0) {
			return true;
		}
		if (source.length == 0) {
			return false;
		}
		Arrays.sort(source);
		Arrays.sort(target);
		for (int i = 0; i < target.length; i++) {
			boolean found = false;
			for (int j = 0; j < source.length; j++) {
				if (target[i].equals(source[j])) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	// 去右格
	public static String rightTrim(String str) {
		if (str.trim().equals("")) {
			return "";
		}
		int len = str.length();
		int st = 0;
		char[] val = str.toCharArray();
		while (val[len - 1] <= ' ') {
			len--;
		}
		return str.substring(st, len);
	}

	public static String[] split(String str) {
		return split(str, ",");
	}

	public static String[] split(String str, String sgn) {
		String[] returnValue = null;
		if (!isEmpty(str)) {
			Vector<String> vectors = new Vector<String>();
			int i = str.indexOf(sgn);
			String tempStr = "";
			for (; i >= 0; i = str.indexOf(sgn)) {
				tempStr = str.substring(0, i);
				str = str.substring(i + 1);
				vectors.addElement(tempStr);
			}
			if (!str.equals("")) {
				vectors.addElement(str);
			}
			int size = vectors.size();
			returnValue = new String[size];
			for (i = 0; i < size; i++) {
				returnValue[i] = (String) vectors.get(i);
				returnValue[i] = returnValue[i].trim();
			}
		}
		return returnValue;
	}


	public static String trimLeft(String value) {
		if (value == null)
			return "";
		String result = value;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}


	public static String trimRight(String value) {
		if (value == null)
			return "";
		String result = value;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/***************************************************************************
	 * 功 能：字符串全局替换函数 入口参数：param strSource 待替换的字符串，param strFrom 源字符串，param strTo
	 * 目的字符串 出口参数： 返 回：替换后的字符串
	 **************************************************************************/
	public static String replaceStrAll(String strSource, String strFrom,
			String strTo) {
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strSource = strSource.substring(0, intPos) + strTo
					+ strSource.substring(intPos + intFromLen);
		}
		return strSource;
	}

	public static String subString(String source, int beginIndex, int endIndex) {
		if (source == null) {
			return null;
		}
		int len = source.length();
		endIndex = len > endIndex ? endIndex : len;
		return source.substring(beginIndex, endIndex);
	}

	/**
	 * 函数名称：toHtml <br>
	 * 函数功能：把数据库的内容转化为相应的html语言<br>
	 * 
	 * @param str
	 *            待转换的字符串
	 * @return 转换後的字串
	 */
	public static String toHtml(String str) {
		if (str == null) {
			return "&nbsp;";
		}
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char chr = str.charAt(i);
			switch (chr) {
			case '<':
				out.append("&lt;");
				break;
			case '>':
				out.append("&gt;");
				break;
			case '"':
				out.append("&quot;");
				break;
			case '&':
				out.append("&amp;");
				break;
			case ' ':
				out.append("&nbsp;");
				break;

			default:
				out.append(chr);
			}
		}
		String chrs = out.toString();
		chrs = chrs.replaceAll("\\\\", "\\\\\\\\");
		chrs = chrs.replaceAll("\r\n", "<br>");
		chrs = chrs.replaceAll("\n", "<br>");
		chrs = chrs.replaceAll("'", "\\\\'");
		chrs = chrs.replaceAll("\"", "\\\\\"");
		return chrs;
	}

	/**
	 * 计算字符串长度<br>
	 * 单个字符ascii码算2个长度,其他的算2个长度<br>
	 * 
	 * @param str
	 * @return
	 */
	// public int calcStringDBLen(String str){
	// String asciiRegex = "";
	// return 0;
	// }
	public static boolean isExist(String str, String substr) {
		return isExist(str, substr, ",");
	}

	public static boolean isExist(String str, String substr, String sepatator) {
		if (str == null || str.trim().equals(""))
			return false;
		if (substr == null || substr.trim().equals(""))
			return false;
		String[] strArr = str.split(sepatator);
		int size = strArr.length;
		for (int i = 0; i < size; i++) {
			if (strArr[i].equals(substr))
				return true;
		}
		return false;
	}

	/**
	 * 按字节按长度截取字符。
	 * 
	 * @param src
	 *            含中文的字符串。
	 * @param len
	 *            截取的字符长度。
	 * @return
	 */
	public static String subStringByByteForZh(String src, int len) {

		if (src == null || len <= 0) {
			return "";
		}
		String result = null;
		String charset = "GBK";
		try {
			byte[] bs = src.getBytes(charset);
			if (bs.length <= len) {
				result = src;
			} else {
				byte[] bstmp = new byte[len];
				System.arraycopy(bs, 0, bstmp, 0, len);
				result = new String(bstmp, charset);
				// 好像有时最后为半个中文字符，重新构造的字符串会为空。？
				if (result.length() == 0) {
					if (len == 1) {
						result = "";
					} else {
						bstmp = new byte[len - 1];
						System.arraycopy(bs, 0, bstmp, 0, len - 1);
						result = new String(bstmp, charset);
					}
				}
			}
		} catch (Throwable t) {
			log.error("", t);
		}
		return result;

	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String str = "abc你好，a";
		// String str = "你";
		// System.out.println(substringByByteForZh(str, 4, 7));
		// System.out.println(subStringByByteForZh(str, 1));
		// System.out.println(subStringByByteForZh(str, 2));
		// System.out.println(subStringByByteForZh(str, 4));
		// System.out.println(subStringByByteForZh(str, 5));
		// System.out.println(subStringByByteForZh(str, 6));
		// System.out.println(subStringByByteForZh(str, 8));
	}


	public static String substringByByteForZh(String src, int begin, int end) {
		if (src == null || end <= 0 || begin < 0 || begin >= end) {
			return "";
		}
		int len = end - begin;
		String result = null;
		String charset = "GBK";
		try {
			byte[] bs = src.getBytes(charset);
			if (begin >= bs.length) {
				return "";
			}
			if (bs.length < end) {
				end = bs.length;
				len = end - begin;
			}
			byte[] bstmp = new byte[len];
			System.arraycopy(bs, begin, bstmp, 0, len);
			result = new String(bstmp, charset);
			// 好像有时最后为半个中文字符，重新构造的字符串会为空。？
			if (result.length() == 0) {
				if (len == 1) {
					result = "";
				} else {
					bstmp = new byte[len - 1];
					System.arraycopy(bs, begin, bstmp, 0, len - 1);
					result = new String(bstmp, charset);
				}
				return result;
			}
			int k = 0;
			for (int i = 0; i < len; i++) {
				if (bstmp[i] < 0) {
					k++;
				} else {
					if (k % 2 != 0) {
						bstmp = new byte[len - 1];
						System.arraycopy(bs, begin + 1, bstmp, 0, len - 1);
						result = new String(bstmp, charset);
						k = 0;
						break;
					}
					k = 0;
				}
			}
			if (k % 2 != 0) {
				bstmp = new byte[len - 1];
				System.arraycopy(bs, begin, bstmp, 0, len - 1);
				result = new String(bstmp, charset);
			}

		} catch (Throwable t) {
			log.error("", t);
		}
		return result;
	}

	/**
	 * 对含中文的字符串按字节长度取子串,一个中文算两个字节<br>
	 * 返回: 字符串数组,String[0]--子串;String[1]--标识最后一位是否是中文取了一半. add by zhangzy
	 * 
	 * @param src
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String[] subStringForZh(String src, int begin, int end) {
		if (src == null || end <= 0 || begin < 0 || begin >= end) {
			return null;
		}
		boolean bEndIsZh = false; // 标识最后一个字符是中文
		int len = end - begin;
		String result = null;
		String charset = "GBK";
		try {
			byte[] bs = src.getBytes(charset);
			if (begin >= bs.length) {
				return null;
			}
			if (bs.length < end) {
				end = bs.length;
				len = end - begin;
			}
			byte[] bstmp = new byte[len];
			System.arraycopy(bs, begin, bstmp, 0, len);
			result = new String(bstmp, charset);
			// 好像有时最后为半个中文字符，重新构造的字符串会为空。？
			if (result.length() == 0) {
				if (len == 1) {
					result = "";
				} else {
					bEndIsZh = true;
					bstmp = new byte[len - 1];
					System.arraycopy(bs, begin, bstmp, 0, len - 1);
					result = new String(bstmp, charset);
				}
				return new String[] { result, String.valueOf(bEndIsZh) };
			}
			int k = 0;
			for (int i = 0; i < len; i++) {
				if (bstmp[i] < 0) {
					k++;
				} else {
					if (k % 2 != 0) {
						bEndIsZh = true;
						bstmp = new byte[len - 1];
						System.arraycopy(bs, begin + 1, bstmp, 0, len - 1);
						result = new String(bstmp, charset);
						k = 0;
						break;
					}
					k = 0;
				}
			}
			if (k % 2 != 0) {
				bEndIsZh = true;
				bstmp = new byte[len - 1];
				System.arraycopy(bs, begin, bstmp, 0, len - 1);
				result = new String(bstmp, charset);
			}

		} catch (Throwable t) {
			log.error("", t);
		}
		return new String[] { result, String.valueOf(bEndIsZh) };
	}

	/**
	 * 对src(可包含中文)按长度进行分隔
	 * 
	 * @param src
	 * @param batchLength
	 * @return String[]
	 */
	public static String[] spiltForZh(String src, int batchLength) {
		if (isEmpty(src) || batchLength < 1) {
			return new String[0];
		}
		List<String> result = new ArrayList<String>();
		int beginIndex = 0, endIndex = batchLength;
		String endIsZh = "";
		while (true) {
			String[] ss = subStringForZh(src, beginIndex, endIndex);
			if (ss == null || ss.length == 0) {
				break;
			}
			String tmpValue = ss[0];
			endIsZh = ss[1];
			if (String.valueOf(Boolean.TRUE).equals(endIsZh)) {
				beginIndex -= 1;
				endIndex -= 1;
			}
			beginIndex += batchLength;
			endIndex += batchLength;
			result.add(tmpValue);
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * json 特殊字符转义 \" \\ \/ \b \f \n \r \t \ u four-hex-digits
	 * 
	 * @param src
	 * @return
	 */
	public static String strEscapeForJson(String src) {
		if (src != null) {
			StringBuilder sb = new StringBuilder(src.length() + 20);
			for (int i = 0; i < src.length(); i++) {
				char c = src.charAt(i);
				switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
				}
			}
			src = sb.toString();
		}
		return src;
	}
	
	/**
	 * 将iso-8859-1的容器编码后的中文乱码还原
	 */
	public static String backIsoToUtf(String src){
		try {
			if (StringUtils.isNotBlank(src)) {
				src = new String(src.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			return src;
		}
		return src;
	}
	
	public static String encodeGbkStr(String src){
		if (StringUtils.isNotBlank(src)) {
			src = URLEncoder.encode(src);
		}
		return src;
	}
	
	public static String decodeGbkStr(String src){
		if (StringUtils.isNotBlank(src)) {
			src = URLDecoder.decode(src);
		}
		return src;
	}
	//ATHENA-12334手工输入多个线索，不止是用逗号
	public static String keywordsReplace(String orignString,String split){
		orignString = fullToHalf(orignString);
		split = StringUtils.isBlank(split) ? "," : split;
		return orignString.replaceAll("[\\t|;|、|\\r\\n]+", split);
	}
	public static String keywordsReplace(String orignString){
		orignString = fullToHalf(orignString);
		String split = ",";
		return orignString.replaceAll("[\\t|;|、|\\r\\n]+", split);
	}
	/**
	 * 全角转半角
	 * @param orignString
	 * @return
	 */
	public static String fullToHalf(String orignString){
		char c[] = orignString.toCharArray();
		for(int i=0;i<c.length;i++){
			if(c[i] == '\u3000'){
				c[i] = ' ';
			}else if(c[i] > '\uFF00' && c[i] < '\uFF5E'){
				c[i] = (char)(c[i] - 65248);
			}
		}
		return new String(c);
	}
	
	public static long getRoundNum(long num1,long num2){
		return Math.round((double)num1/num2);
	}
}
