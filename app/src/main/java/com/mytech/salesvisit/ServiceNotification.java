package com.mytech.salesvisit;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.mytech.salesvisit.util.Util;

/**
 * Helper class for showing and canceling service
 * notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class ServiceNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "ServiceNotification";

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of service notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context,
                              final String exampleString, final int number) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.


        final String ticker = exampleString;
        final String title = res.getString(
                R.string.service_notification_title_template, exampleString);
        final String text = res.getString(
                R.string.service_notification_placeholder_text_template);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_stat_service)
                .setContentTitle(title)
                .setContentText(text)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                // Show a number. This is useful when stacking notifications of
                // a single type.
                .setNumber(number)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(false);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    public static void notifyGps(final Context context){
        notify(context,context.getString(R.string.app_name),Util.GPSNOTIFYI);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context,int notifyid) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, notifyid);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        cancel(context,0);
    }

    public static  Notification getServiceNotification(Context context,String chanalId) {
        Intent intent = new Intent(context, ForegroundLocationService.class);
        intent.putExtra(Util.EXTRA_STARTED_FROM_NOTIFICATION, true);
        CharSequence text = "You are logged in";
        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,chanalId)
                .addAction(R.drawable.ic_check_in, context.getString(R.string.check_in),
                        activityPendingIntent)
                .addAction(R.drawable.ic_logout, context.getString(R.string.log_off),
                        servicePendingIntent)
                .setContentText(text)
                .setContentTitle(context.getString(R.string.app_name))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_service)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());
        return builder.build();
    }
}
