package com.interneted.loginplugin.library.loginPlatform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 *@date: 2019/3/13 - 上午 09:53
 *@author: Ed
 *@email: salahayo3192@gmail.com
 **/


interface LoginPlatform {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun logIn(activity: AppCompatActivity, callback: LogInCallback)
    fun logOut(activity: AppCompatActivity, callback: LogOutCallback)


    interface LogInCallback {
        fun onSuccess(token: String)
        fun onFailure()
    }

    interface LogOutCallback {
        fun onSuccess()
        fun onFailure()
    }
}
