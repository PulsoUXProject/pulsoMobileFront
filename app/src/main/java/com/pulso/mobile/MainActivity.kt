package com.pulso.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pulso.mobile.ui.navigation.PulsoNavHost
import com.pulso.mobile.ui.theme.PulsoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PulsoTheme {
                PulsoNavHost()
            }
        }
    }
}
