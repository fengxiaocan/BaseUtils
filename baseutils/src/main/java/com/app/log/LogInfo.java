package com.app.log;

import static com.app.baselib.log.LogUtils.formatTime;

/**
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 12:23
 * @version: 1.1
 * @desc： TODO
 */

public class LogInfo {

    @LogUtils.LogType
    int type;
    String TAG;
    String log;
    String time;

    public LogInfo() {
        this.time = formatTime(System.currentTimeMillis());
    }

    public LogInfo(int type, String TAG, String log) {
        this.type = type;
        this.TAG = TAG;
        this.log = log;
        this.time = formatTime(System.currentTimeMillis());
    }


    public String getType() {
        switch (type) {
            case LogUtils.VERBOSE:
                return "V";
            case LogUtils.DEBUG:
                return "D";
            case LogUtils.INFO:
                return "I";
            case LogUtils.WARN:
                return "W";
            case LogUtils.ERROR:
                return "E";
        }
        return "V";
    }

    @Override
    public String toString() {
        return new StringBuffer(time).append(" ").append(TAG).append(" ")
                .append(getType()).append(": ").append(log)
                .toString();
    }

    public String toStrings() {
        return new StringBuffer(time).append(" ").append(TAG).append(": ")
                .append(log).toString();
    }
}
