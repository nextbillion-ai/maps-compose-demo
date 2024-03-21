package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.NextBillionMapEffect
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings

import ai.nextbillion.maps.compose_demo.utils.CityLocations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import ai.nextbillion.maps.extension.compose.viewport.rememberCameraPositionState

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
            }
        ) {

            NextBillionMapEffect(Unit) {_ , nbMap ->
                // Use NextbillionMap to access all the NextBillion Maps APIs .
                // For example, to enable debug mode:
                nbMap.isDebugActive = debugModeEnabled.value
            }
        }
    }
}