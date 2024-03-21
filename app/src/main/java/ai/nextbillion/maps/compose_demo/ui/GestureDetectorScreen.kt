package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import ai.nextbillion.maps.geometry.LatLng
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState

@Composable
internal fun GestureDetectorScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder().tilt(0.0).bearing(0.0).zoom(15.0).target(LatLng(0.0, 0.0)).build()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            gesturesSettings = GesturesSettings {
                setZoomGesturesEnabled(true)
                setScrollGesturesEnabled(true)
            }
        )
    }
}