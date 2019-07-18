package com.building.ipi.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: yuzj
 * @Date: 2019/07/02 14:24
 */
public class StringUtil {
    public StringUtil() {
    }

    public static boolean isNumeric(String str) {
        int i = str.length();

        do {
            --i;
            if (i < 0) {
                return true;
            }
        } while(Character.isDigit(str.charAt(i)));

        return false;
    }

    public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object... args) {
        int argsLen = args.length;
        boolean markFound = false;
        StringBuilder sb = new StringBuilder(msgWithFormat);
        if (argsLen > 0) {
            for(int i = 0; i < argsLen; ++i) {
                String flag = "%" + (i + 1);

                for(int idx = sb.indexOf(flag); idx >= 0; idx = sb.indexOf(flag)) {
                    markFound = true;
                    sb.replace(idx, idx + 2, toString(args[i], autoQuote));
                }
            }

            if (args[argsLen - 1] instanceof Throwable) {
                StringWriter sw = new StringWriter();
                ((Throwable)args[argsLen - 1]).printStackTrace(new PrintWriter(sw));
                sb.append("\n").append(sw.toString());
            } else if (argsLen == 1 && !markFound) {
                sb.append(args[argsLen - 1].toString());
            }
        }

        return sb;
    }

    public static StringBuilder formatMsg(String msgWithFormat, Object... args) {
        return formatMsg(new StringBuilder(msgWithFormat), true, args);
    }

    public static String toString(Object obj, boolean autoQuote) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            sb.append("NULL");
        } else if (obj instanceof Object[]) {
            for(int i = 0; i < ((Object[])((Object[])obj)).length; ++i) {
                sb.append(((Object[])((Object[])obj))[i]).append(", ");
            }

            if (sb.length() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
        } else {
            sb.append(obj.toString());
        }

        if (autoQuote && sb.length() > 0 && (sb.charAt(0) != '[' || sb.charAt(sb.length() - 1) != ']') && (sb.charAt(0) != '{' || sb.charAt(sb.length() - 1) != '}')) {
            sb.insert(0, "[").append("]");
        }

        return sb.toString();
    }

    public static String convertQuot(String orgStr) {
        return orgStr.replace("'", "\\'").replace("\"", "\\\"");
    }

    public static String trimSufffix(String toTrim, String trimStr) {
        while(toTrim.endsWith(trimStr)) {
            toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
        }

        return toTrim;
    }

    public static String htmlEntityToString(String dataStr) {
        int start = 0;
        StringBuffer buffer = new StringBuffer();

        while(start > -1) {
            int system = 10;
            if (start == 0) {
                int t = dataStr.indexOf("&#");
                if (start != t) {
                    start = t;
                }
            }

            int end = dataStr.indexOf(";", start + 2);
            String charStr = "";
            char letter;
            if (end != -1) {
                charStr = dataStr.substring(start + 2, end);
                letter = charStr.charAt(0);
                if (letter == 'x' || letter == 'X') {
                    system = 16;
                    charStr = charStr.substring(1);
                }
            }

            try {
                letter = (char)Integer.parseInt(charStr, system);
                buffer.append((new Character(letter)).toString());
            } catch (NumberFormatException var7) {
                var7.printStackTrace();
            }

            start = dataStr.indexOf("&#", end);
            if (start - end > 1) {
                buffer.append(dataStr.substring(end + 1, start));
            }

            if (start == -1) {
                int length = dataStr.length();
                if (end + 1 != length) {
                    buffer.append(dataStr.substring(end + 1, length));
                }
            }
        }

        return buffer.toString();
    }

    public static String stringToHtmlEntity(String str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch(c) {
                case '\n':
                    sb.append(c);
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    if (c >= ' ' && c <= '~') {
                        sb.append(c);
                    } else {
                        sb.append("&#x");
                        sb.append(Integer.toString(c, 16));
                        sb.append(';');
                    }
            }
        }

        return sb.toString();
    }

    public static String stringToUnicode(String s) {
        String unicode = "";
        char[] charAry = new char[s.length()];

        for(int i = 0; i < charAry.length; ++i) {
            charAry[i] = s.charAt(i);
            unicode = unicode + "\\u" + Integer.toString(charAry[i], 16);
        }

        return unicode;
    }

    public static String unicodeToString(String unicodeStr) {
        StringBuffer sb = new StringBuffer();
        String[] str = unicodeStr.toUpperCase().split("\\\\U");

        for(int i = 0; i < str.length; ++i) {
            if (!str[i].equals("")) {
                char c = (char)Integer.parseInt(str[i].trim(), 16);
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String html2Text(String inputString) {
        String htmlStr = inputString;
        String textStr = "";

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile(regEx_script, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception var12) {
            System.err.println("Html2Text: " + var12.getMessage());
        }

        return textStr;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().equals("");
    }

    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    public static String encodingString(String str, String from, String to) {
        String result;
        try {
            result = new String(str.getBytes(from), to);
        } catch (Exception var5) {
            result = str;
        }

        return result;
    }

    public static Long toLong(String str) {
        return str == null ? null : (str == "" ? null : (StringUtils.isNumeric(str) ? Long.parseLong(str) : null));
    }

    public static String isNullToEmpty(String strObject) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(strObject) || "null".equals(strObject)) {
            strObject = "";
        }

        return strObject;
    }

    public static String objectToString(Object object) {
        return null == object ? "" : object.toString();
    }

    public static short objectToShort(Object object) {
        return null == object ? 0 : Short.valueOf(object.toString()).shortValue();
    }

    public static Integer objectToInteger(Object object) {
        return null == object ? 0 : Integer.valueOf(object.toString()).intValue();
    }

    public static String ToSBC(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == ' ') {
                c[i] = 12288;
            } else if (c[i] < 127) {
                c[i] += 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        String returnString = new String(c);
        return returnString;
    }

    public static void main(String[] args) {
        try {
            System.out.println(">>>>>>>>>>>>>.=" + isNullToEmpty("二二 遥压下"));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        }

        String s = "123";
        System.out.println(toLong(s));
    }
}
