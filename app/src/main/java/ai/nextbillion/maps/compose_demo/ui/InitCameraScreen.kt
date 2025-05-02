package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.core.NextbillionMapOptions
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.model.MapType
import ai.nextbillion.maps.geometry.LatLng
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun InitCameraScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.fillMaxSize(),
            mapType = MapType.NORMAL,
            mapOptionsFactory = { context ->
                NextbillionMapOptions.createFromAttributes(context)
                    .camera(
                        CameraPosition.Builder()
                            .target(LatLng(1.0, 40.0))
                            .zoom(12.0)
                            .build()
                    )
                    .tiltGesturesEnabled(false)
                    .minZoomPreference(10.0)
                    .maxZoomPreference(14.0)
            },
        )
    }
}