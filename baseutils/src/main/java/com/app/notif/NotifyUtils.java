package com.app.notif;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.app.baseutils.Utils;
import com.app.notif.builder.BigPicBuilder;
import com.app.notif.builder.BigTextBuilder;
import com.app.notif.builder.MailboxBuilder;
import com.app.notif.builder.ProgressBuilder;
import com.app.notif.builder.SingleLineBuilder;


/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class NotifyUtils {

    /**
     * The constant context.
     */
    public static Context context;
    private static NotificationManager nm;

    static {
        context = Utils.getContext();
        nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    /**
     * Get nm notification manager.
     *
     * @return the notification manager
     */
    public static NotificationManager getNotificationManager() {
        return nm;
    }

    /**
     * Init.
     *
     * @param context1 the context 1
     */
    @Deprecated
    public static void init(Context context1) {
        context = context1;
        nm = (NotificationManager) context1.getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    /**
     * Build simple single line open.
     *
     * @param id            the id
     * @param smallIcon     the small icon
     * @param contentTitle  the content title
     * @param contentText   the content text
     * @param contentIntent the content intent
     * @return the single line open
     */
    public static SingleLineBuilder buildSimple(int id, int smallIcon, CharSequence contentTitle,
            CharSequence contentText, PendingIntent contentIntent)
    {
        SingleLineBuilder builder = new SingleLineBuilder();
        builder.setBase(smallIcon, contentTitle, contentText).setId(id).setContentIntent(
                contentIntent);
        return builder;
    }

    /**
     * Build progress progress open.
     *
     * @param id           the id
     * @param smallIcon    the small icon
     * @param contentTitle the content title
     * @param progress     the progress
     * @param max          the max
     * @return the progress open
     */
    @Deprecated
    public static ProgressBuilder buildProgress(int id, int smallIcon, CharSequence contentTitle,
            int progress, int max)
    {
        ProgressBuilder builder = new ProgressBuilder();
        builder.setBase(smallIcon, contentTitle, progress + "/" + max).setId(id);
        builder.setProgress(max, progress, false);
        return builder;
    }

    /**
     * Build progress progress open.
     *
     * @param id           the id
     * @param smallIcon    the small icon
     * @param contentTitle the content title
     * @param progress     the progress
     * @param max          the max
     * @param format       the format
     * @return the progress open
     */
    public static ProgressBuilder buildProgress(int id, int smallIcon, CharSequence contentTitle,
            int progress, int max, String format)
    {
        ProgressBuilder builder = new ProgressBuilder();
        builder.setBase(smallIcon, contentTitle, progress + "/" + max).setId(id);
        builder.setProgressAndFormat(progress, max, false, format);
        return builder;
    }

    /**
     * Build big pic big pic open.
     *
     * @param id           the id
     * @param smallIcon    the small icon
     * @param contentTitle the content title
     * @param contentText  the content text
     * @param summaryText  the summary text
     * @return the big pic open
     */
    public static BigPicBuilder buildBigPic(int id, int smallIcon, CharSequence contentTitle,
            CharSequence contentText, CharSequence summaryText)
    {
        BigPicBuilder builder = new BigPicBuilder();
        builder.setBase(smallIcon, contentTitle, contentText).setId(id);
        builder.setSummaryText(summaryText);
        return builder;
    }

    /**
     * Build big text big text open.
     *
     * @param id           the id
     * @param smallIcon    the small icon
     * @param contentTitle the content title
     * @param contentText  the content text
     * @return the big text open
     */
    public static BigTextBuilder buildBigText(int id, int smallIcon, CharSequence contentTitle,
            CharSequence contentText)
    {
        BigTextBuilder builder = new BigTextBuilder();
        builder.setBase(smallIcon, contentTitle, contentText).setId(id);
        //open.setSummaryText(summaryText);
        return builder;
    }

    /**
     * Build mail box mailbox open.
     *
     * @param id           the id
     * @param smallIcon    the small icon
     * @param contentTitle the content title
     * @return the mailbox open
     */
    public static MailboxBuilder buildMailBox(int id, int smallIcon, CharSequence contentTitle) {
        MailboxBuilder builder = new MailboxBuilder();
        builder.setBase(smallIcon, contentTitle, "").setId(id);
        return builder;
    }

    /**
     * Notify.
     *
     * @param id           the id
     * @param notification the notification
     */
    public static void notify(int id, Notification notification) {
        nm.notify(id, notification);
    }

    /**
     * Build intent pending intent.
     *
     * @param clazz the clazz
     * @return the pending intent
     */
    public static PendingIntent buildIntent(Class clazz) {
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        Intent intent = new Intent(NotifyUtils.context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(NotifyUtils.context, 0, intent, flags);
        return pi;
    }

    /**
     * Cancel.
     *
     * @param id the id
     */
    public static void cancel(int id) {
        if (nm != null) {
            nm.cancel(id);
        }
    }

    /**
     * Cancel all.
     */
    public static void cancelAll() {
        if (nm != null) {
            nm.cancelAll();
        }
    }

}
