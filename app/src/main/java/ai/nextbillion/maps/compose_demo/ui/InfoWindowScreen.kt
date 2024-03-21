package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.annotations.IconFactory
import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.viewmodel.OverlayViewModel
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.overlay.MarkerInfoWindow
import ai.nextbillion.maps.extension.compose.overlay.rememberMarkerState
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun InfoWindowScreen() {
    val viewModel: OverlayViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(currentState.isShowWFJGroupOverlay) {
        cameraPositionState.move(CameraPosition.Builder().target(currentState.infoWindowLatLng).zoom(11.0).build())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                NextBillionMap(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState,
                    gesturesSettings = GesturesSettings {
                        setZoomGesturesEnabled(true)
                        setScrollGesturesEnabled(true)
                    }
                ) {
                    val image = ImageBitmap.imageResource(LocalContext.current.resources, R.drawable.ic_defaultcluster)
                    val icon = IconFactory.getInstance(LocalContext.current).fromBitmap(image.asAndroidBitmap())
                    MarkerInfoWindow(
                        icon = icon,
                        state = rememberMarkerState(position = currentState.infoWindowLatLng),
                        title = "10 Min",
                        content = {
                            Card(modifier = Modifier.requiredSizeIn(maxWidth = 88.dp, minHeight = 66.dp)) {
                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = it.title ?: ""
                                )
                            }
                        }, onInfoWindowClick = {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("On Info Window Click", duration = SnackbarDuration.Long)
                            }
                            true
                        }
                    )
                }
            }
        }
    )
}