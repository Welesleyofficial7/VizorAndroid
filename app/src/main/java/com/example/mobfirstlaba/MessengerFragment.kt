package com.example.mobfirstlaba

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobfirstlaba.models.Chat
import com.example.mobfirstlaba.utils.ChatAdapter
import androidx.lifecycle.lifecycleScope
import com.example.mobfirstlaba.repository.CharacterRepository
import com.example.mobfirstlaba.utils.CharacterAdapter
import kotlinx.coroutines.launch

class MessengerFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var characterAdapter: CharacterAdapter
    private val characterRepository = CharacterRepository()
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

        fetchCharacters()

        return view

        return view
    }

    private fun fetchCharacters() {
        lifecycleScope.launch {
            val characters = characterRepository.getCharacters()
            if (characters != null && characters.isNotEmpty()) {
                characterAdapter.submitList(characters)
                Log.d("CharacterFragment", "Characters loaded: ${characters.size}")
            } else {
                Log.d("CharacterFragment", "No characters loaded or response is empty")
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