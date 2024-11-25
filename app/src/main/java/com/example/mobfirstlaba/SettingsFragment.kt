package com.example.mobfirstlaba

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobfirstlaba.data.UserPreferencesManager
import com.example.mobfirstlaba.models.CharacterModel
import com.example.mobfirstlaba.repository.CharacterRepository
import com.example.mobfirstlaba.utils.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class SettingsFragment : Fragment() {

    private lateinit var appSettings: AppSettings
    private lateinit var userPreferencesManager: UserPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appSettings = AppSettings(requireContext())
        userPreferencesManager = UserPreferencesManager(requireContext())
    }

    fun isFileExists(fileName: String): Boolean {
        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalStorage, fileName)
        return file.exists()
    }

    fun deleteFile(fileName: String): Boolean {
        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalStorage, fileName)
        return file.delete()
    }

    fun isFileInInternalStorage(fileName: String): Boolean {
        val internalStorage = requireContext().filesDir
        val internalFile = File(internalStorage, fileName)
        return internalFile.exists()
    }


    fun backupFileToInternalStorage(fileName: String): Boolean {
        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val externalFile = File(externalStorage, fileName)

        if (!externalFile.exists()) return false

        val internalStorage = requireContext().filesDir
        val internalFile = File(internalStorage, fileName)

        return try {
            externalFile.copyTo(internalFile, overwrite = true)
            Log.d("MessengerFragment", "Backup created: $fileName")
            true
        } catch (e: Exception) {
            Log.e("MessengerFragment", "Failed to backup file: $fileName", e)
            false
        }
    }

    fun restoreFileFromInternalStorage(fileName: String): Boolean {
        val internalStorage = requireContext().filesDir
        val internalFile = File(internalStorage, fileName)

        if (!internalFile.exists()) return false

        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val externalFile = File(externalStorage, fileName)

        return try {
            internalFile.copyTo(externalFile, overwrite = true)
            Log.d("MessengerFragment", "File restored: $fileName")
            true
        } catch (e: Exception) {
            Log.e("MessengerFragment", "Failed to restore file: $fileName", e)
            false
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val fileInfoTextView = view.findViewById<TextView>(R.id.textFileInfo)
        val deleteFileButton = view.findViewById<Button>(R.id.buttonDeleteFile)
        val restoreFileButton = view.findViewById<Button>(R.id.buttonRestoreFile)

        val fileName = "heroes_${1}.txt"
        val fileExistsInExternal = isFileExists(fileName)
        val fileExistsInInternal = isFileInInternalStorage(fileName)

        if (fileExistsInExternal) {
            deleteFileButton.isEnabled = true
            fileInfoTextView.setText("Файл существует!")
            restoreFileButton.isEnabled = false
            deleteFileButton.visibility = View.VISIBLE
            restoreFileButton.visibility = View.GONE
        } else if (fileExistsInInternal) {
            deleteFileButton.isEnabled = false
            fileInfoTextView.setText("Файл удален с возможностью восстановления!")
            restoreFileButton.isEnabled = true
            deleteFileButton.visibility = View.GONE
            restoreFileButton.visibility = View.VISIBLE
        } else {
            deleteFileButton.isEnabled = false
            fileInfoTextView.setText("Явно какие-то проблемы!")
            restoreFileButton.isEnabled = false
            deleteFileButton.visibility = View.GONE
            restoreFileButton.visibility = View.GONE
        }

        deleteFileButton.setOnClickListener {
            if (backupFileToInternalStorage(fileName) && deleteFile(fileName)) {
                fileInfoTextView.text = "Файл $fileName удалён."
                deleteFileButton.isEnabled = false
                restoreFileButton.isEnabled = true
                restoreFileButton.visibility = View.VISIBLE
            } else {
                fileInfoTextView.text = "Не удалось удалить файл $fileName."
            }
        }

//        if (isFileInInternalStorage(fileName)) {
//            restoreFileButton.visibility = View.VISIBLE
//            restoreFileButton.isEnabled = true
//        } else {
//            restoreFileButton.visibility = View.GONE
//            restoreFileButton.isEnabled = false
//        }

        restoreFileButton.setOnClickListener {
            if (restoreFileFromInternalStorage(fileName)) {
                fileInfoTextView.text = "Файл $fileName восстановлен."
                restoreFileButton.visibility = View.GONE
                restoreFileButton.isEnabled = false
                deleteFileButton.isEnabled = true
            } else {
                fileInfoTextView.text = "Не удалось восстановить файл $fileName."
            }
        }

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
