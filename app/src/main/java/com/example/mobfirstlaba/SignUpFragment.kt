package com.example.mobfirstlaba

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.mobfirstlaba.R.*
import com.example.mobfirstlaba.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    private val TAG = "SignUpActivity"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(layout.fragment_sign_up, container, false)

        Log.d(TAG, "onCreateView called")

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailTextEdit: TextInputEditText = view.findViewById(R.id.email)
        val usernameTextEdit: TextInputEditText = view.findViewById(R.id.username)
        val passwordTextEdit: TextInputEditText = view.findViewById(R.id.password)
        val signUpButton: MaterialButton = view.findViewById(R.id.signup_button)

        signUpButton.setOnClickListener{

            val email = emailTextEdit.text.toString().trim()
            val password = passwordTextEdit.text.toString().trim()
            val username = usernameTextEdit.text.toString().trim()

            if (validateEmail(email) && validatePassword(password) && validateUsername(username)) {
//                val bundle = Bundle().apply {
//                    putString("email", email)
//                    putString("username", username)
//                    putString("password", password)
//                }
                val user = User(email, password, username)
                val bundle = bundleOf("user" to user)
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment, bundle)
            }

        }
        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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