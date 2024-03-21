package ai.nextbillion.maps.compose_demo

import ai.nextbillion.maps.compose_demo.ui.OverlayScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class OverlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OverlayScreen()
        }
    }
}