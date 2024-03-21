package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState
import androidx.compose.ui.graphics.Color

class PolygonContract {

    sealed class Event : IUiEvent {
    }

    data class State(
        val mapCenter : LatLng,
        val polygonList: List<LatLng>,
        val polygonFillColor: Color,
        val polygonAlpha: Float,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}
