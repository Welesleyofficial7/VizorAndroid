package com.example.mobfirstlaba

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobfirstlaba.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.sign

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailTextEdit: TextInputEditText = findViewById(R.id.email)
        val usernameTextEdit: TextInputEditText = findViewById(R.id.username)
        val passwordTextEdit: TextInputEditText = findViewById(R.id.password)
        val signUpButton: MaterialButton = findViewById(R.id.signup_button)

        signUpButton.setOnClickListener{

            val email = emailTextEdit.text.toString().trim()
            val password = passwordTextEdit.text.toString().trim()
            val username = usernameTextEdit.text.toString().trim()

            if (validateEmail(email) && validatePassword(password) && validateUsername(username)) {
                val user = User(email, password, username)
                val intent = Intent()
                intent.putExtra("user", user)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            showToast("Почта не может быть пустой!")
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Неправильный формат почты!")
            return false
        } else {
            return true
        }
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            showToast("Пароль не может быть пустой!")
            return false
        } else if (password.length < 6) {
            showToast("Длина пароля должна быть длиннее 8 символов!")
            return false
        } else {
            return true
        }
    }

    private fun validateUsername(username: String): Boolean {
        if (username.isEmpty()) {
            showToast("Имя пользователя не может быть пустым!")
            return false
        } else if (username.length < 3) {
            showToast("Имя пользователя должно быть длиннее 2 символов")
            return false
        } else {
            return true
        }
    }
}