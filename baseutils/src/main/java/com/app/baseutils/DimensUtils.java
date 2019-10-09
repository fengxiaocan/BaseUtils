package com.app.baseutils;

/**
 * @author
 * @version 1.0
 * @date 2018/2/1 0001
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.DimenRes;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;
/*        屏幕密度 	范围(dpi) 	标准分辨率 	dp与px 	图标尺寸
        ldpi(QVGA) 	~ 120 	240 * 320 	1dp=0.75px 	36 * 36
        mdpi(HVGA) 	120 ~ 160 	320 * 480 	1dp=1px 	48 * 48
        hdpi(WVGA) 	160 ~ 240 	480 * 800 	1dp=1.5px 	72 * 72
        xhdpi(720P) 	240 ~ 320 	720 * 1280 	1dp=2px 	96 * 96
        xxhdpi(1080p) 	320 ~ 480 	1080 * 1920 	1dp=3px 	144 * 144
        xxxhdpi(2K) 	480 ~ 640 	1440 × 2560 	1dp=4px 	192 * 192*/

/**
 * 尺寸工具箱，提供与Android尺寸相关的工具方法
 */
public class DimensUtils {
    public static float pixelsFromSpResource(Context context, @DimenRes int sizeRes) {
        final Resources res = context.getResources();
        return res.getDimension(sizeRes) / res.getDisplayMetrics().density;
    }

    /**
     * dp单位转换为px
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px单位转换为dp
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }


    private static final String[] getDpi = {"ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"};
    /**
     * 分辨率相关adb 命令：查看分辨率，adb shell wm size；查看手机适配分辨率信息，查看信息最后部分adb shell dumpsys
     * activity； mConfiguration: {1.0 460mcc2mnc zh_CN ?layoutDir sw360dp w360dp
     * h567dp 480dp nrml port finger -keyb/v/h -nav/h s.5}
     */
    private static String TAG = "ResolutionAdaptationUtils";

    /**
     * 1dp定义为屏幕密度值为160ppi时的1px，即，在mdpi时，1dp = 1px。 以mdpi为标准，这些屏幕的密度值比为： ldpi :
     * mdpi : hdpi : xhdpi : xxhdpi : xxxhdpi= 0.75 : 1 : 1.5 : 2 : 3 :
     * 4；即，在xhdpi的密度下，1dp=2px；
     * 在hdpi情况下，1dp=1.5px。其他类推。主流分辨率：800×480、960×540、1280×720、1920×1080、2560 x
     * 1440 DENSITY_LOW = 120 DENSITY_MEDIUM = 160 //默认值 DENSITY_TV = 213 //TV专用
     * DENSITY_HIGH = 240 DENSITY_XHIGH = 320 DENSITY_400 = 400 DENSITY_XXHIGH =
     * 480 DENSITY_XXXHIGH = 640 ldpi 120dp~160dp 32×32px mdpi 120dp~160dp
     * 48×48px hdpi 160dp~240dp 72×72px xhdpi 240dp~320dp 96×96px xxhdpi
     * 320dp~480dp 144×144px xxxhdpi 480dp~640dp 192×192px 在Google官方开发文档中，说明了
     * mdpi：hdpi：xhdpi：xxhdpi：xxxhdpi=2：3：4：6：8 的尺寸比例进行缩放。
     * 例如，一个图标的大小为48×48dp，表示在mdpi上
     * ，实际大小为48×48px，在hdpi像素密度上，实际尺寸为mdpi上的1.5倍，即72×72px，以此类推 Android上常见度量单位
     * px（像素）：屏幕上的点，绝对长度，与硬件相关。 in（英寸）：长度单位。 mm（毫米）：长度单位。 pt（磅）：1/72英寸，point。
     * dp（与密度无关的像素）：一种基于屏幕密度的抽象单位。在每英寸160点的显示器上，1dp = 1px。
     * dip：Density-independent pixel,同dp相同。 sp：在dp的基础上，还与比例无关，个人理解为是一个矢量图形单位
     * <p>
     * 得到手机values文件夹方法，在cmd窗口下输入：adb shell am get-config
     * 得到Nexus5手机信息如下:Android程序默认会读取values下的dimens， 如果定义了h567dp
     * values，如values-h567dp
     * ，就会读取该文件夹下在的dimens，getResources().getDimension(R.dimen.witch_values)，
     * 在Android程序中定义主流设备的dimens，并根据缩放系数定义多个dp值，就能做到屏幕适配了。 config:
     * en-rUS-ldltr-sw360dp-w360dp-h567dp-normal-notlong-port-notnight-xxhdpi-f
     * inger-keysexposed-nokeys-navhidden-nonav-v22 abi: armeabi-v7a,armeabi
     * <p>
     * 得到dip，缩放系数
     *
     * @return
     */
    public static float getDipScale() {
        final float scale = Utils.getResources().getDisplayMetrics().density;
        return scale;
    }

    public static String getResolutionInfo() {
        return Utils.getResources().getDisplayMetrics().toString();
    }

    /**
     * 获取屏幕密度，每英寸的像素数
     *
     * @return
     */
    public static int getDensity() {
        return Utils.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = Utils.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxValue) {
        final float scale = Utils.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Utils.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Utils.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * 手动计算设备的屏幕像素密度
     *
     * @return
     */
    public static int getPPI() {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);
        int height = point.y;
        int width = point.x;
        return (int) (Math.sqrt(height * height + width * width) / getScreenSizeInch());
    }

    public static int getDpi(int height, int width, int inch) {
        return (int) (Math.sqrt(height * height + width * width) / inch);
    }

    /**
     * 得到该手机的drawable文件夹密码
     */
    public static String getDensityString() {
        final float density = getDipScale();
        String desintyString;
        if (0.75 == density) {
            desintyString = "ldpi";
        } else if (1.0 == density) {
            desintyString = "mdpi";
        } else if (1.5 == density) {
            desintyString = "hdpi";
        } else if (2.0 == density) {
            desintyString = "xhdpi";
        } else if (3.0 == density) {
            desintyString = "xxhdpi";
        } else if (4.0 == density) {
            desintyString = "xxxhdpi";
        } else {
            desintyString = "hdpi";
        }
        return desintyString;
    }

    public static void getDensityInfo() {
        DisplayMetrics displayMetrics = Utils.getResources().getDisplayMetrics();
    }

    public static void getDisplayInfomation() {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);
    }

    //不包含虚拟键在内的屏幕高度
    public static int getScreenHeightWithoutVirtualBar(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取虚拟按键的高度，无关虚拟按键是否显示
     */
    public static int getNavigationBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen",
                "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 得到设备屏幕尺寸 http://blog.csdn.net/lincyang/article/details/42679589
     */
    public static double getScreenSizeInch() {
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = Utils.getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    /**
     * 获取设备的分辨率信息
     */
    public static void getScreenSizeOfDevice(Activity activity) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(point);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        //        Log.d(TAG, "当前设备---分辨率： " + point.toString());
        //        Log.d(TAG, "当前设备---app分辨率： " + +width + "x" + height);
        //        Log.d(TAG, "当前设备---虚拟键高度： " + getNavigationBarHeight());
        DisplayMetrics dm = Utils.getResources().getDisplayMetrics();
        //        Log.d(TAG, "当前设备---屏幕像素密度(PPI) dm.xdpi == ： " + dm.xdpi);
        //        Log.d(TAG, "当前设备---屏幕像素密度(PPI) dm.ydpi == ： " + dm.ydpi);
        double x = Math.pow(point.x / dm.xdpi, 2); // 根据分辨率、屏幕尺寸和屏幕像素密度三者之间的公式
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        double density = Math.sqrt(point.x * point.x + point.y * point.y) / screenInches;
        // double density = Math.sqrt(point.x*point.x + point.y*point.y)/4.95;
        // //Nexus尺寸4.95，计算所得4.971247911145661
        //        Log.d(TAG, "当前设备---屏幕尺寸： " + screenInches);
        //        Log.d(TAG, "当前设备---系统参数密度Density： " + dm.densityDpi);
        //        Log.d(TAG, "当前设备---系统参数密度Dpi缩放系数： " + dm.scaledDensity);
        //        Log.d(TAG, "当前设备---计算密度Density： " + density);
        //        Log.d(TAG, "当前设备---计算密度Dpi缩放系数： " + density / 160);
    }

    /**
     * 得到设备状态栏高度
     *
     * @return
     */
    public static int getStatusHeightOnRReflect() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen.xml");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                                               .get(object)
                                               .toString());
            statusHeight = Utils.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 得到statusbar高度
     *
     * @return
     */
    public static int getStatusHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
        // 获取status_bar_height资源的ID
        int resourceId = Utils.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            // 根据资源ID获取响应的尺寸值
            statusBarHeight = Utils.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 标题栏高度
     *
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }


    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi() {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes") Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics",
                    DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 通过WindowManager得到虚拟键高度
     *
     * @return
     */
    public static int getNavigationBarHeight() {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        // 不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        int screenHeigth = point.y;
        // 包含虚拟按键
        windowManager.getDefaultDisplay().getRealSize(point);
        int screenRealHeigth = point.y;
        return screenRealHeigth - screenHeigth;
    }

    /**
     * 获取屏幕的宽高
     */
    public static void measure() {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        // 不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        // 包含虚拟按键
        // windowManager.getDefaultDisplay().getRealSize(point);
        // 屏幕宽度
        int height = point.y;
        // 屏幕高度
        int width = point.x;
        Log.d(TAG, " height:" + height + "width:" + width);
    }

    // 获取是否存在NavigationBar虚拟键
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = Utils.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    // 获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getScreenRealHeight() {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes") Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics",
                    DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @return
     */
    public static int getNavigationBarHeight1() {
        int totalHeight = getScreenRealHeight();
        int contentHeight = ScreenUtils.getScreenHeight();
        return totalHeight - contentHeight;
    }

    //获得手机的宽度和高度像素单位为px
    // 通过WindowManager获取
    public static void getScreenDensity_ByWindowManager(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG,
                "Screen Ratio: [" + width + "x" + height + "],density=" + density + ",densityDpi=" +
                densityDpi);
        Log.d(TAG, "Screen mDisplayMetrics: " + mDisplayMetrics);
    }

    // 通过Resources获取
    public static void getScreenDensity_ByResources(Context activity) {
        DisplayMetrics mDisplayMetrics = activity.getResources().getDisplayMetrics();
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG,
                "Screen Ratio: [" + width + "x" + height + "],density=" + density + ",densityDpi=" +
                densityDpi);
        Log.d(TAG, "Screen mDisplayMetrics: " + mDisplayMetrics);

    }

    /**
     * 隐藏虚拟键
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void hideNavBar(Activity activity) {
        int flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;// hide
        // 获取属性
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    /**
     * 显示虚拟键
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void showNavBar(Activity activity) {
        int flag = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR; // show
        // 获取属性
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);
    }

    enum DrawableDPIIndex {
        ldpi,
        mdpi,
        hdpi,
        xhdpi,
        xxhdpi,
        xxxhdpi;
    }

}
