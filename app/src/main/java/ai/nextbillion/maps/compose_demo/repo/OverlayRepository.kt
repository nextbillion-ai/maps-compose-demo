package ai.nextbillion.maps.compose_demo.repo

import ai.nextbillion.maps.annotations.PolygonOptions
import ai.nextbillion.maps.geometry.LatLng
object OverlayRepository {
    fun initPolygonHolePointList(): List<LatLng>{
        return listOf(
            LatLng(39.984864, 116.305756),
            LatLng(39.983618, 116.305848),
            LatLng(39.982347, 116.305966),
            LatLng(39.982412, 116.308111),
            LatLng(39.984122, 116.308224),
            LatLng(39.984955, 116.308099),
            LatLng(39.984864, 116.305756)
        )
    }
    fun initPolygonHoleOptionList(): List<PolygonOptions> {
        val options:MutableList<PolygonOptions> = mutableListOf()
        val holeOptions = PolygonOptions()
        holeOptions.addAll(initPolygonHolePointList())
        options.add(holeOptions)
        return  options
    }

//    fun initCircleHoleOptions():CircleHoleOptions {
//        return CircleHoleOptions().center(LatLng(39.97923, 116.357428)).radius(1800)
//    }
}