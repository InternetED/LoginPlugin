package com.interneted.loginplugin.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.interneted.loginplugin.library.loginPlatform.LoginPlatform

/**
 *@date: 2019/3/13 - 上午 10:23
 *@author: Ed
 *@email: salahayo3192@gmail.com
 **/
class LoginManager private constructor() {

    private val factoryProvide = LoginPlatformProvide()

    fun login(activity: AppCompatActivity, type: Int, callback: LoginPlatform.LogInCallback) {
        getLoginPlatform(type).logIn(activity, callback)
    }

    fun logOut(activity: AppCompatActivity, type: Int, callback: LoginPlatform.LogOutCallback) {
        getLoginPlatform(type).logOut(activity, callback)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getLoginPlatform(requestCode).onActivityResult(requestCode, resultCode, data)
    }

    private fun getLoginPlatform(type: Int) =
        factoryProvide.getLoginPlatform(type)


    companion object {

        private val INSTANCE by lazy { LoginManager() }

        fun getInstance() = INSTANCE

    }
}