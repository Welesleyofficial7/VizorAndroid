package com.example.mobfirstlaba

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.mobfirstlaba.data.UserPreferencesManager
import com.example.mobfirstlaba.utils.AppSettings
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var appSettings: AppSettings
    private lateinit var userPreferencesManager: UserPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appSettings = AppSettings(requireContext())
        userPreferencesManager = UserPreferencesManager(requireContext())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val fontSizeEditText = view.findViewById<EditText>(R.id.editTextFontSize)
        val notificationsSwitch = view.findViewById<Switch>(R.id.switchNotifications)
        val languageEditText = view.findViewById<EditText>(R.id.editTextLanguage)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)

        emailEditText.setText(appSettings.email)
        fontSizeEditText.setText(appSettings.fontSize.toString())
        languageEditText.setText(appSettings.language.toString())

        CoroutineScope(Dispatchers.Main).launch {
            userPreferencesManager.notificationsEnabled.collect {
                notificationsSwitch.isChecked = it
            }
//            userPreferencesManager.language.collect { language ->
//                languageEditText.setText(language ?: "")
//                Log.d("SETTINGS",language?:"");
//            }
        }

        saveButton.setOnClickListener {
            appSettings.email = emailEditText.text.toString()
            appSettings.fontSize = fontSizeEditText.text.toString().toFloatOrNull() ?: 14f
            appSettings.language = languageEditText.text.toString()
            Log.d("MAIN",emailEditText.text.toString())
            Log.d("MAIN",languageEditText.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                userPreferencesManager.setNotificationsEnabled(notificationsSwitch.isChecked)
//                userPreferencesManager.setLanguage("en")
            }
        }

        return view
    }
}
