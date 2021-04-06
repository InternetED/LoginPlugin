package com.interneted.loginplugin.library.loginPlatform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.interneted.loginplugin.library.LoginPlatformProvide
import com.interneted.loginplugin.library.R
import com.interneted.loginplugin.library.logd
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.api.LineApiClientBuilder
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *@date: 2019/3/13 - 上午 09:59
 *@author: Ed
 *@email: salahayo3192@gmail.com
 **/
class LineLoginPlatform : LoginPlatform {


    private var callback: LoginPlatform.LogInCallback? = null


    override fun logIn(activity: AppCompatActivity, callback: LoginPlatform.LogInCallback) {


        val loginIntent = LineLoginApi.getLoginIntent(
            activity, getLintChannelID(activity), LineAuthenticationParams.Builder()
                .scopes(arrayListOf(Scope.PROFILE))
                .build()
        )

        this.callback = callback

        activity.startActivityForResult(loginIntent, LoginPlatformProvide.LINE)

    }


    override fun logOut(activity: AppCompatActivity, callback: LoginPlatform.LogOutCallback) {
        val lineApiClient = LineApiClientBuilder(activity, getLintChannelID(activity))
            .build()



        activity.lifecycleScope.launch(Dispatchers.IO) {
            val lineApiResponse = lineApiClient.logout()

            if (lineApiResponse.isSuccess) {
                logd("Line logout has success.")
                callback.onSuccess()
            } else {
                logd("Line logout has failure. error：${lineApiResponse.errorData}")
                callback.onFailure()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = LineLoginApi.getLoginResultFromIntent(data)



        if (result.isSuccess) {
            // 登入成功

            val token = result.lineCredential?.accessToken?.tokenString ?: ""
            logd("Line login has success. token：$token")

            callback?.onSuccess(token)

        } else {
            // 登入失敗
            logd("Line login has failure. error：${result.errorData}")
        }

        callback = null // 移除引用

    }


    /**
     * 若拋出錯誤，代表尚未註冊 Ling channel id，
     * 請至 res/values/strings 中設定 login_line_channelId
     *
     * @throws NotFoundException
     * */
    private fun getLintChannelID(context: Context): String {
        return context.getString(R.string.login_line_channelId)
    }
}