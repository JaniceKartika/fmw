package com.jkm.findmyway.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.SignInButton
import com.jkm.findmyway.R
import com.jkm.findmyway.contract.AuthContract
import com.jkm.findmyway.model.User
import com.jkm.findmyway.presenter.AuthPresenter
import kotlinx.android.synthetic.main.activity_auth.*
import java.lang.ref.WeakReference

class AuthActivity : AppCompatActivity(), AuthContract.View, View.OnClickListener {

    private var presenter: AuthContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initViews()

        presenter = AuthPresenter(WeakReference(this), this)
        presenter?.prepareSignIn()
    }

    override fun onStart() {
        super.onStart()
        presenter?.checkSignIn()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btSignIn) {
            presenter?.requestSignIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter?.handleSignInRequest(requestCode, data)
    }

    override fun onSucceedSignIn(user: User) {
        Toast.makeText(this, "Logged in: ${user.name}, email:${user.email}", Toast.LENGTH_LONG).show()
    }

    override fun onFailedSignIn(message: String) {
        Toast.makeText(this, "Failed: $message", Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        btSignIn.setSize(SignInButton.SIZE_STANDARD)
        btSignIn.setOnClickListener(this)
    }
}
