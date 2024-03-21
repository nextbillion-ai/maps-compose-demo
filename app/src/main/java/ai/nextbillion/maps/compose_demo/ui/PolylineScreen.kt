package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.utils.showToast
import ai.nextbillion.maps.compose_demo.viewmodel.PolylineViewModel
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.overlay.Polyline
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import ai.nextbillion.maps.extension.compose.viewport.CameraPositionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Random

@Composable
internal fun PolylineScreen() {
    val viewModel: PolylineViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()
    val cameraPositionState = CameraPositionState(position = CameraPosition.Builder().target(currentState.mapCenter).zoom(7.0).build())

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(500f.dp)) {
            NextBillionMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                gesturesSettings = GesturesSettings {
                    setZoomGesturesEnabled( true)
                    setScrollGesturesEnabled(true)
                }
            ) {
                Polyline(
                    alpha = currentState.polylineAlpha,
                    color = currentState.polylineColor,
                    points = currentState.polyline1,
                    width = currentState.polylineWidth,
                    onClick = {
                        it.color = viewModel.colors[Random().nextInt(viewModel.colors.size)].toArgb()
                        showToast("You clicked on polyline id ${it.id}")
                    }
                )
                Polyline(
                    alpha = currentState.polylineAlpha,
                    color = currentState.polylineColor,
                    points = currentState.polyline2,
                    width = currentState.polylineWidth,
                    onClick = {
                        it.width = viewModel.widths[Random().nextInt(viewModel.widths.size)]
                        showToast("You clicked on polyline id ${it.id}")
                    }
                )
            }
        }
        Row(modifier = Modifier.padding(top = 10.dp)) {
            FloatingActionButton(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                onClick = {
                    viewModel.updatePolylineWidth()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(modifier = Modifier.padding(5.dp), text = "Change width")
            }
            FloatingActionButton(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                onClick = {
                    viewModel.updatePolylineAlpha()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(modifier = Modifier.padding(5.dp), text = "Change alpha")
            }
            FloatingActionButton(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                onClick = {
                    viewModel.updatePolylineColor()
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(modifier = Modifier.padding(5.dp), text = "Change color")
            }
        }
    }
}