package ai.nextbillion.maps.compose_demo.initializer

import ai.nextbillion.maps.Nextbillion
import ai.nextbillion.maps.compose_demo.utils.SDKUtils
import android.app.Application
import android.content.Context
import androidx.startup.Initializer

class AppDataInitStartup : Initializer<Boolean> {

    override fun create(context: Context): Boolean {
        SDKUtils.init(context as Application)
        //TODO: Replace your api key
        Nextbillion.getInstance(context, "YOUR-API-KEY")
        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}