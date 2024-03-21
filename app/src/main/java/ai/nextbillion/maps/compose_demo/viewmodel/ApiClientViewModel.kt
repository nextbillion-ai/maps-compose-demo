package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.api.directions.NBDirectionsResponse
import ai.nextbillion.api.models.NBLocation
import ai.nextbillion.maps.compose_demo.client.NBMapAPIClient
import ai.nextbillion.maps.compose_demo.client.NBRouteLineConfig
import ai.nextbillion.maps.compose_demo.contract.ApiClientContract
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiClientViewModel :
    BaseViewModel<ApiClientContract.Event, ApiClientContract.State, ApiClientContract.Effect>() {
    override fun createInitialState(): ApiClientContract.State {
        return ApiClientContract.State(
            locationLatLng = LatLng(1.2943697224834985, 103.8317451802061),
            client = NBMapAPIClient(),
            queryStartPoint = NBLocation(1.2943697224834985, 103.8317451802061),
            queryEndPoint = NBLocation(1.3303739400394545, 103.80463752235354),
            isLoading = false,
            routeLineConfig = NBRouteLineConfig.Builder().setRouteName("Test_route_line_displaying").build(),
            nbRoute = null
        )
    }

    private val callback = object : Callback<NBDirectionsResponse> {
        override fun onResponse(call: Call<NBDirectionsResponse>, response: Response<NBDirectionsResponse>) {
            val directionsResponse = response.body()
            if (directionsResponse?.routes() != null && directionsResponse.routes().isNotEmpty()
            ) {
                val route = directionsResponse.routes()[0]
                setState { copy(isLoading = false, nbRoute = route) }
            } else {
                setState { copy(isLoading = false, nbRoute = null) }
                setEffect { ApiClientContract.Effect.Toast("Failed to fetch directions") }
            }
        }

        override fun onFailure(call: Call<NBDirectionsResponse>, t: Throwable) {
            setState { copy(isLoading = false,nbRoute = null) }
            setEffect { ApiClientContract.Effect.Toast( t.message) }
        }
    }

    override fun handleEvents(event: ApiClientContract.Event) {
        when(event) {

            is ApiClientContract.Event.QueryRoutePlan -> {
                setState { copy(isLoading = true, nbRoute = null) }
                asyncLaunch(Dispatchers.IO) {
                    kotlin.runCatching {
                        currentState.client.enQueueGetDirections(currentState.queryStartPoint,currentState.queryEndPoint,callback)
                    }
                }
            }
        }
    }

    fun loadRoute() {
        setEvent(ApiClientContract.Event.QueryRoutePlan)
    }
}