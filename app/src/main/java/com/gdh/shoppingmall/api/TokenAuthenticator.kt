package com.gdh.shoppingmall.api

import com.gdh.shoppingmall.api.response.ApiResponse
import com.gdh.shoppingmall.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * API의 응답 코드가 401인 경우 토큰 갱신 후 API 요청을 재시도하게 만들어주는 역할
 *
 * 응답 코드가 401인 경우 토큰 갱신 로직으로 진입하도록 하고 있다.
 * API가 suspend 함수이므로 runBlocking {} 을 이용해 코루틴을 실행시켜준다.
 * runBlocking 함수는 블록의 마지막 줄을 반환값으로 사용한다.
 * 토큰 갱신이 성공할 경우 새 토큰을 덮어 씌워준다.
 * 토큰 갱신이 실패할 경우 로그아웃 처리를 위해 기존 토큰을 제거한다.
 */

class TokenAuthenticator: Authenticator, AnkoLogger {

    override fun authenticate(route: Route?, response: Response): Request? {
        if(response.code() == 401) {
            debug("토큰 갱신 필요")
            return runBlocking {
                val tokenResponse = refreshToken()  // 토큰 갱신 API 호출

                if (tokenResponse.success) {
                    debug("토큰 갱신 성공")
                    Prefs.token = tokenResponse.data
                } else {
                    error("토큰 갱신 실패")
                    Prefs.token = null
                    Prefs.refreshToken = null
                }

                // 토큰이 성공적으로 갱신된 경우 새 토큰으로 새 요청을 만들어 반환한다.
                Prefs.token?.let { token ->
                    debug("토큰 = $token")
                    response.request()
                        .newBuilder()
                        .header("Authorization", token)
                        .build()
                }
            }
        }

        return null
    }

    private suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            try {
                ParayoRefreshApi.instance.refreshToken()
            } catch (e: Exception) {
                ApiResponse.error<String>("인증 실패")
            }
        }
}