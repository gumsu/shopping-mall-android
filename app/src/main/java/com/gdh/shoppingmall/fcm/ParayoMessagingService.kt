package com.gdh.shoppingmall.fcm

import com.gdh.shoppingmall.api.ParayoApi
import com.gdh.shoppingmall.common.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import org.jetbrains.anko.error

/**
 * 토큰 생성 및 변화를 감지하기 위해 생성
 *
 * onNewToken() -> 토큰 값이 업데이트될 때 호출되는 콜백
 * Prefs의 fcmToken을 업데이트 해준다.
 * 이미 로그인되어 있는 경우 서버에 토큰 업데이트를 요청한다.
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
}