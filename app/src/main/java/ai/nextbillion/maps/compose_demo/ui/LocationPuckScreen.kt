package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.components.ShowOpenGPSDialog
import ai.nextbillion.maps.compose_demo.launcher.handlerGPSLauncher
import ai.nextbillion.maps.compose_demo.utils.requestMultiplePermission
import ai.nextbillion.maps.compose_demo.utils.showToast
import ai.nextbillion.maps.compose_demo.viewmodel.LocationViewModel
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.compose_demo.contract.LocationTrackingContract
import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun LocationPuckScreen() {

    val viewModel: LocationViewModel = viewModel()
    val currentState by viewModel.uiState.collectAsState()
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

        FloatingActionButton(
            onClick = {
                viewModel.toggleLocationComponentStyle()
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
    }
}