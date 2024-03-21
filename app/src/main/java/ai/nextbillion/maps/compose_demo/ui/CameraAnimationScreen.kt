package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import ai.nextbillion.maps.geometry.LatLng
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CameraAnimationScreen() {

    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.Builder().target(LatLng(21.142364, 79.094730)).zoom(15.0)
            .build()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
        )
        Button(
            onClick = {
                cameraPositionState.easeCamera(CameraPosition.Builder().target(LatLng(21.152364, 79.098730)).build(), durationMs = 5000)
            }
        ) {
            Text(text = "Animate camera with easeCamera")
        }
    }
}