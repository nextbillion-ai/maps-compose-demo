package ai.nextbillion.maps.compose_demo.viewmodel

import ai.nextbillion.maps.compose_demo.repo.OverlayRepository
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.geometry.LatLngBounds
import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import ai.nextbillion.maps.compose_demo.contract.OverlayContract

class OverlayViewModel : BaseViewModel<OverlayContract.Event, OverlayContract.State, OverlayContract.Effect>() {

    override fun createInitialState(): OverlayContract.State {
        return OverlayContract.State(
            isShowWFJGroupOverlay = false,
            isShowTileOverlay = false,
            circleCenter = LatLng(39.903787, 116.426095),
            mapCenter = LatLng(39.91, 116.40),
            wfjCenter = LatLng(39.936713,116.386475),
            wfjLatLngBounds = LatLngBounds.from(39.939577, 116.388331,39.935029, 116.384377,),
            arcStartPoint = LatLng(39.80, 116.09),
            arcPassPoint = LatLng(39.77, 116.28),
            arcEndPoint = LatLng(39.78, 116.46),
            infoWindowLatLng = LatLng(39.93, 116.13),
            polylineList = listOf(LatLng(39.92, 116.34),LatLng(39.93, 116.34),LatLng(39.92, 116.35)),
            polygonTriangleList = listOf(LatLng(39.88, 116.41), LatLng(39.87, 116.49), LatLng(39.82, 116.38)),
            polygonCornerLatLng = LatLng(39.982347, 116.305966),
            polygonHolePointList = OverlayRepository.initPolygonHolePointList(),
            polygonHoleOptionList = OverlayRepository.initPolygonHoleOptionList()
        )
    }

    override fun handleEvents(event: OverlayContract.Event) {
        when(event) {
            is OverlayContract.Event.ShowWFJGroupOverlayEvent -> {
                setState { copy(isShowWFJGroupOverlay = true) }
            }
            is OverlayContract.Event.HideWFJGroupOverlayEvent -> {
                setState { copy(isShowWFJGroupOverlay = false) }
            }
            is OverlayContract.Event.ShowTileOverlayEvent -> {
                setState { copy(isShowTileOverlay = true) }
            }
            is OverlayContract.Event.HideTileOverlayEvent -> {
                setState { copy(isShowTileOverlay = false) }
            }
        }
    }

    fun toggleWFJGroupOverlay() {
        if(currentState.isShowWFJGroupOverlay){
            setEvent(OverlayContract.Event.HideWFJGroupOverlayEvent)
        } else {
            setEvent(OverlayContract.Event.ShowWFJGroupOverlayEvent)
        }
    }

    fun toggleTileOverlay() {
        if(currentState.isShowTileOverlay){
            setEvent(OverlayContract.Event.HideTileOverlayEvent)
        } else {
            setEvent(OverlayContract.Event.ShowTileOverlayEvent)
        }
    }
}