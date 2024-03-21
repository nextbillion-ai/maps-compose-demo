package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.api.models.NBLocation
import ai.nextbillion.api.models.directions.NBRoute
import ai.nextbillion.maps.compose_demo.client.NBMapAPIClient
import ai.nextbillion.maps.compose_demo.client.NBRouteLineConfig
import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState

class ApiClientContract {

    sealed class Event : IUiEvent {
        object QueryRoutePlan : Event()
    }

    data class State(
        val locationLatLng: LatLng?,
        val client: NBMapAPIClient,
        val queryStartPoint: NBLocation,
        val queryEndPoint: NBLocation,
        val isLoading:Boolean,
        val routeLineConfig: NBRouteLineConfig,
        val nbRoute: NBRoute?,
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}
