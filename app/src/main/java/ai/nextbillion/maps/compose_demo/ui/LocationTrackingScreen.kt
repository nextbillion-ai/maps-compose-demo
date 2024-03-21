package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.location.modes.CameraMode
import ai.nextbillion.maps.location.modes.RenderMode
import ai.nextbillion.maps.compose_demo.contract.LocationTrackingContract
import ai.nextbillion.maps.compose_demo.launcher.handlerGPSLauncher
import ai.nextbillion.maps.compose_demo.utils.requestMultiplePermission
import ai.nextbillion.maps.compose_demo.utils.showToast
import ai.nextbillion.maps.compose_demo.viewmodel.LocationViewModel
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.components.ShowOpenGPSDialog

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun LocationTrackingScreen() {
    val viewModel: LocationViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()
    val cameraPosition = rememberCameraPositionState {
        position =
            CameraPosition.Builder().tilt(0.0).bearing(0.0).zoom(15.0).target(LatLng(0.0, 0.0))
                .build()
    }

    var locationComponentSettings by remember { mutableStateOf(currentState.locationSettings) }

    var expandedLocationSettingMenu by remember { mutableStateOf(false) }
    var expandedRenderMode by remember { mutableStateOf(false) }
    var expandedTrackingMode by remember { mutableStateOf(false) }

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
            // 从app应用权限开关页面，打开权限，需要再检查一下GPS开关
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
            } else {
//                viewModel.startMapLocation()
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
            locationComponentSettings = locationComponentSettings,
            onMapLoaded = viewModel::onMapLoaded
        )
        FloatingActionButton(
            onClick = {
                locationComponentSettings =
                    locationComponentSettings.copy(cameraMode = CameraMode.TRACKING_GPS)
            },
            modifier = Modifier
                .padding(16.dp, 64.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_my_location),
                contentDescription = "Tracking Location"
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            IconButton(
                onClick = { expandedLocationSettingMenu = !expandedLocationSettingMenu },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expandedLocationSettingMenu,
                onDismissRequest = { expandedLocationSettingMenu = false },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                DropdownMenuItem(onClick = {
                    locationComponentSettings = locationComponentSettings.copy(enable = true)
                    expandedLocationSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_enable_component))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings = locationComponentSettings.copy(enable = false)
                    expandedLocationSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_disable_component))
                }
                DropdownMenuItem(onClick = {
                    var options = locationComponentSettings.options
                    options = options?.toBuilder()?.trackingGesturesManagement(true)?.build()
                    locationComponentSettings = locationComponentSettings.copy(options = options)
                    expandedLocationSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_enable_gesture_management))
                }
                DropdownMenuItem(onClick = {
                    var options = locationComponentSettings.options
                    options = options?.toBuilder()?.trackingGesturesManagement(false)?.build()
                    locationComponentSettings = locationComponentSettings.copy(options = options)
                    expandedLocationSettingMenu = false
                }) {
                    Text(stringResource(id = R.string.location_setting_disable_gesture_management))
                }
                DropdownMenuItem(onClick = {
                    expandedLocationSettingMenu = false
                    locationComponentSettings = locationComponentSettings.copy(maxAnimationFps = 5)
                }) {
                    Text(stringResource(id = R.string.location_setting_enable_animation_throttling))
                }
                DropdownMenuItem(onClick = {
                    expandedLocationSettingMenu = false
                    locationComponentSettings = locationComponentSettings.copy(maxAnimationFps = Int.MAX_VALUE)
                }) {
                    Text(stringResource(id = R.string.location_setting_disable_animation_throttling))
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
                "Mode:",
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = {
                    expandedRenderMode = true
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                val modeString = when(locationComponentSettings.renderMode) {
                    RenderMode.NORMAL -> "Normal"
                    RenderMode.COMPASS -> "Compass"
                    RenderMode.GPS -> "Gps"
                    else -> ""
                }
                Text(modeString, color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp)) // 用于添加间距
            Text(
                "Tracking:",
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = {
                    expandedTrackingMode = true
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                val modeString = when(locationComponentSettings.cameraMode) {
                    CameraMode.TRACKING -> "TRACKING"
                    CameraMode.NONE -> "NONE"
                    CameraMode.TRACKING_GPS -> "TRACKING_GPS"
                    CameraMode.TRACKING_COMPASS -> "TRACKING_COMPASS"
                    CameraMode.NONE_COMPASS -> "NONE_COMPASS"
                    CameraMode.NONE_GPS -> "NONE_GPS"
                    CameraMode.TRACKING_GPS_NORTH -> "TRACKING_GPS_NORTH"
                    else -> ""
                }
                Text(modeString, color = Color.White)
            }

            DropdownMenu(
                expanded = expandedRenderMode,
                onDismissRequest = { expandedRenderMode = false },
                modifier = Modifier.weight(1f),
            ) {
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(renderMode = RenderMode.NORMAL)
                    expandedRenderMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_normal))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(renderMode = RenderMode.COMPASS)
                    expandedRenderMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_compass))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(renderMode = RenderMode.GPS)
                    expandedRenderMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_GPS))
                }
            }

            DropdownMenu(
                expanded = expandedTrackingMode,
                onDismissRequest = { expandedTrackingMode = false },
                modifier = Modifier.weight(1f),
            ) {
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.NONE)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_none))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.NONE_COMPASS)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_none_compass))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.NONE_GPS)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_none_gps))
                }

                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.TRACKING)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_tracking))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.TRACKING_COMPASS)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_tracking_compass))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.TRACKING_GPS)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_tracking_gps))
                }
                DropdownMenuItem(onClick = {
                    locationComponentSettings =
                        locationComponentSettings.copy(cameraMode = CameraMode.TRACKING_GPS_NORTH)
                    expandedTrackingMode = false
                }) {
                    Text(stringResource(id = R.string.location_tracking_tracking_north))
                }
            }
        }
    }
}