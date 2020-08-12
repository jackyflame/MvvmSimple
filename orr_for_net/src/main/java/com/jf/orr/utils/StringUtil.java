package com.jf.orr.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Discribe: com.vw.epls.base.utils
 * @Time: 2020/3/20
 * @Author: Yinhao
 */
public class StringUtil {

    public static boolean isEmpty(CharSequence string) {
        return (string == null || string.length() == 0);
    }

    public static boolean isHtml(String input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        if (input.contains("<html>") && input.contains("</html>")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String string) {
        return "null".equals(string) || "NULL".equals(string) || TextUtils.isEmpty(string);
    }

    public static String emptyToStr(String string) {
        return isEmpty(string) ? "" : string;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param string 指定字符串
     * @return 不为 null 且 长度大于 0 返回 true, 否则返回 false
     */
    public static boolean notEmpty(CharSequence string) {
        return string != null && string.length() > 0;
    }

    /**
     * 判断所有的字符串是否都为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串都为空返回 true, 否则放回 false
     */
    public static boolean isAllEmpty(CharSequence... strings) {
        if (strings == null) return true;
        for (CharSequence charSequence : strings) {
            if (!isEmpty(charSequence)) return false;
        }
        return true;
    }

    /**
     * 判断所有的字符串是否都不为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串都不为空返回 true, 否则放回 false
     */
    public static boolean isAllNotEmpty(CharSequence... strings) {
        if (strings == null) return false;
        for (CharSequence charSequence : strings) {
            if (isEmpty(charSequence)) return false;
        }
        return true;
    }

    /**
     * 判断所有的字符串是否不都为空
     *
     * @param strings 指定字符串数组获或可变参数
     * @return 所有字符串不都为空返回 true, 否则返回 false
     */
    public static boolean isNotAllEmpty(CharSequence... strings) {
        return !isAllEmpty(strings);
    }

    /**
     * 判断字符串是否为空或空格
     *
     * @param string 指定字符串
     * @return null 或空字符串或空格字符串返回true, 否则返回 false
     */
    public static boolean isTrimEmpty(String string) {
        return (string == null || string.trim().length() == 0);
    }

    /**
     * 判断字符串是否为空或空白
     *
     * @param string 指定字符串
     * @return null 或空白字符串返回true, 否则返回 false
     */
    public static boolean isBlank(String string) {
        if (string == null) return true;
        for (int i = 0, len = string.length(); i < len; ++i) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个字符串是否相同
     *
     * @param a 作为对比的字符串
     * @param b 作为对比的字符串
     * @return 是否相同
     */
    public static boolean isEquals(String a, String b) {
        return a == b || (a != null && a.equals(b));
    }

    /**
     * 判断两个字符串是否不同
     *
     * @param a 作为对比的字符串
     * @param b 作为对比的字符串
     * @return 是否不同
     */
    public static boolean notEquals(String a, String b) {
        return !isEquals(a, b);
    }

    /**
     * null 转 空字符串
     *
     * @param obj 对象
     * @return 将 null 对象返回空字符串(""), 其他对象调用 toString() 返回的字符串
     */
    public static String nullToEmpty(Object obj) {
        return (obj == null ? "" : (obj instanceof String ? (String) obj : obj.toString()));
    }

    /**
     * null 转 指定的字符串
     *
     * @param obj             对象
     * @param defaultEmptyStr 用于替换 null 对象的字符串
     * @return 如果对象不为 null, 将返回该对象的 toString() 字符串; 如果对象为 null, 则返回指定的默认字符串
     */
    public static String nullToDefault(Object obj, String defaultEmptyStr) {
        return obj == null ? defaultEmptyStr : obj.toString();
    }

    /**
     * null 或空串 转 指定的字符串
     *
     * @param str             字符串
     * @param defaultEmptyStr 用于替换 null 或空串的字符串
     * @return 如果字符串不为 null 或空串, 将返回该字符串; 否则返回指定的默认字符串
     */
    public static String emptyToDefault(String str, String defaultEmptyStr) {
        return str == null || str.length() == 0 ? defaultEmptyStr : str;
    }

    /**
     * 将字符串进行 UTF-8 编码
     *
     * @param string 指定字符串
     * @return 编码后的字符串
     */
    public static String utf8Encode(String string) {
        if (!isEmpty(string) && string.getBytes().length != string.length()) {
            try {
                return URLEncoder.encode(string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return string;
    }

    /**
     * 将字符串进行 UTF-8 编码
     *
     * @param string        指定字符串
     * @param defaultReturn 编码失败返回的字符串
     * @return 编码后的字符串
     */
    public static String utf8Encode(String string, String defaultReturn) {
        if (!isEmpty(string) && string.getBytes().length != string.length()) {
            try {
                return URLEncoder.encode(string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defaultReturn;
            }
        }
        return string;
    }

    /**
     * 判断字符串中是否存在中文汉字
     *
     * @param string 指定字符串
     * @return 是否存在
     */
    public static boolean hasChineseChar(String string) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(string);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 格式化字符串, 用参数进行替换, 例子: format("I am {arg1}, {arg2}", arg1, arg2);
     *
     * @param format 需要格式化的字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String format(String format, Object... args) {
        for (Object arg : args) {
            format = format.replaceFirst("\\{[^\\}]+\\}", arg.toString());
        }
        return format;
    }

    /**
     * 获取中文空格 (宽度和中文字符一致)
     *
     * @param length 空格数
     * @return 中文空格字符串
     */
    public static String getChineseSpaces(int length) {
        if (length < 100) {
            String spaces = "";
            for (int i = 0; i < length; i++) {
                spaces += (char) 12288;
            }
            return spaces;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append((char) 12288);
            }
            return builder.toString();
        }
    }

    public static boolean isNumber(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }
        try {
            Double.valueOf(src);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLetter(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("[a-zA-Z]*");
            return p.matcher(src).matches();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isCapLetter(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("[A-Z]*");
            return p.matcher(src).matches();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLowerLetter(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("[a-z]*");
            return p.matcher(src).matches();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean notNull(String s) {
        return !isEmpty(s);
    }

    public static Boolean getBooleanValue(String booleanStr) {
        if ("false".equals(booleanStr)) {
            return false;
        } else if ("true".equals(booleanStr)) {
            return true;
        }
        return null;
    }

    public static boolean isValueEqual(String value1, String value2) {
        return isValueEqual(value1, value2, true);
    }

    public static boolean isValueEqual(String value1, String value2, boolean NullEqual) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return NullEqual;
        }
        if (isEmpty(value1) && !isEmpty(value2)) {
            return false;
        }
        if (!isEmpty(value1) && isEmpty(value2)) {
            return false;
        }
        if (!value1.equals(value2)) {
            return false;
        }
        if ("null".equals(value1)) {
            return false;
        }
        return true;
    }

    public static boolean getBooleanValue(Boolean value) {
        if (value == null) {
            return false;
        }
        return value;
    }

    public static int getIntValue(Integer value, int nullValue) {
        if (value == null) {
            return nullValue;
        }
        return value;
    }

    public static int getIntValue(String value, int nullValue) {
        if (value == null || value.isEmpty()) {
            return nullValue;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return nullValue;
        }
    }

    public static long getLongValue(String value, int nullValue) {
        if (value == null) {
            return nullValue;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return nullValue;
        }
    }

    public static long getLongValue(Long value, int nullValue) {
        if (value == null) {
            return nullValue;
        }
        return value;
    }

    public static float getFloatValue(String value, float nullValue) {
        if (value == null) {
            return nullValue;
        }
        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            return nullValue;
        }
    }

    public static long getDateTimes(Date value) {
        return getDateTimes(value, 0);
    }

    public static long getDateTimes(Date value, long nullValue) {
        if (value == null) {
            return nullValue;
        }
        return value.getTime();
    }

    public static void setEditTextInhibitInputSpeChat(EditText editText, final Context context){
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].\\-+=<>/?~～！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find() || source.equals(" ")){
                    //ToastUtil.getInstance.showWarn("不允许输入特殊字符与空格", EPApplication.getContext());
                    return "";
                }
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}