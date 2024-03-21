package ai.nextbillion.maps.compose_demo.client

import java.io.IOException

import ai.nextbillion.api.NBMapAPI
import ai.nextbillion.api.directions.NBDirectionsResponse
import ai.nextbillion.api.models.NBLocation
import ai.nextbillion.maps.Nextbillion
import retrofit2.Callback
import retrofit2.Response

class NBMapAPIClient {

    private var mapAPI: NBMapAPI

    constructor() {
        val key = Nextbillion.getAccessKey()
        val baseUrl = Nextbillion.getBaseUri()
        mapAPI = NBMapAPI.getInstance(key, baseUrl)
        mapAPI.mode = NBDirectionsConstants.MODE_CAR
    }

    constructor(baseUrl: String) {
        val key = Nextbillion.getAccessKey()
        mapAPI = NBMapAPI.getInstance(key, baseUrl)
        mapAPI.mode = NBDirectionsConstants.MODE_CAR
        mapAPI.isDebug = true
    }

    fun setMode(mode: String): NBMapAPIClient {
        mapAPI.mode = mode
        return this
    }

    @Throws(IOException::class)
    fun getDirections(origin: NBLocation, dest: NBLocation): Response<NBDirectionsResponse> {
        return mapAPI.getDirections(origin, dest)
    }

    @Throws(IOException::class)
    fun enQueueGetDirections(origin: NBLocation, dest: NBLocation, callback: Callback<NBDirectionsResponse>) {
        mapAPI.enqueueGetDirections(origin, dest, callback)
    }

}

