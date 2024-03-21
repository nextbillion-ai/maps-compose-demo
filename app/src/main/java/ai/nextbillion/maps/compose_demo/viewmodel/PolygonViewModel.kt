package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import ai.nextbillion.maps.compose_demo.contract.PolygonContract
import androidx.compose.ui.graphics.Color
import java.util.Random

class PolygonViewModel : BaseViewModel<PolygonContract.Event, PolygonContract.State, PolygonContract.Effect>() {
    val colors = listOf<Color>(Color.Red, Color.Green, Color.Yellow);
    val alphas = listOf<Float>(0.2f, 0.5f, 1f)
    override fun createInitialState(): PolygonContract.State {
        return PolygonContract.State(
            mapCenter = LatLng(5.454029847741182, 102.12279928897392),
            polygonList = listOf<LatLng>(LatLng(5.905950548125056, 102.20094147543449),
                LatLng(5.447582810324074, 102.4236053421908),
                LatLng(5.319304234490461, 103.13322846086854),
                LatLng(5.009276677957602, 102.43232095620415),
                LatLng(4.5261730655607915, 102.64303506758347),
                LatLng(4.957180657851621, 102.12291665873018),
                LatLng(4.764991311408667, 101.51405086422352),
                LatLng(5.113456220724453, 101.63920008098916),
                LatLng(5.5704634364237124, 101.24733645772515),
                LatLng(5.5069783817472056, 101.93764874951532),
                LatLng(5.905950548125056, 102.20094147543449)),
            polygonFillColor = Color.Blue,
            polygonAlpha = 1f
        )
    }

    override fun handleEvents(event: PolygonContract.Event) {
        TODO("Not yet implemented")
    }


    fun updatePolylineColor() {
        setState {
            copy(polygonFillColor = colors[Random().nextInt(colors.size)])
        }
    }

    fun updatePolylineAlpha() {
        setState {
            copy(polygonAlpha = alphas[Random().nextInt(alphas.size)])
        }
    }


}