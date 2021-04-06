# LoginPlugin
Used to  login on third login platform collection.



Step.1

If you want to use login platform take access token,you need set string value in res/strings.


    <!--Google Login-->
    <string name="login_google_serverId" translatable="false" />
    
    <!-- Facebook Login -->
    <string name="login_facebook_applicationId" translatable="false" />
    
    <!--Line Login-->
    <string name="login_line_channelId" translatable="false" />
    
    

Step.2

```
    val loginPlatformType = LoginPlatformProvide.GOOGLE
//    val loginPlatformType = LoginPlatformProvide.FACEBOOK
//    val loginPlatformType = LoginPlatformProvide.LINE
    
    LoginManager.getInstance()
        .login(activity,loginPlatformType,object :LoginPlatform.LogInCallback{
            override fun onSuccess(token: String) {
                // operation
            }
            override fun onFailure() {
                // take access token happened error.
            }
        })
```
