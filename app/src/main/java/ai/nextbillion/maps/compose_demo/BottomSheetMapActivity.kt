package ai.nextbillion.maps.compose_demo

import ai.nextbillion.maps.compose_demo.ui.BottomSheetScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class BottomSheetMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetScreen()
        }
    }
} 