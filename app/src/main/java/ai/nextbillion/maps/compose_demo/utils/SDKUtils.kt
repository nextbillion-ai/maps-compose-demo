package ai.nextbillion.maps.compose_demo.utils

import android.app.Application

class SDKUtils private constructor(
    private val application: Application
) {

    companion object {
        private var instance: SDKUtils? = null

        fun init(application: Application){
            if(null == instance) {
                instance = SDKUtils(application)
            }
        }

        fun getApplicationContext() : Application {
            return instance?.application ?: throw NullPointerException("SDKUtils instance == null")
        }
    }

}