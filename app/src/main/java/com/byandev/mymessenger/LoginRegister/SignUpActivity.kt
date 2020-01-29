package com.byandev.mymessenger.LoginRegister

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.byandev.mymessenger.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        val uname = etUName.text.toString()

        btnRegister.setOnClickListener {
            createUsers()
        }

        textSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        selectPhoto.setOnClickListener {
            selectPhotoProfile()
        }

    }

    private fun selectPhotoProfile() {
        Log.d("selectPP", "try to select pp")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if  (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        {
            Log.d("selectPp", "Photo was selector")

            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            selectPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun createUsers() {

        // TODO#1 initialize your id on layout
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()

        Log.d("Context", "Emails is : "+email)
        Log.d("Context", "Password is : $pass")

        // TODO#2 Check your input
        if (email.isEmpty() || pass.isEmpty())
        {
            Toast.makeText(this, "Insert your data, please...!", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO#3 Create user with email and password Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d("Create users", "successful create users with uid : ${it.result!!.user!!.uid}")
            }
            .addOnFailureListener{
                Log.d("Create users", "Failed to create user : ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }
}
