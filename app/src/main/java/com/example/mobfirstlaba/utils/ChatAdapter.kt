package com.example.mobfirstlaba.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobfirstlaba.R
import com.example.mobfirstlaba.models.Chat


class ChatAdapter(private val chatList: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.bind(chat)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val senderNameTextView: TextView = itemView.findViewById(R.id.senderName)
        private val senderIconImageView: ImageView = itemView.findViewById(R.id.senderIcon)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessage)
        private val timeOfLastMessageTextView: TextView = itemView.findViewById(R.id.timeOfLastMessage)

        fun bind(chat: Chat) {
            senderNameTextView.text = chat.senderName
            senderIconImageView.setImageResource(chat.senderIcon)
            lastMessageTextView.text = chat.lastMessage
            timeOfLastMessageTextView.text = chat.timeOfLastMessage
        }
    }
}
