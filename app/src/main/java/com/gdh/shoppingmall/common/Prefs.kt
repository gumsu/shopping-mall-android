package com.gdh.shoppingmall.common

import android.preference.PreferenceManager
import com.gdh.shoppingmall.App

/**
 * context를 파라미터로 넣고 객체를 생성해주어야 한다. context를 알고 있어야 하므로 사용 범위가 제한적이다.
 * 이 클래스를 싱클톤으로 만들고 App 클래스를 이용해 전역으로 사용 가능하다.
 * 어디서든 Prefs.name과 같은 코드로 값을 읽고 저장할 수 있다.
 *
 * 푸시 알림을 수신하기 위한 유니크한 키값 저장 필요
 */

object Prefs {
    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NAME = "user_name"
    private const val USER_ID = "user_id"
    private const val FCM_TOKEN = "fcm-token"
    private const val NOTIFICATION_ID = "notification-id"

    val prefs by lazy {
        PreferenceManager
            .getDefaultSharedPreferences(App.instance)
    }

    var token
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit()
            .putString(TOKEN, value)
            .apply()

    var refreshToken
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit()
            .putString(REFRESH_TOKEN, value)
            .apply()

    var userName
        get() = prefs.getString(USER_NAME, null)
        set(value) = prefs.edit()
            .putString(USER_NAME, value)
            .apply()

    var userId
        get() = prefs.getLong(USER_ID, 0)
        set(value) = prefs.edit()
            .putLong(USER_ID, value)
            .apply()

    var fcmToken
        get() = prefs.getString(FCM_TOKEN, null)
        set(value) = prefs.edit()
            .putString(FCM_TOKEN, value)
            .apply()

    var notificationId
        get() = prefs.getInt(NOTIFICATION_ID, 0)
        set(value) = prefs.edit()
            .putInt(NOTIFICATION_ID, value)
            .apply()
}