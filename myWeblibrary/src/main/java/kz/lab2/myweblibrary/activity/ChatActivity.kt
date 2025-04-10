package kz.lab2.myweblibrary.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import kz.lab2.myweblibrary.R
import kz.lab2.myweblibrary.common.ChatAdapter
import kz.lab2.myweblibrary.common.WebSocketManager
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response

class ChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<String>()

    private val wsListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            runOnUiThread {
                val displayText = if (text == "203 = 0xcb") "收到特殊指令消息" else text
                messages.add(displayText)
                chatAdapter.notifyItemInserted(messages.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val rvChat = findViewById<RecyclerView>(R.id.rvChat)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<Button>(R.id.btnSend)

        chatAdapter = ChatAdapter(messages)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = chatAdapter

        WebSocketManager.connect(wsListener)

        btnSend.setOnClickListener {
            val msg = etMessage.text.toString()
            if (msg.isNotEmpty()) {
                messages.add("我: $msg")
                chatAdapter.notifyItemInserted(messages.size - 1)
                WebSocketManager.sendMessage(msg)
                etMessage.text.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocketManager.close()
    }
}