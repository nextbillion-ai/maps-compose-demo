package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import ai.nextbillion.maps.compose_demo.contract.PolylineContract
import androidx.compose.ui.graphics.Color
import java.util.Random

class PolylineViewModel : BaseViewModel<PolylineContract.Event, PolylineContract.State, PolylineContract.Effect>() {

    val colors = listOf<Color>(Color.Red, Color.Green, Color.Yellow)
    val widths = listOf<Float>(5f, 15f, 20f)
    val alphas = listOf<Float>(0.2f, 0.5f, 1f)

    override fun createInitialState(): PolylineContract.State {
        return PolylineContract.State(
            mapCenter = LatLng(4.454029847741182, 102.12279928897392),
            polyline1 = listOf(LatLng(4.173966011743196, 102.56143332932972), LatLng(4.757388856118707, 102.84923902653723)),
            polyline2 = listOf(LatLng(4.454029847741182, 102.12279928897392), LatLng(4.306435309587597, 101.05572884416955)),
            polylineWidth = 10f,
            polylineColor = Color.Blue,
            polylineAlpha = 1f
        )
    }

    fun updatePolylineWidth() {
        setState {
            copy(polylineWidth = widths[Random().nextInt(widths.size)])
        }
    }

    fun updatePolylineColor() {
        setState {
            copy(polylineColor = colors[Random().nextInt(colors.size)])
        }
    }

    fun updatePolylineAlpha() {
        setState {
            copy(polylineAlpha = alphas[Random().nextInt(alphas.size)])
        }
    }


    override fun handleEvents(event: PolylineContract.Event) {
    }

}