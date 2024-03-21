
package ai.nextbillion.maps.compose_demo.contract

import ai.nextbillion.maps.annotations.PolygonOptions
import ai.nextbillion.maps.compose_demo.state.IUiEffect
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.geometry.LatLngBounds
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState

class OverlayContract {
    sealed class Event : IUiEvent {
        object ShowWFJGroupOverlayEvent : Event()
        object HideWFJGroupOverlayEvent : Event()
        object ShowTileOverlayEvent : Event()
        object HideTileOverlayEvent : Event()
    }

    data class State(
        val isShowWFJGroupOverlay: Boolean,
        val isShowTileOverlay: Boolean,
        val circleCenter: LatLng,
        val mapCenter: LatLng,
        val wfjCenter: LatLng,
        val wfjLatLngBounds: LatLngBounds,
        val arcStartPoint: LatLng,
        val arcPassPoint: LatLng,
        val arcEndPoint: LatLng,
        val infoWindowLatLng: LatLng,
        val polygonCornerLatLng: LatLng,
        val polylineList: List<LatLng>,
        val polygonTriangleList: List<LatLng>,
        val polygonHolePointList: List<LatLng>,
        val polygonHoleOptionList: List<PolygonOptions>,
    ) : IUiState

    sealed class Effect : IUiEffect
}