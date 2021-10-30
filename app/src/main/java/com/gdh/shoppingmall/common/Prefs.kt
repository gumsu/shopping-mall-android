package com.gdh.shoppingmall.common

import android.preference.PreferenceManager
import com.gdh.shoppingmall.App

/**
 * context를 파라미터로 넣고 객체를 생성해주어야 한다. context를 알고 있어야 하므로 사용 범위가 제한적이다.
 * 이 클래스를 싱클톤으로 만들고 App 클래스를 이용해 전역으로 사용 가능하다.
 * 어디서든 Prefs.name과 같은 코드로 값을 읽고 저장할 수 있다.
 */

object Prefs {
    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NAME = "user_name"
    private const val USER_ID = "user_id"
    private const val FCM_TOKEN = "fcm-token"

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
}