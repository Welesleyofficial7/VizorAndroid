package com.example.mobfirstlaba
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobfirstlaba.MainActivity
import com.example.mobfirstlaba.MessengerFragment
import com.example.mobfirstlaba.R
import com.example.mobfirstlaba.SignUpFragment
import com.example.mobfirstlaba.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment() {
    private val users = mutableListOf<User>()

    private val TAG = "AuthActivity"

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        Log.d(TAG, "onCreateView called")
        initializeUsers()
        arguments?.let {
            val args: SignInFragmentArgs by navArgs()
            val user = args.user
            if (user != null) {
                users.add(user)
            }
            val emailTextEdit: TextInputEditText = view.findViewById(R.id.email)
            val passwordTextEdit: TextInputEditText = view.findViewById(R.id.password)
            val usernameTextEdit: TextInputEditText = view.findViewById(R.id.username)

            emailTextEdit.setText(user?.email)
            passwordTextEdit.setText(user?.password)
            usernameTextEdit.setText(user?.username)
        }

        val loginButton: MaterialButton = view.findViewById(R.id.login_button)
        val signUpButton: MaterialButton = view.findViewById(R.id.signup_button)

        loginButton.setOnClickListener {
            val email = (view.findViewById(R.id.email) as TextInputEditText).text.toString().trim()
            val password = (view.findViewById(R.id.password) as TextInputEditText).text.toString().trim()
            val username = (view.findViewById(R.id.username) as TextInputEditText).text.toString().trim()

            if (validateEmail(email) && validatePassword(password) && validateUsername(username)) {
                val user = User(email, password, username)

                if (checkUser(user)) {
                    showToast("Авторизация успешно пройдена!")
                    findNavController().navigate(R.id.action_signInFragment_to_messengerFragment)
                } else {
                    showToast("Неверный логин или пароль!")
                }
            }
        }

        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return view
    }

    private fun initializeUsers() {
        users.add(User(email = "alex4002@ya.ru", password = "alex4002", username = "Alex"))
        users.add(User("ivan3002@ya.ru", "ivan3002","Ivan"))
    }

    private fun checkUser(user: User): Boolean {
        return users.any { it.email == user.email && it.password == user.password }
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
