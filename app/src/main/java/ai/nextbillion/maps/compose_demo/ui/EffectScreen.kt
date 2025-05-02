package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.compose_demo.repo.LocationTrackingRepository
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.NextBillionMapEffect
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings

import ai.nextbillion.maps.compose_demo.utils.CityLocations
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.core.NextbillionMapOptions
import ai.nextbillion.maps.extension.compose.settings.LocationComponentSettings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState
import ai.nextbillion.maps.geometry.LatLng

@Composable
internal fun EffectScreen() {

    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.Builder().target(CityLocations.BEIJING).zoom(15.0)
            .build()
    }

    val debugModeEnabled = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            gesturesSettings = GesturesSettings {
                setZoomGesturesEnabled(true)
                setScrollGesturesEnabled(true)
            },
            locationComponentSettings = LocationTrackingRepository.initMapLocationComponentSettings(
                SDKUtils.getApplicationContext()),
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
        ) {

            NextBillionMapEffect(Unit) {_ , nbMap ->
                // Use NextbillionMap to access all the NextBillion Maps APIs .
                // For example, to enable debug mode:
                nbMap.isDebugActive = debugModeEnabled.value
                nbMap.setMinZoomPreference(10.0)
                nbMap.setMaxZoomPreference(14.0)
            }
        }
    }
}