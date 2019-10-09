package com.app.baseutils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.util
 * @创建者: Noah.冯
 * @时间: 16:16
 * @描述： 与view相关的工具类
 */
public class ViewUtils {

    /**
     * 设置listView的高度为所有子View的高度之和
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     *
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildrens(ListView listView) {
        if (listView == null) {
            return 0;
        }
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }


    /**
     * 修改整个界面所有控件的字体
     */
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        //path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, path, act);
            }
        }
    }

    /**
     * 修改整个界面所有控件的字体大小
     */
    public static void changeTextSize(ViewGroup root, int size, Activity act) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextSize(size);
            } else if (v instanceof Button) {
                ((Button) v).setTextSize(size);
            } else if (v instanceof EditText) {
                ((EditText) v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup) v, size, act);
            }
        }
    }

    /**
     * 不改变控件位置，修改控件大小
     */
    public static void changeSize(View view, int width, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (view.getMeasuredWidth() != width) {
                params.width = width;
            }
            if (view.getMeasuredHeight() != height) {
                params.height = height;
            }
        }
    }

    /**
     * 修改控件的高
     */
    public static void changeHeight(View v, int H) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) v.getLayoutParams();
        params.height = H;
        v.setLayoutParams(params);
    }

    /**
     * 获取全局坐标系的一个视图区域， 返回一个填充的Rect对象；该Rect是基于总整个屏幕的
     *
     * @param view
     * @return
     */
    public static Rect getGlobalVisibleRect(View view) {
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        return r;
    }

    /**
     * 算该视图在全局坐标系中的x，y值，（注意这个值是要从屏幕顶端算起，也就是索包括了通知栏的高度）
     * 获取在当前屏幕内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 计算该视图在它所在的widnow的坐标x，y值，//获取在整个窗口内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }


    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 判断触点是否落在该View上
     */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] &&
               motionY <= (vLoc[1] + v.getHeight());
    }

    /**
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(View view, boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }


    /**
     * 给TextView设置下划线
     *
     * @param textView
     */
    public static void setUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }


    /**
     * 获取view的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取view的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取view的上下文
     *
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * View是否可见
     *
     * @param view
     * @return
     */
    public static boolean isVisibilit(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    /**
     * View不可见
     *
     * @param view
     */
    public static void hideView(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void invisibleView(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showView(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                AlphaAnimation mShowAction = new AlphaAnimation(0f, 1f);
                mShowAction.setDuration(animtime);
                view.setAnimation(mShowAction);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showViewWithAnim(View view, Animation animation, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                animation.setDuration(animtime);
                view.setAnimation(animation);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void hideViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.GONE) {
                AlphaAnimation mShowAction = new AlphaAnimation(1f, 0f);
                mShowAction.setDuration(animtime);
                view.setAnimation(mShowAction);
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置View的PaddingLeft和paddingRight
     *
     * @param view
     * @param padding
     */
    public static void setViewPaddingVertical(View view, int padding) {
        if (view != null) {
            view.setPadding(padding, view.getPaddingTop(), padding, view.getPaddingBottom());
        }
    }

    /**
     * 设置View的PaddingTop和paddingBottom
     *
     * @param view
     * @param padding
     */
    public static void setViewPaddingHorizontal(View view, int padding) {
        if (view != null) {
            view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), padding);
        }
    }

    /**
     * 隐藏或展示View
     *
     * @param view
     * @return 是否隐藏
     */
    public static boolean showOrHideView(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏或展示View
     *
     * @param view
     * @return 是否隐藏
     */
    public static boolean showOrHideViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                // 隐藏动画
                view.setVisibility(View.GONE);
                AlphaAnimation mHiddenAction = new AlphaAnimation(1f, 0f);
                mHiddenAction.setDuration(animtime);
                view.setAnimation(mHiddenAction);
            } else {
                view.setVisibility(View.VISIBLE);
                // 显示动画
                AlphaAnimation mShowAction = new AlphaAnimation(0f, 1f);
                mShowAction.setDuration(animtime);
                view.setAnimation(mShowAction);
                return true;
            }
        }
        return false;
    }

    /**
     * 限制EditText最大字数
     *
     * @param editText
     */
    public static void restrictEditMaxLenght(EditText editText, int maxLenght) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenght)});
    }

    /**
     * 限制Edittext输入类型
     *
     * @param editText
     * @param inputType
     * @param restrict
     */
    public static void restrictEditInputType(EditText editText, final int inputType,
            final char[] restrict)
    {
        editText.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return restrict;
            }

            @Override
            public int getInputType() {
                // TODO Auto-generated method stub
                return inputType;
            }
        });
    }

    public static void changeEditSeePassword(EditText editText, boolean isCanSeePassword) {
        if (!isCanSeePassword) {
            //否则隐藏密码
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            //如果选中，显示密码
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    public static void recycleImageView(ImageView view) {
        if (view == null) {
            return;
        }
        Drawable drawable = view.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                view.setImageBitmap(null);
                bmp.recycle();
                bmp = null;
            }
        }
    }
}
