package kz.lab2.myweblibrary.common

import okhttp3.OkHttpClient
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Request

object WebSocketManager {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun connect(listener: WebSocketListener) {
        val request = Request.Builder()
            .url("wss://echo.websocket.events")
            .build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(msg: String) {
        webSocket?.send(msg)
    }

    fun close() {
        webSocket?.close(1000, "Activity closed")
    }
}