package com.app.baseutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.app.key.Md5Utils;

import java.util.UUID;

public class DeviceInfoUtils {

    public static String getAppInfo(Context context) {
        String versionName = AppUtils.getAppVersionName();
        int appVersionCode = AppUtils.getAppVersionCode();
        return StringUtils.join("app版本号:", appVersionCode, "\r\n", "app版本:", versionName);
    }

    /**
     * 获取网络信息
     *
     * @param context
     * @return
     */
    public static String getNetworkInfo(Context context) {
        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = systemService.getActiveNetworkInfo();
        if (info != null && info.isConnected() && info.isAvailable()) {
            switch (info.getType()) {
                default:
                    return "UNKNOW";
                case ConnectivityManager.TYPE_MOBILE:
                    int nSubType = info.getSubtype();
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                            Context.TELEPHONY_SERVICE);
                    //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                    String type;
                    switch (nSubType) {
                        case TelephonyManager.NETWORK_TYPE_GSM:
                            type = "GSM";
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            type = "GPRS";
                            break;
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            type = "CDMA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                            type = "EDGE";
                            break;
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                            type = "1xRTT";
                            break;
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            type = "IDEN";
                            break;
                        case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                            type = "TD_SCDMA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            type = "EVDO_A";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                            type = "UMTS";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            type = "EVDO_0";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                            type = "HSDPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                            type = "HSUPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                            type = "HSPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            type = "EVDO_B";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                            type = "EHRPD";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            type = "HSPAP";
                            break;
                        case TelephonyManager.NETWORK_TYPE_IWLAN:
                            type = "IWLAN";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            type = "LTE";
                            break;
                        default:
                            type = info.getSubtypeName();
                            break;
                    }

                    if (telephonyManager.isNetworkRoaming()) {
                        type += " 漫游";
                    }
                    return type;
                case ConnectivityManager.TYPE_WIFI:
                    return "WIFI";
                case ConnectivityManager.TYPE_MOBILE_MMS:
                    return "MOBILE_MMS";
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                    return "MOBILE_SUPL";
                case ConnectivityManager.TYPE_MOBILE_DUN:
                    return "MOBILE_DUN";
                case ConnectivityManager.TYPE_MOBILE_HIPRI:
                    return "MOBILE_HIPRI";
                case ConnectivityManager.TYPE_WIMAX:
                    return "WIMAX";
                case ConnectivityManager.TYPE_BLUETOOTH:
                    return "BLUETOOTH";
                case ConnectivityManager.TYPE_DUMMY:
                    return "DUMMY";
                case ConnectivityManager.TYPE_ETHERNET:
                    return "ETHERNET";
                case ConnectivityManager.TYPE_VPN:
                    return "VPN";
                case 10:
                    return "MOBILE_FOTA";
                case 11:
                    return "MOBILE_IMS";
                case 12:
                    return "MOBILE_CBS";
                case 13:
                    return "WIFI_P2P";
                case 14:
                    return "MOBILE_IA";
                case 15:
                    return "MOBILE_EMERGENCY";
                case 16:
                    return "PROXY";
            }
        }
        return "UNKNOW";
    }

    /**
     * 获取简单的设备信息
     *
     * @return
     */
    public static String getSimpleDeviceInfo() {
        return StringUtils.join("设备信息:", "\r\n", "设备基板=", Build.BOARD, "\r\n", "引导程序版本号=",
                Build.BOOTLOADER, "\r\n", "品牌=", Build.BRAND, "\r\n", "驱动名称=", Build.DEVICE, "\r\n",
                "系统版本号=", Build.DISPLAY, "\r\n", "硬件名称=", Build.HARDWARE, "\r\n", "主机地址=",
                Build.HOST, "\r\n", "设备版本号=", Build.ID, "\r\n", "手机型号=", Build.MODEL, "\r\n",
                "设备制造商=", Build.MANUFACTURER, "\r\n", "产品名称=", Build.PRODUCT, "\r\n", "设备标签=",
                Build.TAGS, "\r\n", "版本类型=", Build.TYPE, "\r\n", "系统开发代号=", Build.VERSION.CODENAME,
                "\r\n", "系统版本=", Build.VERSION.RELEASE, "\r\n", "系统API=", Build.VERSION.SDK_INT);
    }

    public static String getUUID() {
        String serial = null;
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                                Build.USER.length() % 10; //13 位

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String getDeviceCode() {
        StringBuilder builder = new StringBuilder(Build.BOARD);
        builder.append("-");
        builder.append(Build.BRAND);
        builder.append("-");
        builder.append(Build.CPU_ABI);
        builder.append("-");
        builder.append(Build.DEVICE);
        builder.append("-");
        builder.append(Build.DISPLAY);
        builder.append("-");
        builder.append(Build.MANUFACTURER);
        builder.append("-");
        builder.append(Build.HARDWARE);
        builder.append("-");
        builder.append(Build.MODEL);
        builder.append("-");
        builder.append(Build.TAGS);
        builder.append("-");
        builder.append(Build.PRODUCT);
        builder.append("-");
        builder.append(Build.TYPE);
        builder.append("-");
        builder.append(Build.USER);
        builder.append("-");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.append(Build.getSerial());
            } else {
                builder.append(Build.SERIAL);
            }
            //API>=9 使用serial号
        } catch (Exception exception) {
        }
        //使用硬件信息拼凑出来的15位号码
        return Md5Utils.codeMd5(builder.toString());
    }

    public static class DeviceInfo {
        private static final String mBoard = Build.BOARD;//获取设备基板名称;
        private static final String mBootloader = Build.BOOTLOADER;//获取设备引导程序版本号;
        private static final String mBrand = Build.BRAND;//获取设备品牌;
        private static final String mCpuAbi = Build.CPU_ABI;//获取设备指令集名称（CPU的类型）;
        private static final String mCpuAbi2 = Build.CPU_ABI2;//获取第二个指令集名称;
        private static final String mDevice = Build.DEVICE;//获取设备驱动名称;
        private static final String mDisplay = Build.DISPLAY;
        //获取设备显示的版本包（在系统设置中显示为版本号）和ID一样;
        private static final String mFingerprint = Build.FINGERPRINT;
        //设备的唯一标识。由设备的多个信息拼接合成。;
        private static final String mHardware = Build.HARDWARE;//设备硬件名称,一般和基板名称一样（BOARD）;
        private static final String mHost = Build.HOST;//设备主机地址;
        private static final String mId = Build.ID;//设备版本号。;
        private static final String mModel = Build.MODEL;//获取手机的型号 设备名称。;
        private static final String mManufacturer = Build.MANUFACTURER;//获取设备制造商;
        private static final String mProduct = Build.PRODUCT;//整个产品的名称;
        private static final String mRadio = Build.RADIO;//无线电固件版本号，通常是不可用的 显示unknown;
        private static final String mTags = Build.TAGS;
        //设备标签。如release-keys 或测试的 test-keys ;
        private static final long mTime = Build.TIME;//时间;
        private static final String mType = Build.TYPE;//设备版本类型  主要为”user” 或”eng”.;
        private static final String mUser = Build.USER;//设备用户名 基本上都为android-build;
        private static final String mRelease = Build.VERSION.RELEASE;
        //获取系统版本字符串。如4.1.2 或2.2 或2.3等;
        private static final String mCodename = Build.VERSION.CODENAME;
        //设备当前的系统开发代号，一般使用REL代替;
        private static final String mIncremental = Build.VERSION.INCREMENTAL;
        //系统源代码控制值，一个数字或者git hash值;
        private static final String mSdk = Build.VERSION.SDK;
        //系统的API级别 一般使用下面大的SDK_INT 来查看;
        private static final int mSdkInt = Build.VERSION.SDK_INT;//系统的API级别 数字表示;

        public static String getSimpleDeviceInfo() {
            return "设备信息:" + "\r\n" + "设备基板=" + Build.BOARD + "\r\n" + "引导程序版本号=" +
                   Build.BOOTLOADER + "\r\n" + "品牌=" + Build.BRAND + "\r\n" + "驱动名称=" +
                   Build.DEVICE + "\r\n" + "系统版本号=" + Build.DISPLAY + "\r\n" + "硬件名称=" +
                   Build.HARDWARE + "\r\n" + "主机地址=" + Build.HOST + "\r\n" + "设备版本号=" + Build.ID +
                   "\r\n" + "手机型号=" + Build.MODEL + "\r\n" + "设备制造商=" + Build.MANUFACTURER +
                   "\r\n" + "产品名称=" + Build.PRODUCT + "\r\n" + "设备标签=" + Build.TAGS + "\r\n" +
                   "版本类型=" + Build.TYPE + "\r\n" + "系统版本字符串=" + Build.VERSION.RELEASE + "\r\n" +
                   "系统开发代号=" + Build.VERSION.CODENAME + "\r\n" + "系统API=" + Build.VERSION.SDK_INT +
                   "\r\n" + "SERIAL=" + Build.getSerial();
        }
    }
}
