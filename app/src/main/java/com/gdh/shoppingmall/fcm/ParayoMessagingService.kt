package com.gdh.shoppingmall.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gdh.shoppingmall.R
import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.common.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.*

/**
 * 토큰 생성 및 변화를 감지하기 위해 생성
 *
 * onNewToken() -> 토큰 값이 업데이트될 때 호출되는 콜백
 * Prefs의 fcmToken을 업데이트 해준다.
 * 이미 로그인되어 있는 경우 서버에 토큰 업데이트를 요청한다.
 *
 * onMessageReceived() -> 서버로부터 푸시 메시지를 받았을 때 호출되는 콜백
 * 서버에서 data 필드를 채워 보냈으므로 앱에서도 data 필드를 사용해야 한다.
 */

class ParayoMessagingService : FirebaseMessagingService(), AnkoLogger {

    override fun onNewToken(fcmToken: String) {
        Prefs.fcmToken = fcmToken
        if(!Prefs.token.isNullOrEmpty() && fcmToken != null){
            runBlocking {
                try {
                    val response = ParayoApi.instance.updateFcmToken(fcmToken)
                    if(!response.success){
                        warn(response.message ?: "토큰 업데이트 실패")
                    }
                }catch (e: Exception) {
                    error(e.message ?: "토큰 업데이트 실패", e)
                }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message?.data?.let { data ->
            debug(data)
            createNotificationChannelIfNeeded()

            val builder = NotificationCompat
                .Builder(this, "channel.parayo.default")
                .setContentTitle(data["title"])
                .setContentText(data["content"])
                .setSmallIcon(R.drawable.ic_logo_black)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(NotificationId.generate(), builder.build())
            }
        }
    }

    private fun createNotificationChannelIfNeeded() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel.parayo.default",
                "기본 알림",
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.description = "기본 알림"
            notificationManager.createNotificationChannel(channel)
        }
    }
}