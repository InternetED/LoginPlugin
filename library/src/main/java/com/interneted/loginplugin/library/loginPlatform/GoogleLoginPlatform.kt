package com.interneted.loginplugin.library.loginPlatform

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.interneted.loginplugin.library.LoginPlatformProvide
import com.interneted.loginplugin.library.R
import com.interneted.loginplugin.library.logd


/**
 *@date: 2019/3/13 - 上午 10:08
 *@author: Ed
 *@email: salahayo3192@gmail.com
 **/
class GoogleLoginPlatform : LoginPlatform {


    private var logInCallback: LoginPlatform.LogInCallback? = null


    override fun logIn(activity: AppCompatActivity, callback: LoginPlatform.LogInCallback) {

        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(activity)
        if (lastSignedInAccount != null && lastSignedInAccount.isExpired) {

            logd("Google has account. account：${lastSignedInAccount} isExpired：${lastSignedInAccount.isExpired}")

            // 避免重複登入
            logOut(activity, object : LoginPlatform.LogOutCallback {
                override fun onSuccess() {
                    logIn(activity, callback)
                }

                override fun onFailure() {
                    logIn(activity, callback)
                }

            })
            return
        }

        val googleSignIntent =
            GoogleSignIn.getClient(activity, getGoogleSignInOptions(activity)).signInIntent


        this.logInCallback = callback

        activity.startActivityForResult(googleSignIntent, LoginPlatformProvide.GOOGLE)
    }

    override fun logOut(activity: AppCompatActivity, callback: LoginPlatform.LogOutCallback) {

        GoogleSignIn.getClient(activity, getGoogleSignInOptions(activity))
            .signOut()
            .addOnSuccessListener {
                logd("Google logout has success.")
                callback.onSuccess()
            }
            .addOnFailureListener {
                logd("Google logout has failure. error：$it")
                callback.onFailure()
            }
            .addOnCanceledListener {
                logd("Google logout has cancel.")
            }
            .addOnCompleteListener {
                logd("Google logout has complete.")
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data)

        signedInAccountFromIntent
            .addOnSuccessListener {
                logInCallback?.onSuccess(it.serverAuthCode ?: "")
                logd("Google login has success. token：${it.serverAuthCode}")
            }
            .addOnFailureListener {
                logd("Google login has failure. error：${it}")
                logInCallback?.onFailure()
            }
            .addOnCanceledListener {
                logd("Google login has cancel.")
            }
            .addOnCompleteListener {
                logd("Google login has complete.")
                logInCallback = null // 移除引用
            }

    }

    /**
     * 若拋出錯誤，代表尚未註冊 Google Server id，
     * 請至 res/values/strings 中設定 login_google_serverId
     *
     * @throws NotFoundException
     * */
    private fun getGoogleSignInOptions(context: Context): GoogleSignInOptions {
        val serverAuthCode = context.getString(R.string.login_google_serverId)

        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(serverAuthCode)
            .requestProfile()
            .build()
    }

}