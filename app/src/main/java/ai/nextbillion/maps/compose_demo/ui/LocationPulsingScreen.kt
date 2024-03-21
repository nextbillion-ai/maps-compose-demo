package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.components.ShowOpenGPSDialog
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.contract.LocationTrackingContract
import ai.nextbillion.maps.compose_demo.launcher.handlerGPSLauncher
import ai.nextbillion.maps.compose_demo.utils.requestMultiplePermission
import ai.nextbillion.maps.compose_demo.utils.showToast
import ai.nextbillion.maps.compose_demo.viewmodel.LocationPulsingViewModel
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun LocationPulsingScreen() {

    val blue = "Blue"
    val green = "Green"
    val red = "Red"

    val viewModel: LocationPulsingViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()

    var expandedDurationSettings by remember { mutableStateOf(false) }
    var expandedColorSettings by remember { mutableStateOf(false) }
    var currentDuration by remember { mutableFloatStateOf(viewModel.secondLocationCirclePulseDurationMs) }
    var currentColor by remember { mutableIntStateOf(android.graphics.Color.BLUE) }
    var currentColorString by remember { mutableStateOf(green) }
    var expandedSettingMenu by remember { mutableStateOf(false) }

    val cameraPosition = rememberCameraPositionState {
        position =
            CameraPosition.Builder().tilt(0.0).bearing(0.0).zoom(15.0).target(LatLng(0.0, 0.0))
                .build()
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach {
            if (it is LocationTrackingContract.Effect.Toast) {
                showToast(it.msg)
            }
        }.collect()
    }

    val openGpsLauncher = handlerGPSLauncher(viewModel::checkGpsStatus)
    val reqGPSPermission = requestMultiplePermission(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onGrantAllPermission = viewModel::handleGrantLocationPermission,
        onNoGrantPermission = viewModel::handleNoGrantLocationPermission
    )

    LaunchedEffect(Unit) {
        snapshotFlow { reqGPSPermission.allPermissionsGranted }.collect {
            viewModel.checkGpsStatus()
        }
    }

    LaunchedEffect(currentState.locationLatLng) {
        if (null == currentState.locationLatLng) return@LaunchedEffect
        cameraPosition.move(CameraPosition.Builder().target(currentState.locationLatLng!!).build())
    }

    LaunchedEffect(currentState.isOpenGps, reqGPSPermission.allPermissionsGranted) {
        if (currentState.isOpenGps == true) {
            if (!reqGPSPermission.allPermissionsGranted) {
                reqGPSPermission.launchMultiplePermissionRequest()
            }
        }
    }

    if (currentState.isShowOpenGPSDialog) {
        ShowOpenGPSDialog(
            onDismiss = viewModel::hideOpenGPSDialog,
            onPositiveClick = {
                viewModel.openGPSPermission(openGpsLauncher)
            }
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            cameraPositionState = cameraPosition,
            locationComponentSettings = currentState.locationSettings,
            onMapLoaded = viewModel::onMapLoaded
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            IconButton(
                onClick = { expandedSettingMenu = !expandedSettingMenu },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expandedSettingMenu,
                onDismissRequest = { expandedSettingMenu = false },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.updateComponentStatus(true)
                    expandedSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_enable_component))
                }
                DropdownMenuItem(onClick = {
                    viewModel.updateComponentStatus(false)
                    expandedSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_disable_component))
                }
                DropdownMenuItem(onClick = {
                    viewModel.updatePulsingStatus(true)
                    expandedSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_enable_pulsing))
                }
                DropdownMenuItem(onClick = {
                    viewModel.updatePulsingStatus(false)
                    expandedSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_disable_pulsing))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Green)

        ) {
            Text(
                "Duration:",
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = {
                    expandedDurationSettings = true
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {

                Text("$currentDuration ms", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Color:",
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = {
                    expandedColorSettings = true
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text("$currentColorString", color = Color.White)
            }
        }
        DropdownMenu(
            expanded = expandedDurationSettings,
            onDismissRequest = { expandedDurationSettings = false },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(viewModel.defaultLocationCirclePulseDurationMs,currentColor)
                currentDuration = viewModel.defaultLocationCirclePulseDurationMs
                expandedDurationSettings = false
            }) {
                Text("${viewModel.defaultLocationCirclePulseDurationMs} ms")
            }
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(viewModel.secondLocationCirclePulseDurationMs,currentColor)
                currentDuration = viewModel.secondLocationCirclePulseDurationMs
                expandedDurationSettings = false
            }) {
                Text("${viewModel.secondLocationCirclePulseDurationMs} ms")
            }
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(viewModel.thirdLocationCirclePulseDurationMs,currentColor)
                currentDuration = viewModel.thirdLocationCirclePulseDurationMs
                expandedDurationSettings = false
            }) {
                Text("${viewModel.thirdLocationCirclePulseDurationMs} ms")
            }
        }

        DropdownMenu(
            expanded = expandedColorSettings,
            onDismissRequest = { expandedColorSettings = false },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(currentDuration,android.graphics.Color.BLUE)
                currentColor = android.graphics.Color.BLUE
                currentColorString = blue
                expandedColorSettings = false
            }) {
                Text(blue)
            }
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(currentDuration,android.graphics.Color.GREEN)
                currentColor = android.graphics.Color.GREEN
                currentColorString = green
                expandedColorSettings = false
            }) {
                Text(green)
            }
            DropdownMenuItem(onClick = {
                viewModel.updatePulsingStyle(currentDuration,android.graphics.Color.RED)
                currentColor = android.graphics.Color.RED
                currentColorString = red
                expandedColorSettings = false
            }) {
                Text(red)
            }
        }
    }
}