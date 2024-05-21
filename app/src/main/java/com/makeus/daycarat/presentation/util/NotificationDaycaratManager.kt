package com.makeus.daycarat.presentation.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

object NotificationDaycaratManager {

    val NOTI_FCM = 1001
    val channelId: String = "daycarat_fcm"

    fun createFCMNotiChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notiManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var channel: NotificationChannel? =
                notiManager.getNotificationChannel(channelId)

            if (channel == null) {
                channel = NotificationChannel(
                    channelId,
                    "FCM_CAHNNEL",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.setShowBadge(false) // badge off
                channel.lockscreenVisibility = Notification.VISIBILITY_SECRET

                notiManager.createNotificationChannel(channel)
            }
        }
    }

}