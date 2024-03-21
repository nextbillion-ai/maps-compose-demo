package ai.nextbillion.maps.compose_demo.client

import ai.nextbillion.api.models.NBLocation
import ai.nextbillion.api.models.directions.NBRoute
import ai.nextbillion.api.snaptoroad.NBSnapToRoadResponse
import ai.nextbillion.kits.geojson.Feature
import ai.nextbillion.kits.geojson.FeatureCollection
import ai.nextbillion.kits.geojson.LineString
import ai.nextbillion.kits.geojson.Point
import ai.nextbillion.kits.turf.TurfMeasurement
import ai.nextbillion.maps.camera.CameraPosition
import ai.nextbillion.maps.camera.CameraUpdateFactory
import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.core.NextbillionMap
import ai.nextbillion.maps.geometry.LatLng
import ai.nextbillion.maps.style.layers.Layer
import ai.nextbillion.maps.style.layers.LineLayer
import ai.nextbillion.maps.style.layers.Property
import ai.nextbillion.maps.style.layers.PropertyFactory
import ai.nextbillion.maps.style.layers.SymbolLayer
import ai.nextbillion.maps.style.sources.GeoJsonOptions
import ai.nextbillion.maps.style.sources.GeoJsonSource
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import kotlin.math.roundToInt


class NBRouteLine(map: NextbillionMap, context: Context, config: NBRouteLineConfig) {
    private lateinit var routeSource: GeoJsonSource
    private lateinit var originSource: GeoJsonSource
    private lateinit var destinationSource: GeoJsonSource
    private lateinit var mMap: NextbillionMap
    private lateinit var routeLayer: Layer
    private lateinit var originLayer: Layer
    private lateinit var destinationLayer: Layer
    private var routeLayerId: String? = null
    private var originLayerId: String? = null
    private var destinationLayerId: String? = null
    private var routeSourceId: String? = null
    private var originSourceId: String? = null
    private var destinationSourceId: String? = null
    private var originIconId: String? = null
    private var destIconId: String? = null
    private var mLineWidth = 3.0f
    private var mLineColor = "#f54242"
    private var mLineCap = Property.LINE_CAP_ROUND
    private var mLineJoin = Property.LINE_JOIN_ROUND

    @DrawableRes
    private var originalIcon: Int = R.mipmap.blue_marker

    @DrawableRes
    private var destinationIcon: Int = R.mipmap.red_marker

    init {
        initIds(map, config.routeName)
        mLineCap = config.lineCap
        mLineWidth = config.lineWidth
        mLineColor = config.lineColor
        mLineJoin = config.lineJoin
        originalIcon = config.originalIcon
        destinationIcon = config.destinationIcon
        init(map, context)
    }

    private fun initIds(map: NextbillionMap, name: String) {
        mMap = map
        routeLayerId = name + "_route_layer_id"
        routeSourceId = name + "_route_source_id"
        originLayerId = name + "_origin_layer_id"
        originSourceId = name + "_origin_source_id"
        destinationLayerId = name + "_destination_layer_id"
        destinationSourceId = name + "_destination_source_id"
        originIconId = name + "_origin_icon_id"
        destIconId = name + "_dest_icon_id"
    }

    private fun init(map: NextbillionMap, context: Context) {
        val style = map.style ?: return
        updateIcons(context, map)
        val routeLineGeoJsonOptions = GeoJsonOptions().withMaxZoom(16)
        val originGeoJsonOptions = GeoJsonOptions().withMaxZoom(16)
        val destinationGeoJsonOptions = GeoJsonOptions().withMaxZoom(16)
        routeSource = GeoJsonSource(routeSourceId, routeLineGeoJsonOptions)
        originSource = GeoJsonSource(originSourceId, originGeoJsonOptions)
        destinationSource = GeoJsonSource(destinationSourceId, destinationGeoJsonOptions)
        routeLayer = LineLayer(routeLayerId, routeSourceId).withProperties(
            PropertyFactory.lineWidth(mLineWidth),
            PropertyFactory.lineColor(mLineColor),
            PropertyFactory.lineCap(mLineCap),
            PropertyFactory.lineJoin(mLineJoin)
        )
        originLayer = SymbolLayer(originLayerId, originSourceId).withProperties(
            PropertyFactory.iconImage(originIconId)
        )
        destinationLayer = SymbolLayer(destinationLayerId, destinationSourceId).withProperties(
            PropertyFactory.iconImage(destIconId)
        )
        style.removeLayer(routeLayerId!!)
        style.removeLayer(originLayerId!!)
        style.removeLayer(destinationLayerId!!)
        style.removeSource(routeSourceId!!)
        style.removeSource(originSourceId!!)
        style.removeSource(destinationSourceId!!)
        style.addSource(routeSource)
        style.addSource(originSource)
        style.addSource(destinationSource)
        style.addLayer(routeLayer)
        style.addLayer(originLayer)
        style.addLayer(destinationLayer)
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    private fun updateIcons(context: Context, map: NextbillionMap) {
        val style = map.style ?: return
        style.removeImage(originIconId!!)
        style.removeImage(destIconId!!)
        val bitmap: Bitmap? = AppCompatResources.getDrawable(
            context,
            originalIcon
        )?.let {
            getBitmapFromDrawable(
                it
            )
        }
        bitmap?.let {
            style.addImage(originIconId!!, it)
        }
        val bitmap2 = AppCompatResources.getDrawable(
            context,
            destinationIcon
        )?.let {
            getBitmapFromDrawable(
                it
            )
        }
        bitmap2?.let {
            style.addImage(destIconId!!, it)
        }
    }

    fun setLineWidth(lineWidth: Float) {
        mLineWidth = lineWidth
        routeLayer.setProperties(
            PropertyFactory.lineWidth(mLineWidth),
            PropertyFactory.lineColor(mLineColor),
            PropertyFactory.lineCap(mLineCap),
            PropertyFactory.lineJoin(mLineJoin)
        )
    }

    fun setLineColor(lineColor: String) {
        mLineColor = lineColor
        routeLayer.setProperties(
            PropertyFactory.lineWidth(mLineWidth),
            PropertyFactory.lineColor(mLineColor),
            PropertyFactory.lineCap(mLineCap),
            PropertyFactory.lineJoin(mLineJoin)
        )
    }

    fun setLineCap(mLineCap: String) {
        this.mLineCap = mLineCap
        routeLayer.setProperties(
            PropertyFactory.lineWidth(mLineWidth),
            PropertyFactory.lineColor(mLineColor),
            PropertyFactory.lineCap(mLineCap),
            PropertyFactory.lineJoin(mLineJoin)
        )
    }

    fun setLineJoin(lineJoin: String) {
        mLineJoin = lineJoin
        routeLayer.setProperties(
            PropertyFactory.lineWidth(mLineWidth),
            PropertyFactory.lineColor(mLineColor),
            PropertyFactory.lineCap(mLineCap),
            PropertyFactory.lineJoin(mLineJoin)
        )
    }

    fun setOriginIcon(context: Context, nbMap: NextbillionMap, @DrawableRes resId: Int) {
        originalIcon = resId
        val style = nbMap.style ?: return
        style.removeImage(originIconId!!)
        val bitmap: Bitmap? = AppCompatResources.getDrawable(
            context,
            originalIcon
        )?.let {
            getBitmapFromDrawable(
                it
            )
        }
        bitmap?.let {
            style.addImage(originIconId!!, it)
        }

    }

    fun setDestinationIcon(context: Context, nbmapMap: NextbillionMap, @DrawableRes resId: Int) {
        destinationIcon = resId
        val style = nbmapMap.style ?: return
        style.removeImage(destIconId!!)
        val bitmap: Bitmap? = AppCompatResources.getDrawable(
            context,
            destinationIcon
        )?.let {
            getBitmapFromDrawable(
                it
            )
        }
        bitmap?.let {
            style.addImage(destIconId!!, it)
        }
    }

    fun remove() {
        mMap.style!!.removeLayer(routeLayerId!!)
        mMap.style!!.removeLayer(originLayerId!!)
        mMap.style!!.removeLayer(destinationLayerId!!)
        mMap.style!!.removeSource(routeSourceId!!)
        mMap.style!!.removeSource(originSourceId!!)
        mMap.style!!.removeSource(destinationSourceId!!)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Render
    ///////////////////////////////////////////////////////////////////////////
    fun drawRoute(route: NBRoute) {
        val geometry = route.geometry()
        drawRoute(geometry, route.legs()[0].startLocation(), route.legs()[0].endLocation())
        moveCamera(mMap, route.startLocation(), route.endLocation())
    }

    fun drawMatchedRoute(snapToRoadResponse: NBSnapToRoadResponse?) {
        if (snapToRoadResponse != null && "Ok" == snapToRoadResponse.status() && snapToRoadResponse.snappedPoints() != null && snapToRoadResponse.geometry() != null) {
            val geometries = snapToRoadResponse.geometry()
            val wayPoints: MutableList<NBLocation> = ArrayList()
            val origin = snapToRoadResponse.snappedPoints()[0].location
            val destination =
                snapToRoadResponse.snappedPoints()[snapToRoadResponse.snappedPoints().size - 1].location
            for (point in snapToRoadResponse.snappedPoints()) {
                wayPoints.add(point.location)
            }
            drawRoute(geometries, wayPoints)
            if (snapToRoadResponse.snappedPoints().size > 0) {
                moveCamera(mMap, origin, destination)
            }
        }
    }

    private fun drawRoute(geometry: String?, origin: NBLocation?, destination: NBLocation?) {
        val routeFeatureCollection = generateFeatureFromGeometry(geometry)
        routeSource.setGeoJson(routeFeatureCollection)
        originSource.setGeoJson(generateFeatureCollectionFromLocation(origin))
        destinationSource.setGeoJson(generateFeatureCollectionFromLocation(destination))
    }

    private fun drawRoute(geometries: List<String?>?, wayPoints: List<NBLocation>?) {
        val features: MutableList<Feature> = ArrayList()
        if (geometries != null) {
            for (geometry in geometries) {
                val routeFeatureCollection = generateFeatureFromGeometry(geometry)
                features.addAll(routeFeatureCollection.features()!!)
            }
        }
        val routeFeatureCollection = FeatureCollection.fromFeatures(features)
        routeSource.setGeoJson(routeFeatureCollection)
        if (wayPoints != null) {
            val wayPointsFeatureCollection = generateFeatureCollectionFromPoints(wayPoints)
            originSource.setGeoJson(wayPointsFeatureCollection)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Camera
    ///////////////////////////////////////////////////////////////////////////
    private fun calcMidLocation(origin: NBLocation?, dest: NBLocation?): NBLocation {
        val midLat = (origin!!.latitude + dest!!.latitude) / 2
        val midLng = (origin.longitude + dest.longitude) / 2
        return NBLocation(midLat, midLng)
    }

    private fun calcZoomLevel(origin: NBLocation?, dest: NBLocation?): Double {
        val originalPoint = Point.fromLngLat(
            origin!!.longitude, origin.latitude
        )
        val destPoint = Point.fromLngLat(
            dest!!.longitude, dest.latitude
        )
        val distanceInKM = TurfMeasurement.distance(originalPoint, destPoint)
        val exponent = calcExponent(distanceInKM)
        val zoomLevel = 13 - exponent
        return zoomLevel.coerceAtLeast(0) + 0.5
    }

    private fun calcExponent(distanceInKM: Double): Int {
        var ret = distanceInKM.roundToInt()
        var exponent = 0
        while (ret >= 2) {
            ret /= 2
            exponent++
        }
        return exponent
    }

    private fun moveCamera(map: NextbillionMap?, origin: NBLocation?, dest: NBLocation?) {
        val zoomLevel = calcZoomLevel(origin, dest)
        val midLocation = calcMidLocation(origin, dest)
        val position = CameraPosition.Builder()
            .target(LatLng(midLocation.latitude, midLocation.longitude))
            .zoom(zoomLevel)
            .build()
        map!!.animateCamera(CameraUpdateFactory.newCameraPosition(position), 600)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Feature
    ///////////////////////////////////////////////////////////////////////////
    private fun generateFeatureFromGeometry(geometry: String?): FeatureCollection {
        val features: MutableList<Feature> = ArrayList()
        val routeGeometry = LineString.fromPolyline(
            geometry!!, 5
        )
        val routeFeature = Feature.fromGeometry(routeGeometry)
        features.add(routeFeature)
        return FeatureCollection.fromFeatures(features)
    }

    private fun buildFeatureFromLocation(location: NBLocation?): Feature {
        return Feature.fromGeometry(
            Point.fromLngLat(
                location!!.longitude, location.latitude
            )
        )
    }

    private fun generateFeatureCollectionFromPoints(points: List<NBLocation>): FeatureCollection {
        val features: MutableList<Feature> = ArrayList()
        for (location in points) {
            features.add(buildFeatureFromLocation(location))
        }
        return FeatureCollection.fromFeatures(features)
    }

    private fun generateFeatureCollectionFromLocation(location: NBLocation?): FeatureCollection {
        return FeatureCollection.fromFeature(buildFeatureFromLocation(location))
    }

    companion object {
        ///////////////////////////////////////////////////////////////////////////
        // Utils
        ///////////////////////////////////////////////////////////////////////////
        fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
            return if (drawable is BitmapDrawable) {
                drawable.bitmap
            } else {
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }
        }
    }
}

