package com.jkm.findmyway.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.SignInButton
import com.jkm.findmyway.R
import com.jkm.findmyway.databinding.ActivityAuthBinding
import com.jkm.findmyway.model.User
import java.lang.ref.WeakReference
import javax.inject.Inject

class AuthActivity : AppCompatActivity(), AuthContract.View, View.OnClickListener {

    @Inject
    lateinit var presenter: AuthPresenter

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        DaggerAuthComponent.builder()
            .authPresenterModule(AuthPresenterModule(WeakReference(this), this))
            .build().inject(this)
        presenter.prepareSignIn()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkSignIn()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btSignIn) {
            presenter.requestSignIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.handleSignInRequest(requestCode, data)
    }

    override fun onSucceedSignIn(user: User) {
        Toast.makeText(this, "Logged in: ${user.name}, email:${user.email}", Toast.LENGTH_LONG).show()
    }

    override fun onFailedSignIn(message: String) {
        Toast.makeText(this, "Failed: $message", Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        binding.apply {
            btSignIn.setSize(SignInButton.SIZE_STANDARD)
            btSignIn.setOnClickListener(this@AuthActivity)
        }
    }
}
