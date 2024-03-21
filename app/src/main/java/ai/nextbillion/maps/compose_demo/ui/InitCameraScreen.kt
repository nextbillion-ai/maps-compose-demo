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
            mapType = MapType.SATELLITE,
            mapOptionsFactory = { context ->
                NextbillionMapOptions.createFromAttributes(context)
                    .camera(
                        CameraPosition.Builder()
                            .target(LatLng(21.142364, 79.094730))
                            .zoom(12.0)
                            .build()
                    )
            }
        )
    }
}