package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState
import androidx.compose.ui.graphics.Color

class PolylineContract {

    sealed class Event : IUiEvent {
        object ShowOpenGPSDialog : Event()
        object HideOpenGPSDialog : Event()
    }

    data class State(
        val mapCenter : LatLng,
        val polyline1: List<LatLng>,
        val polyline2: List<LatLng>,
        val polylineWidth: Float,
        val polylineColor: Color,
        val polylineAlpha: Float,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}
