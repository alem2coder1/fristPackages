package kz.lab2.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kz.lab2.myweblibrary.common.WebSocketManager
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            WebSocketManager.connect(wsListener)
        }
    }

    private val wsListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("WebSocket", "连接成功")
            WebSocketManager.sendMessage("zhanel")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("WebSocket", "收到消息：$text")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("WebSocket", "连接关闭")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("WebSocket", "连接错误", t)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocketManager.close()
    }
}