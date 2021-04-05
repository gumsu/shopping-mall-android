package com.gdh.shoppingmall

import android.app.Application

/**
 * 안드로이드에는 애플리케이션이 실행될 때 Application이라는 클래스의 전역 객체가 만들어진다.
 * 이 클래스는 애플리케이션의 전역적인 상태를 관리하는 클래스로,
 * 이 클래스를 상속받아 AndroidManifest에 지정해주면 우리가 필요한 값들도 이 클래스를 통해
 * 전역적으로 공유할 수 있다.
 * 애플리케이션 컨텍스트를 이용하기 위해 커스텀 클래스 작성.
 *
 * Application 클래스는 Context 클래스를 상속받고 있으므로 context가 필요한 곳에
 * 이 클래스의 인스턴스를 사용할 수 있다. 따라서 instance라는 정적 필드를 만들고
 * lateinit 키워드로 지연 초기화를 사용해 onCreate() 객체에 넣어준다.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance : App
    }
}