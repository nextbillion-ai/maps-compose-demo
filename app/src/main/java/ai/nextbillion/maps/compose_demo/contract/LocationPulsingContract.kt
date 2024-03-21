package ai.nextbillion.navigation.compose_demo.contract

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.extension.compose.settings.LocationComponentSettings
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState

class LocationPulsingContract {

    sealed class Event : IUiEvent {
        object ShowOpenGPSDialog : Event()
        object HideOpenGPSDialog : Event()
    }

    data class State(
        // Whether the system GPS permission is enabled
        val isOpenGps: Boolean?,
        // Whether to display a confirmation box for opening GPS
        val isShowOpenGPSDialog: Boolean,
        // Whether the App has enabled the location permission
        val grantLocationPermission:Boolean,
        // location
        val locationLatLng: LatLng?,
        val locationSettings: LocationComponentSettings,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}