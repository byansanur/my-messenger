package com.byandev.mymessenger.LoginRegister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.byandev.mymessenger.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnLogin.setOnClickListener {
            val emails = loginEmail.text.toString()
            val passs = loginPassword.text.toString()
            Log.d("BtnLogin", "email/password : $emails/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(emails, passs)
                .addOnCompleteListener{

                }
                .addOnFailureListener {

                }
        }
        SignUp.setOnClickListener {
            finish()
        }
    }
}
