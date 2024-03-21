package ai.nextbillion.maps.compose_demo.ui

import ai.nextbillion.maps.compose_demo.ImmutableListWrapper
import ai.nextbillion.maps.compose_demo.components.BasicFeatureMenuBar
import ai.nextbillion.maps.extension.compose.NextBillionMap
import ai.nextbillion.maps.extension.compose.model.MapType
import ai.nextbillion.maps.extension.compose.settings.AttributionSettings
import ai.nextbillion.maps.extension.compose.settings.CompassSettings
import ai.nextbillion.maps.extension.compose.settings.GesturesSettings
import ai.nextbillion.maps.extension.compose.settings.LogoSettings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

@Composable
internal fun BasicFeatureScreen() {
    val stringOfLightMap = "Light Map"
    val stringOfSatelliteMap = "Satellite Map"
    val stringOfDarkMap = "Dark Map"
    val stringOfRotateGestureSwitch = "Rotate gesture switch"
    val stringOfTiltGestureSwitch = "Tilt gesture switch"
    val stringOfZoomGestureSwitch = "Zoom gesture switch"
    val stringOfScrollGestureSwitch = "Scroll gesture switch"
    val stringOfHorizontalScrollGestureSwitch = "Horizontal Scroll gesture switch"
    val stringOfDoubleTapGestureSwitch = "Double Tap gesture switch"
    val stringOfQuickZoomGestureSwitch = "Quick Zoom gesture switch"

    val stringOfCompassEnable = "Compass Switch"
    val stringOfAttributionEnable = "Attribution Switch"
    val stringOfLogoEnable = "Logo Switch"


    var mapType by remember { mutableStateOf(MapType.NORMAL) }
    var gesturesSettings by remember { mutableStateOf(GesturesSettings {  }) }
    var compassSettings by remember { mutableStateOf(CompassSettings {  }) }
    var attributionSettings by remember { mutableStateOf(AttributionSettings {  }) }
    var logoSettings by remember { mutableStateOf(LogoSettings {  }) }

//    var mapProperties by remember { mutableStateOf(MapProperties()) }

    val menuList by remember {
        mutableStateOf(
            ImmutableListWrapper(
            listOf(
                stringOfLightMap,
                stringOfSatelliteMap,
                stringOfDarkMap,
                stringOfRotateGestureSwitch,
                stringOfTiltGestureSwitch,
                stringOfZoomGestureSwitch,
                stringOfScrollGestureSwitch,
                stringOfHorizontalScrollGestureSwitch,
                stringOfDoubleTapGestureSwitch,
                stringOfQuickZoomGestureSwitch,
                stringOfCompassEnable,
                stringOfAttributionEnable,
                stringOfLogoEnable
            )
        )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NextBillionMap(
            modifier = Modifier.matchParentSize(),
            mapType = mapType,
            gesturesSettings = gesturesSettings,
            compassSettings = compassSettings,
            logoSettings = logoSettings,
            attributionSettings = attributionSettings
        )

        Column(modifier = Modifier.align(Alignment.TopCenter).statusBarsPadding()) {
            BasicFeatureMenuBar(
                modifier = Modifier.fillMaxWidth().background(color = Color.Black.copy(alpha = 0.3F)),
                listWrapper = menuList,
                onStatusLabel = {
                    Text(
                        text = when (it) {
                            stringOfLightMap -> mapType == MapType.NORMAL
                            stringOfSatelliteMap -> mapType ==  MapType.SATELLITE
                            stringOfDarkMap -> mapType ==  MapType.NIGHT
                            stringOfRotateGestureSwitch -> gesturesSettings.rotateGesturesEnabled
                            stringOfTiltGestureSwitch -> gesturesSettings.tiltGesturesEnabled
                            stringOfZoomGestureSwitch -> gesturesSettings.zoomGesturesEnabled
                            stringOfScrollGestureSwitch -> gesturesSettings.scrollGesturesEnabled
                            stringOfHorizontalScrollGestureSwitch -> gesturesSettings.horizontalScrollGesturesEnabled
                            stringOfDoubleTapGestureSwitch -> gesturesSettings.doubleTapGesturesEnabled
                            stringOfQuickZoomGestureSwitch -> gesturesSettings.quickZoomGesturesEnabled
                            stringOfCompassEnable -> compassSettings.enabled
                            stringOfAttributionEnable -> attributionSettings.enabled
                            stringOfLogoEnable -> logoSettings.enabled
                            else -> ""
                        }.toString(),
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.White,
                            shadow = Shadow(
                                color = Color.Red,
                                offset = Offset.Zero,
                                blurRadius = 20.0f
                            )
                        )
                    )
                },
                onItemClick = {
                    when (it) {
                        stringOfLightMap-> {
                            mapType = MapType.NORMAL
                        }
                        stringOfSatelliteMap-> {
                            mapType = MapType.SATELLITE
                        }

                        stringOfDarkMap-> {
                            mapType = MapType.NIGHT
                        }
                        stringOfRotateGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(rotateGesturesEnabled = !gesturesSettings.rotateGesturesEnabled)
                        }
                        stringOfTiltGestureSwitch -> {
                            gesturesSettings = gesturesSettings.copy(tiltGesturesEnabled = !gesturesSettings.tiltGesturesEnabled)
                        }
                        stringOfZoomGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(zoomGesturesEnabled = !gesturesSettings.zoomGesturesEnabled)
                        }
                        stringOfScrollGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(scrollGesturesEnabled = !gesturesSettings.scrollGesturesEnabled)
                        }
                        stringOfHorizontalScrollGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(horizontalScrollGesturesEnabled = !gesturesSettings.horizontalScrollGesturesEnabled)
                        }
                        stringOfDoubleTapGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(doubleTapGesturesEnabled = !gesturesSettings.doubleTapGesturesEnabled)
                        }
                        stringOfQuickZoomGestureSwitch-> {
                            gesturesSettings = gesturesSettings.copy(quickZoomGesturesEnabled = !gesturesSettings.quickZoomGesturesEnabled)
                        }
                        stringOfCompassEnable-> {
                            compassSettings = compassSettings.copy(enabled = !compassSettings.enabled)
                        }
                        stringOfAttributionEnable-> {
                            attributionSettings = attributionSettings.copy(enabled = !attributionSettings.enabled)
                        }
                        stringOfLogoEnable -> {
                            logoSettings = logoSettings.copy(enabled = !logoSettings.enabled)
                        }
                    }
                }
            )
        }
    }
}