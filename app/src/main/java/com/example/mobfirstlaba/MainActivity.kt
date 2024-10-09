package com.example.mobfirstlaba

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            navigateToOnboard()  // Начальный экран приложения - фрагмент Onboard
//        }
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

//    fun navigateFromSignUpToSignIn(bundle: Bundle?) {
//        val signInFragment = SignInFragment()
//        bundle?.let {
//            signInFragment.arguments = it
//        }
//
//        supportFragmentManager.commit {
//            replace(R.id.fragment_container, signInFragment)
//            addToBackStack(null)
//        }
//    }
//
//    fun navigateToSignIn() {
//        supportFragmentManager.commit {
//            replace<SignInFragment>(R.id.fragment_container)
//        }
//    }
//
//
//    fun navigateToSignUp() {
//        supportFragmentManager.commit {
//            replace<SignUpFragment>(R.id.fragment_container)
//            addToBackStack(null)
//        }
//    }
//
//    fun navigateToHome() {
//        supportFragmentManager.commit {
//            replace<MessengerFragment>(R.id.fragment_container)
//        }
//    }
//
//    fun navigateToOnboard() {
//        supportFragmentManager.commit {
//            replace<OnboardFragment>(R.id.fragment_container)
//        }
//    }
}
