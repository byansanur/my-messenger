package com.byandev.mymessenger.LoginRegister

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.byandev.mymessenger.Message.MessageActivity
import com.byandev.mymessenger.Model.User
import com.byandev.mymessenger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val uname = etUName.text.toString()

        btnRegister.setOnClickListener {
            createUsers()
        }

        textSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        selectPhoto.setOnClickListener {
            Log.d("selectPP", "try to select pp")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if  (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        {
            Log.d("selectPp", "Photo was selector")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            profile_image.setImageBitmap(bitmap)

            selectPhoto.alpha = 0f
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
                uploadImageFirebaseStorage()
            }
            .addOnFailureListener{
                Log.d("Create users", "Failed to create user : ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageFirebaseStorage() {
        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("this", "Success upload images : ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("download", "file location: $it")
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{

            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, etUName.text.toString(), profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Success", "users save to database firebase")

                val intent = Intent(this, MessageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK )
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("Gagal", "Gagal membuat db")
            }

    }
}

//@Parcelize
//class User(val uid: String, val username: String, val profileImageUrl: String): Parcelable {
//    constructor(): this("","","")
//}
