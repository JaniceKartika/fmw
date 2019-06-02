package com.jkm.findmyway.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jkm.findmyway.model.User
import java.lang.ref.WeakReference
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    private val activity: WeakReference<Activity>,
    private val view: AuthContract.View
) : AuthContract.Presenter {

    companion object {
        private const val RC_SIGN_IN = 1
    }

    private var client: GoogleSignInClient? = null

    override fun prepareSignIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        activity.get()?.let { client = GoogleSignIn.getClient(it, options) }
    }

    override fun checkSignIn() {
        if (activity.get() != null) {
            val account = GoogleSignIn.getLastSignedInAccount(activity.get())
            if (account != null) view.onSucceedSignIn(getUser(account))
        }
    }

    override fun requestSignIn() {
        val signInIntent = client?.signInIntent
        activity.get()?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun handleSignInRequest(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                view.onSucceedSignIn(getUser(account))
            } catch (e: ApiException) {
                view.onFailedSignIn(e.message ?: "")
            }
        }
    }

    private fun getUser(account: GoogleSignInAccount?): User {
        return User(account?.displayName ?: "", account?.email ?: "")
    }
}
