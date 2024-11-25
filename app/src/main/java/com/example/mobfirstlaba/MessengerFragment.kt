package com.example.mobfirstlaba

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobfirstlaba.models.Chat
import com.example.mobfirstlaba.utils.ChatAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobfirstlaba.models.CharacterModel
import com.example.mobfirstlaba.repository.CharacterRepository
import com.example.mobfirstlaba.utils.CharacterAdapter
import kotlinx.coroutines.launch
import java.io.File

class MessengerFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var characterRepository: CharacterRepository
    private val TAG = "MessengerActivity"

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_messenger, container, false)
        Log.d(TAG, "onCreate called")
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        characterRepository = CharacterRepository(requireContext())

        val nextButton = view.findViewById<Button>(R.id.buttonGoToHome)
        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_messengerFragment_to_settingsFragment)
        }

        recyclerView = view.findViewById(R.id.recyclerView)

        val chatList = listOf(
            Chat("Alice", R.drawable.user_icon, "Hey there!", "10:00 AM"),
            Chat("Bob", R.drawable.user_icon, "How's it going?", "10:05 AM"),
        )

//        chatAdapter = ChatAdapter(chatList)
//        recyclerView.adapter = chatAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        characterAdapter = CharacterAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = characterAdapter

        val updateButton = view.findViewById<Button>(R.id.buttonUpdateCharacters)
        updateButton.setOnClickListener {
            fetchCharactersFromApi()
        }

        val pageInput = view.findViewById<EditText>(R.id.pageInput)
        val goToPageButton = view.findViewById<Button>(R.id.buttonGoToPage)

        goToPageButton.setOnClickListener {
            val page = pageInput.text.toString().toIntOrNull() ?: 1
            lifecycleScope.launch {
                val characters = characterRepository.getCharactersByPage(page)
                if (!characters.isNullOrEmpty()) {
                    characterRepository.saveCharactersToDb(characters)
                } else {
                    Log.d(TAG, "No characters fetched for page $page")
                }
            }
        }


        fetchCharacters()

        return view
    }



//    private fun saveHeroesToExternalStorage(heroes: List<CharacterModel>) {
//        val heroNames = heroes.map { it.name } // Assuming `CharacterModel` has a `name` field
//        val fileName = "heroes_1.txt"
//        val repository = CharacterRepository()
//        if (repository.saveHeroesToFile(requireContext(), heroNames, fileName)) {
//            Log.d("SettingsFragment", "File $fileName successfully saved.")
//        } else {
//            Log.e("SettingsFragment", "Failed to save file $fileName.")
//        }
//    }

    private fun observeCharacters() {
        lifecycleScope.launch {
            characterRepository.observeCharacters().collect { characterEntities ->
                val characters = characterEntities.map { entity ->
                    CharacterModel(
                        name = entity.name,
                        culture = entity.culture,
                        born = entity.born,
                        titles = entity.titles?.split(",") ?: emptyList(),
                        aliases = entity.aliases?.split(",") ?: emptyList(),
                        playedBy = entity.playedBy?.split(",") ?: emptyList()
                    )
                }
                characterAdapter.submitList(characters)
                Log.d(TAG, "Characters updated: ${characters.size}")
            }
        }
    }


//    private fun fetchCharacters() {
//        lifecycleScope.launch {
//            try {
//                val characters = characterRepository.getCharacters()
//                characters?.let {
//                    characterRepository.saveCharactersToDb(it) // Сохранение в локальную БД
//                }
//
//                characterRepository.getCharactersFromDb().collect { localCharacters ->
//                    characterAdapter.submitList(localCharacters.map { entity ->
//                        CharacterModel(
//                            name = entity.name,
//                            culture = entity.culture,
//                            born = entity.born,
//                            titles = entity.titles?.split(",") ?: emptyList(),
//                            aliases = entity.aliases?.split(",") ?: emptyList(),
//                            playedBy = entity.playedBy?.split(",") ?: emptyList()
//                        )
//                    })
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error fetching characters: ${e.message}")
//            }
//        }
//    }

    private fun fetchCharacters() {
        lifecycleScope.launch {
            characterRepository.getCharactersFromDb().collect { charactersInDb ->
                if (charactersInDb.isNotEmpty()) {
                    observeCharacters()
                } else {
                    fetchCharactersFromApi()
                }
            }
        }
    }

    private fun fetchCharactersFromApi() {
        lifecycleScope.launch {
            val characters = characterRepository.getCharacters()
            if (!characters.isNullOrEmpty()) {
                characterRepository.saveCharactersToDb(characters)
                Log.d(TAG, "Characters fetched from API: ${characters.size}")
            } else {
                Log.d(TAG, "No characters fetched from API")
            }
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

}