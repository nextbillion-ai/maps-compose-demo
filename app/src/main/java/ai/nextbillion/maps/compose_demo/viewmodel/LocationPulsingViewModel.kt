package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.maps.compose_demo.repo.LocationTrackingRepository
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.compose_demo.contract.LocationTrackingContract

import android.view.animation.DecelerateInterpolator


class LocationPulsingViewModel : LocationViewModel() {

    val defaultLocationCirclePulseDurationMs = 2300f
    val secondLocationCirclePulseDurationMs = 800f
    val thirdLocationCirclePulseDurationMs = 8000f
    override fun createInitialState(): LocationTrackingContract.State {
        var locationSettings = LocationTrackingRepository.initMapLocationComponentSettings(SDKUtils.getApplicationContext())
        val options = locationSettings.options
            ?.toBuilder()
            ?.pulseFadeEnabled(true)
            ?.pulseInterpolator(DecelerateInterpolator())
            ?.pulseColor(android.graphics.Color.BLUE)
            ?.pulseAlpha(0.55f)
            ?.pulseSingleDuration(secondLocationCirclePulseDurationMs)
            ?.pulseMaxRadius(35.0f)
            ?.build()
        locationSettings = locationSettings.copy(options = options)
        return LocationTrackingContract.State(
            isShowOpenGPSDialog = false,
            grantLocationPermission = false,
            locationLatLng = null,
            isOpenGps = null,
            locationSettings = locationSettings,
        )
    }

    fun updatePulsingStyle(duration:Float,color:Int) {
        val options = currentState.locationSettings.options?.toBuilder()
            ?.pulseColor(color)
            ?.pulseSingleDuration(duration)
            ?.pulseEnabled(true)
            ?.build()
        val locationSettings = currentState.locationSettings.copy(options = options)
        setState {
            copy(locationSettings = locationSettings)
        }
    }

    fun updateComponentStatus(enable: Boolean) {
        val locationSettings =  currentState.locationSettings.copy(enable = enable)
        setState {
            copy(locationSettings = locationSettings)
        }
    }

    fun updatePulsingStatus(enable: Boolean) {
        var options =  currentState.locationSettings.options
        options = options?.toBuilder()?.pulseFadeEnabled(enable)?.pulseEnabled(enable)?.build()
        val locationSettings =  currentState.locationSettings.copy(options = options )
        setState {
            copy(locationSettings = locationSettings)
        }
    }

}