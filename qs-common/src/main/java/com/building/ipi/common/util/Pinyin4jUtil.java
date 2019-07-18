package com.building.ipi.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

/**
 * @Description:
 * @author: yuzj
 * @Date: 2019/07/02 14:26
 */
public class Pinyin4jUtil {
    public Pinyin4jUtil() {
    }

    public static String converterToFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for(int i = 0; i < nameChar.length; ++i) {
            if (nameChar[i] > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for(int j = 0; j < strs.length; ++j) {
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination var7) {
                    var7.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }

            pinyinName.append(" ");
        }

        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    public static String converterToSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for(int i = 0; i < nameChar.length; ++i) {
            if (nameChar[i] > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for(int j = 0; j < strs.length; ++j) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination var7) {
                    var7.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }

            pinyinName.append(" ");
        }

        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        List<Map<String, Integer>> mapList = new ArrayList();
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        String[] var4 = firsts;
        int var5 = firsts.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String str = var4[var6];
            onlyOne = new Hashtable();
            String[] china = str.split(",");
            String[] var9 = china;
            int var10 = china.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                String s = var9[var11];
                Integer count = (Integer)onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count = count.intValue() + 1;
                    onlyOne.put(s, count);
                }
            }

            mapList.add(onlyOne);
        }

        return mapList;
    }

    private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null;

        for(int i = 0; i < list.size(); ++i) {
            Map<String, Integer> temp = new Hashtable();
            Iterator var4;
            String s;
            if (first == null) {
                var4 = ((Map)list.get(i)).keySet().iterator();

                while(var4.hasNext()) {
                    s = (String)var4.next();
                    temp.put(s, Integer.valueOf(1));
                }
            } else {
                var4 = first.keySet().iterator();

                while(true) {
                    if (!var4.hasNext()) {
                        if (temp != null && temp.size() > 0) {
                            first.clear();
                        }
                        break;
                    }

                    s = (String)var4.next();
                    Iterator var6 = ((Map)list.get(i)).keySet().iterator();

                    while(var6.hasNext()) {
                        String s1 = (String)var6.next();
                        String str = s + s1;
                        temp.put(str, Integer.valueOf(1));
                    }
                }
            }

            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }

        String returnStr = "";
        String str;
        if (first != null) {
            for(Iterator var10 = first.keySet().iterator(); var10.hasNext(); returnStr = returnStr + str + ",") {
                str = (String)var10.next();
            }
        }

        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }

        return returnStr;
    }
}
