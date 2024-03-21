package ai.nextbillion.maps.compose_demo

import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import ai.nextbillion.maps.compose_demo.utils.StringUtils
import android.content.Intent

object MainRepository {
    fun handleMainFeatureItemClick(item: String) {
        when(item) {
            StringUtils.getString(R.string.nb_map_main_feature_item_basic) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),BasicFeatureActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_overlay) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),OverlayActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_blue_location) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),LocationTrackingActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_map_camera_move) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),MapViewportAnimationsActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_info_window) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),InfoWindowActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_custom_puck) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(), CustomLocationPuckActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_custom_pulsing) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),LocationPulsingActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_launch_effect) -> {
                startActivity(
                    Intent(
                        SDKUtils.getApplicationContext(),
                        LaunchedEffectActivity::class.java
                    )
                )
            }
            StringUtils.getString(R.string.nb_map_main_feature_init_camera) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),InitCameraPositionActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_polyline) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),PolylineActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_item_polygon) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),PolygonActivity::class.java))
            }

            StringUtils.getString(R.string.nb_map_main_feature_route_line) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(), ApiClientActivity::class.java))
            }
            StringUtils.getString(R.string.nb_map_main_feature_click_listener) -> {
                startActivity(Intent(SDKUtils.getApplicationContext(),ClickListenerActivity::class.java))
            }

//            StringUtils.getString(R.string.nb_map_main_feature_item_smooth_move) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),SmoothMoveActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_drag_drop_select_point) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),DragDropSelectPointActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_route_plan) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),RoutePlanActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_multipoint_click) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),MultiPointOverlayActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_marker_animation) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),MarkerAnimationActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_movement_track) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),MovementTrackActivity::class.java))
//            }
//            StringUtils.getString(R.string.nb_map_main_feature_item_cluster_effect) -> {
//                startActivity(Intent(SDKUtils.getApplicationContext(),ClusterEffectActivity::class.java))
//            }
        }
    }

    private fun startActivity(intent: Intent) {
        SDKUtils.getApplicationContext().startActivity(
            intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}