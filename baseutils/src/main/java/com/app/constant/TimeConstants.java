package com.app.constant;

import androidx.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *
 *
 *     time  : 2017/03/13
 *     desc  : 时间相关常量
 * </pre>
 */
public final class TimeConstants {

    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60 * SEC;
    /**
     * 时与毫秒的倍数
     */
    public static final long HOUR = 60L * MIN;
    /**
     * 天与毫秒的倍数
     */
    public static final long DAY = 24L * HOUR;
    /**
     * 周与毫秒的倍数
     */
    public static final long WEEK = 7L * DAY;

    /**
     * 年与毫秒的倍数
     */
    public static final long YEAR = 355L * DAY;

    @LongDef({MSEC, SEC, MIN, HOUR, DAY, WEEK, YEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
