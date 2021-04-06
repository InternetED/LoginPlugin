package com.interneted.loginplugin.library

import android.util.Log
import com.facebook.internal.CallbackManagerImpl
import com.interneted.loginplugin.library.loginPlatform.FacebookLoginPlatform
import com.interneted.loginplugin.library.loginPlatform.GoogleLoginPlatform
import com.interneted.loginplugin.library.loginPlatform.LineLoginPlatform
import com.interneted.loginplugin.library.loginPlatform.LoginPlatform

/**
 *@date: 2019/4/18 - 上午 10:15
 *@author: Ed
 *@email: salahayo3192@gmail.com
 **/
class LoginPlatformProvide {
    companion object {
        const val GOOGLE = 300
        val FACEBOOK = CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode() // 64206
        const val LINE = 500
    }

    private val platformMap = mutableMapOf<Int, LoginPlatform>()


    fun getLoginPlatform(type: Int): LoginPlatform {

        if (platformMap.containsKey(type))
            return platformMap.getValue(type)

        val loginPlatform = when (type) {
            GOOGLE -> GoogleLoginPlatform()
            FACEBOOK -> FacebookLoginPlatform()
            LINE -> LineLoginPlatform()
            else -> {
                throw NullPointerException("尚未支援該登入模式,type:$type")
            }
        }
        platformMap[type] = loginPlatform

        return loginPlatform
    }


}


fun logd(msg: String) {
    Log.d("LoginPlugin", msg)
}