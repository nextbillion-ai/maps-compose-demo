package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.repo.LocationTrackingRepository
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.core.NextbillionMapOptions
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.NextBillionMapEffect

import ai.nextbillion.maps.extension.compose.settings.LocationComponentSettings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import ai.nextbillion.maps.geometry.LatLng
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetScreen() {

    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showBottomSheet = true }
            ) {
                Text("Show Map")
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                modifier = Modifier.fillMaxHeight(0.7f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    NextBillionMap(
                        modifier = Modifier.fillMaxSize(),
                        // ... other settings ...
                        mapOptionsFactory = { context ->
                            NextbillionMapOptions.createFromAttributes(context)
                                .camera(
                                    CameraPosition.Builder()
                                        .target(LatLng(0.0, 0.0))
                                        .zoom(12.0)
                                        .build()
                                )
                                .tiltGesturesEnabled(false)
                                .minZoomPreference(10.0)
                                .maxZoomPreference(14.0)
                        },
                        locationComponentSettings = LocationTrackingRepository.initMapLocationComponentSettings(SDKUtils.getApplicationContext()),
                    ) {
                        // Marker here (irrelevant for explanation)
                        NextBillionMapEffect(Unit) {_ , nbMap ->
                            // Use NextbillionMap to access all the NextBillion Maps APIs .
                            // For example, to enable debug mode:
                            nbMap.isDebugActive = true

                        }
                    }
                }
            }
        }
    }
}