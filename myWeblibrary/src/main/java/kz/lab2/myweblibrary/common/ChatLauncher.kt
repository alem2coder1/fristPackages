package kz.lab2.myweblibrary.common

import android.content.Context
import android.content.Intent
import kz.lab2.myweblibrary.activity.ChatActivity

object ChatLauncher {
    @JvmStatic
    fun start(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}