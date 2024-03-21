package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.utils.showToast
import ai.nextbillion.maps.compose_demo.viewmodel.PolygonViewModel
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.overlay.Polygon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Random

@Composable
internal fun PolygonScreen() {
    val viewModel: PolygonViewModel = viewModel()
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
                Polygon(
                    points = currentState.polygonList,
                    fillColor = currentState.polygonFillColor,
                    alpha = currentState.polygonAlpha,
                    strokeColor = Color.Blue,
                    onClick = {
                        it.fillColor = viewModel.colors[Random().nextInt(viewModel.colors.size)].toArgb()
                        showToast("You clicked on polygon id ${it.id}")
                    }
                )

            }
        }
        Row(modifier = Modifier.padding(top = 10.dp)) {
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