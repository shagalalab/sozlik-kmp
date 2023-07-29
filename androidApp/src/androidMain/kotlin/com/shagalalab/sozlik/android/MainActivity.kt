package com.shagalalab.sozlik.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.shagalalab.sozlik.shared.MainView
import com.shagalalab.sozlik.shared.domain.component.root.RootComponentImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = RootComponentImpl(defaultComponentContext())

        setContent {
            MainView(rootComponent)
        }
    }
}
