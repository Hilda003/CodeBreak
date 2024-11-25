package com.example.codebreak

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.codebreak.databinding.ActivityRegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterScreen : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterScreenBinding

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db : FirebaseFirestore by lazy { Firebase.firestore }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignup.setOnClickListener {
            // Add your code here
            signUp()
        }
        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPass = binding.etConfirmPassword.text.toString()
        val terms = binding.cbTermsConditions.isChecked

//        Validation Access
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
           return
        }
        if(password != confirmPass){
            Toast.makeText(this, "Password and Confirm Password should be same", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.length < 6){
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }
        if(!terms){
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser?.uid
                    saveUser(user, name, email)

                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // ...
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    // ...
                    Toast.makeText(this, "User creation failed", Toast.LENGTH_SHORT).show()
                }
            }

    }


    private fun saveUser(userId: String?, name: String, email: String) {
        if(userId != null){
            val user = hashMapOf(
                "name" to name,
                "email" to email,
            )
            db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "User save failed", Toast.LENGTH_SHORT).show()
                }
        }

    }
}