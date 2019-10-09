package com.app.baseutils;


import com.app.constant.RegexConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *
 *
 *     time  : 2016/08/02
 *     desc  : 正则相关工具类
 * </pre>
 */
public final class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * 验证IP地址
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配的第一个
     *
     * @param regex
     * @param input
     * @return
     */
    public static String getFirstMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        String matches = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            matches = matcher.group();
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) {
            return null;
        }
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    /**
     * 提取按照顺序规则的标签中的字符串 相当于正则匹配
     *
     * @param input  需要提取的句子
     * @param regexs 按顺序排列的标签,
     *               比如要提取 [url=http:www.baidu.com]百度一下,你就知道[/url]
     *               的    <<http:www.baidu.com>>   和   <<百度一下,你就知道>>
     *               匹配的标签为 <<[url=>> , <<]>> , <<[/url]>>;
     *               标签个数必须大于等于2 << 和 >>
     * @param indexs 匹配到的位置信息,需要传进来,在匹配到位置结果后赋值,然后将结果返回
     * @return 匹配截取到的结果 //只截取最面的结果 例如:String[]{"http:www.baidu.com","百度一下,你就知道"}
     */
    public static String[] regexTag(String input, String[] regexs, int[] indexs) {
        if (input == null) {
            return null;
        }
        if (regexs == null || regexs.length <= 1) {
            return null;
        } else {
            if (indexs == null || indexs.length < regexs.length) {
                indexs = new int[regexs.length];
            }
            for (int i = 0; i < regexs.length; i++) {
                String regex1 = regexs[i];
                int index = input.indexOf(regex1);
                indexs[i] = index;
            }
            for (int i = 0; i < indexs.length; i++) {
                if (indexs[i] < 0) {
                    return null;
                } else {
                    if (i < indexs.length - 1) {
                        if (indexs[i] > indexs[i + 1]) {
                            return null;
                        }
                    }
                }
            }
            String[] result = new String[regexs.length - 1];
            for (int i = 0; i < indexs.length; i++) {
                if (i == 0) {
                    result[i] = input.substring(regexs[i].length() + indexs[i], indexs[i + 1]);
                } else if (i < indexs.length - 1) {
                    result[i] = input.substring(regexs[i].length() + indexs[i], indexs[i + 1]);
                }
            }
            return result;
        }
    }


    /**
     * 提取按照顺序规则的标签中的字符串 相当于正则匹配
     *
     * @param input  需要提取的句子
     * @param regexs 按顺序排列的标签,
     *               比如要提取 [url=http:www.baidu.com]百度一下,你就知道[/url]
     *               的    <<http:www.baidu.com>>   和   <<百度一下,你就知道>>
     *               匹配的标签为 <<[url=>> , <<]>> , <<[/url]>>;
     *               标签个数必须大于等于2 << 和 >>
     * @return 匹配截取到的结果 //只截取最面的结果 例如:String[]{"http:www.baidu.com","百度一下,你就知道"}
     */
    public static String[] regexTag(String input, String[] regexs) {
        return regexTag(input, regexs, null);
    }
}