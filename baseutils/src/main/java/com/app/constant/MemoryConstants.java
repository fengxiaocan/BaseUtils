package com.app.constant;

import androidx.annotation.IntDef;
import androidx.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     time  : 2017/03/13
 *     desc  : 存储相关常量
 * </pre>
 */
public final class MemoryConstants {

    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = KB * KB;
    /**
     * GB与Byte的倍数
     */
    public static final long GB = MB * KB;
    /**
     * TB与Byte的倍数
     */
    public static final long TB = GB * KB;

    @IntDef({BYTE, KB, MB})
    @LongDef({GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {}
}
