package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.compose_demo.repo.LocationTrackingRepository
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.compose_demo.utils.openAppPermissionSettingPage
import ai.nextbillion.maps.compose_demo.utils.safeLaunch
import ai.nextbillion.maps.core.Style
import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import ai.nextbillion.maps.compose_demo.contract.LocationTrackingContract

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import kotlinx.coroutines.Dispatchers

open class LocationViewModel :
    BaseViewModel<LocationTrackingContract.Event, LocationTrackingContract.State, LocationTrackingContract.Effect>() {


    override fun createInitialState(): LocationTrackingContract.State {
        return LocationTrackingContract.State(
            isShowOpenGPSDialog = false,
            grantLocationPermission = false,
            locationLatLng = null,
            isOpenGps = null,
            locationSettings = LocationTrackingRepository.initMapLocationComponentSettings(SDKUtils.getApplicationContext()),
        )
    }

    override fun handleEvents(event: LocationTrackingContract.Event) {
        when (event) {
            is LocationTrackingContract.Event.ShowOpenGPSDialog -> {
                setState { copy(isShowOpenGPSDialog = true) }
            }

            is LocationTrackingContract.Event.HideOpenGPSDialog -> {
                setState { copy(isShowOpenGPSDialog = false) }
            }
        }
    }

    private fun checkGPSIsOpen(): Boolean {
        val locationManager: LocationManager? = SDKUtils.getApplicationContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    /**
     * 检查系统GPS开关是否打开
     */
    fun checkGpsStatus() = asyncLaunch(Dispatchers.IO) {
        val isOpenGps = checkGPSIsOpen()
        setState { copy(isOpenGps = isOpenGps) }
        if (!isOpenGps) {
            setEvent(LocationTrackingContract.Event.ShowOpenGPSDialog)
        } else {
            hideOpenGPSDialog()
        }
    }

    fun onMapLoaded(style: Style) {
        style.isFullyLoaded
        checkGpsStatus()
    }

    fun hideOpenGPSDialog() {
        setEvent(LocationTrackingContract.Event.HideOpenGPSDialog)
    }

    /**
     * The phone has GPS on, and the app doesn't grant permission
     */
    fun handleNoGrantLocationPermission() {
        setState { copy(grantLocationPermission = false) }
        setEvent(LocationTrackingContract.Event.ShowOpenGPSDialog)
    }

    fun handleGrantLocationPermission() {
        setState { copy(grantLocationPermission = true) }
        checkGpsStatus()
    }

    fun openGPSPermission(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        if (checkGPSIsOpen()) {
            // The system GPS has been opened, the APP has not authorized, jump the permission page
            openAppPermissionSettingPage()
        } else {
            // The system GPS switch page is displayed
            launcher.safeLaunch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    fun toggleLocationComponentStyle() {
        val options = currentState.locationSettings.options?.toBuilder()
            ?.foregroundDrawable(R.drawable.ic_custom_user_icon)
            ?.bearingDrawable(R.drawable.ic_custom_user_arrow)
            ?.gpsDrawable(R.drawable.ic_custom_user_puck_icon)
            ?.accuracyAlpha(0.15f)
            ?.accuracyColor(R.color.accuracyColor)
            ?.elevation(0f)
            ?.compassAnimationEnabled(false)
            ?.accuracyAnimationEnabled(true)
            ?.pulseEnabled(true)
            ?.build()
        val locationSettings = currentState.locationSettings.copy(options = options)
        setState {
            copy(locationSettings = locationSettings)
        }
    }

}