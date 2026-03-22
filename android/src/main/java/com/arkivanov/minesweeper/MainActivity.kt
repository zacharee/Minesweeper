package com.arkivanov.minesweeper

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.minesweeper.root.DefaultRootComponent
import com.arkivanov.minesweeper.root.RootContent
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer

class MainActivity : AppCompatActivity() {
    private val timeTravelServer by lazy { TimeTravelServer() }
    private val rootComponent by lazy {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle),
            storeFactory = DefaultStoreFactory(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RootContent(component = rootComponent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        timeTravelServer.stop()
    }
}