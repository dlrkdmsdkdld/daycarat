package com.makeus.daycarat.data.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.makeus.daycarat.R
import com.makeus.daycarat.domain.repository.FcmRepository
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.util.Constant.TAG
import com.makeus.daycarat.presentation.util.NotificationDaycaratManager
import com.makeus.daycarat.presentation.util.NotificationDaycaratManager.createFCMNotiChannel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DayCaratFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: FcmRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token) // 새로운 토큰 발급될때마다 , 서버에 전
        repository(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("GHLEEFCM", "에프시엠 옴")
//TODO  알림오면 로컬디비에 내용 저장해야함 , 클릭했으면 그에따른 처리로직도 추가해야함

        //NOTE 백그라운드에서는 시스템이 자동으로 푸쉬 메시지를 보내주지만 , 포그라운드일때는 푸쉬알림을 따로 지정해줘야함
        Log.d(TAG, "onMessageReceived: $message")
        message.notification?.apply {
            createFCMNotiChannel(this@DayCaratFirebaseMessagingService)
            val intent = Intent(this@DayCaratFirebaseMessagingService, MainActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(this@DayCaratFirebaseMessagingService, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val builder = NotificationCompat.Builder(this@DayCaratFirebaseMessagingService, NotificationDaycaratManager.channelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NotificationDaycaratManager.NOTI_FCM, builder.build())
        }
        

    }
}