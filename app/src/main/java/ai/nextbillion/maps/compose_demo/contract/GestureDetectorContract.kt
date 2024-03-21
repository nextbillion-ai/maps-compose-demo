package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState

class GestureDetectorContract {

    sealed class Event : IUiEvent {
        object ShowOpenGPSDialog : Event()
        object HideOpenGPSDialog : Event()
    }

    data class State(
        // The current location
        val locationLatLng: LatLng?,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}
