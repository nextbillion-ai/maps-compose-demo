package ai.nextbillion.maps.compose_demo.utils

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.*

/**
 * Request multiple permissions
 */
@ExperimentalPermissionsApi
@Composable
fun requestMultiplePermission(
    permissions: List<String>,
    onNoGrantPermission: () -> Unit = {},
    onGrantAllPermission: () -> Unit = {}
): MultiplePermissionsState {
    return rememberMultiplePermissionsState(
        permissions = permissions,
        onPermissionsResult = { mapInfo ->
            val noGrantPermissionMap = mapInfo.filter { !it.value }
            if (noGrantPermissionMap.isNotEmpty()) {
                onNoGrantPermission.invoke()
            } else {
                onGrantAllPermission.invoke()
            }
        }
    )
}