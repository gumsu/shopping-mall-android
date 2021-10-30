package com.gdh.shoppingmall.fcm

import com.gdh.shoppingmall.common.Prefs
import java.util.concurrent.Semaphore

/**
 * notification Id를 순차적으로 증가시켜 반환하는 클래스
 *
 * id 중복을 방지하기 위해 값을 변경하는 부분을 하나짜리 세마포어로 감싼다.
 * 앱이 초기화 되면 Prefs로 부터 저장되어 있던 id를 미리 가져온다.
 *
 * lock.acquire() -> id 값을 변경하는 로직 진입 시 다른 스레드가 변경하지 못하도록 막는다.
 * 다음 값을 반환할 때 id 값이 변경될 수 있으므로 임시변수인 next 사용한다.
 * lock.release() -> Pref에 다음 값을 저장해두고, 로직이 끝나면 세마포어 락을 풀어준다.
 * id는 변경 위험이 있으므로 next를 반환환
*/

class NotificationId {
    companion object {
        private val lock = Semaphore(1)
        private var id = Prefs.notificationId

        fun generate(): Int {
            lock.acquire()
            val next = id + 1
            id = next
            Prefs.notificationId = next
            lock.release()
            return next
        }
    }
}