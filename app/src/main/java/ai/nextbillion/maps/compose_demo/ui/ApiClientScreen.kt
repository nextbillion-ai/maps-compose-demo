package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.core.NextbillionMapOptions
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.NextBillionMapEffect
import ai.nextbillion.maps.compose_demo.client.NBRouteLine
import ai.nextbillion.maps.compose_demo.viewmodel.ApiClientViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun ApiClientScreen() {
    val viewModel: ApiClientViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.fillMaxSize(),
            mapOptionsFactory = { context ->
                NextbillionMapOptions.createFromAttributes(context)
                    .camera(
                        CameraPosition.Builder()
                            .target(currentState.locationLatLng)
                            .zoom(12.0)
                            .build()
                    )
            }
        ){
            val  context = LocalContext.current
            NextBillionMapEffect(currentState.nbRoute != null) { _, nbMap ->
                if (currentState.nbRoute != null) {
                    val routeLine = NBRouteLine(nbMap, context,currentState.routeLineConfig)
                    routeLine.drawRoute(currentState.nbRoute!!)
                }
            }
        }
    }

    LaunchedEffect(currentState.nbRoute == null) {
        if(currentState.nbRoute == null) {
            viewModel.loadRoute()
        }
    }

}