package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import ai.nextbillion.maps.geometry.LatLng
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
internal fun ClickListenerScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder().tilt(0.0).bearing(0.0).zoom(15.0).target(LatLng(21.142364, 79.094730)).build()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClickListener = { latLng ->
                Toast.makeText(SDKUtils.getApplicationContext(), "Click$latLng", Toast.LENGTH_LONG).show()
                true
            },
            onMapLongClickListener = { latLng ->
                Toast.makeText(SDKUtils.getApplicationContext(), "Long Click:$latLng", Toast.LENGTH_LONG).show()
                true
            },
            gesturesSettings = GesturesSettings {

                setZoomGesturesEnabled(true)
                setScrollGesturesEnabled(true)
            }
        )
    }
}