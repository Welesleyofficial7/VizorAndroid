package com.example.mobfirstlaba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobfirstlaba.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AuthActivity : AppCompatActivity() {

    private val users = mutableListOf<User>()

    private val TAG = "AuthActivity"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeUsers()

//        val email: String = findViewById<TextInputEditText>(R.id.email).text.toString().trim()
//        val password: String = findViewById<TextInputEditText>(R.id.password).text.toString().trim()

        val emailTextEdit: TextInputEditText = findViewById(R.id.email)
        val passwordTextEdit: TextInputEditText = findViewById(R.id.password)
        val usernameTextEdit: TextInputEditText = findViewById(R.id.username)
        val button: MaterialButton = findViewById(R.id.login_button)
        val signUpButton: MaterialButton = findViewById(R.id.signup_button)

        button.setOnClickListener{

            val email = emailTextEdit.text.toString().trim()
            val password = passwordTextEdit.text.toString().trim()
            val username = usernameTextEdit.text.toString().trim()

            if (validateEmail(email) && validatePassword(password) && validateUsername(username)) {
                val user = User(email, password, username)

                if (checkUser(user)) {
                    showToast("Авторизация успешно пройдена!")
                    val intent = Intent(this, MessengerActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    showToast("Неверный логин или пароль!")
                }
            }
        }

        signUpButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, SIGN_UP_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_UP_REQUEST_CODE && resultCode == RESULT_OK) {
            val user: User? = data?.getParcelableExtra("user")
            val emailTextEdit: TextInputEditText = findViewById(R.id.email)
            val passwordTextEdit: TextInputEditText = findViewById(R.id.password)
            val usernameTextEdit: TextInputEditText = findViewById(R.id.username)
            user?.let {
                emailTextEdit.setText(it.email)
                passwordTextEdit.setText(it.password)
                usernameTextEdit.setText(it.username)
            }
        }
    }


    companion object {
        const val SIGN_UP_REQUEST_CODE = 1
    }


    private fun initializeUsers() {
        users.add(User(email = "alex4002@ya.ru", password = "alex4002", username = "Alex"))
        users.add(User("ivan3002@ya.ru", "ivan3002","Ivan"))
    }

    private fun checkUser(user: User): Boolean {
        return users.any { it.email == user.email && it.password == user.password}
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateEmail(email: String): Boolean {
        when {
            email.isEmpty() -> {
                showToast("Почта не может быть пустой!")
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Неправильный формат почты!")
                return false
            }
            else -> {
                return true
            }
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
    }

}