package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.extension.compose.settings.LocationComponentSettings
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState

class LocationTrackingContract {

    sealed class Event : IUiEvent {
        object ShowOpenGPSDialog : Event()
        object HideOpenGPSDialog : Event()
    }

    data class State(
        val isOpenGps: Boolean?,
        val isShowOpenGPSDialog: Boolean,
        val grantLocationPermission:Boolean,
        val locationLatLng: LatLng?,
        val locationSettings: LocationComponentSettings,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}