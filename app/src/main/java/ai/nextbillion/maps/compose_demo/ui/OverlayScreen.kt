package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.annotations.IconFactory
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.viewmodel.OverlayViewModel
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.overlay.MarkerInfoWindow
import ai.nextbillion.maps.extension.compose.overlay.Polygon
import ai.nextbillion.maps.extension.compose.overlay.Polyline
import ai.nextbillion.maps.extension.compose.overlay.rememberMarkerState
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun OverlayScreen() {
    val viewModel: OverlayViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            gesturesSettings = GesturesSettings {
                setZoomGesturesEnabled( true)
                setScrollGesturesEnabled(true)
            }
        ) {
            val image =  ImageBitmap.imageResource(LocalContext.current.resources, R.drawable.ic_defaultcluster)
            val icon = IconFactory.getInstance(LocalContext.current).fromBitmap(image.asAndroidBitmap())
            MarkerInfoWindow(
                icon = icon,
                state = rememberMarkerState(position = currentState.infoWindowLatLng),
                title = "I'm a marker",
                content = {
                    Card(modifier = Modifier.requiredSizeIn(maxWidth = 88.dp, minHeight = 66.dp)) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = it.title?:""
                        )
                    }
                }
            )

            MarkerInfoWindow(
                icon = icon,
                state = rememberMarkerState(position = currentState.circleCenter),
                snippet = "I'm marker snippet"
            ) {
                FlowRow(modifier = Modifier.width(120.dp).wrapContentHeight()) {
                    Text(it.snippet ?: "", color = Color.Red)
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_defaultcluster),
                        contentDescription = null
                    )
                }
            }

            Polygon(
                points = currentState.polygonTriangleList,
                strokeColor = Color.Red,
                fillColor = Color.Yellow
            )

            Polyline(
                points = currentState.polylineList,
                width = 10F,
            )
        }
    }
}