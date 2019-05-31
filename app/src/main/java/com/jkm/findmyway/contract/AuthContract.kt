package com.jkm.findmyway.contract

import android.content.Intent
import com.jkm.findmyway.model.User

interface AuthContract {
    interface View {
        fun onSucceedSignIn(user: User)
        fun onFailedSignIn(message: String)
    }

    interface Presenter {
        fun prepareSignIn()
        fun checkSignIn()
        fun requestSignIn()
        fun handleSignInRequest(requestCode: Int, data: Intent?)
    }
}
