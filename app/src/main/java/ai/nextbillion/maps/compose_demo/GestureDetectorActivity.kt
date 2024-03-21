package ai.nextbillion.maps.compose_demo


import ai.nextbillion.maps.compose_demo.ui.GestureDetectorScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class GestureDetectorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestureDetectorScreen()
        }
    }
}