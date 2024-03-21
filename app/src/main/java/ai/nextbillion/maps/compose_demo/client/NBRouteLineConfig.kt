package ai.nextbillion.maps.compose_demo.client

import ai.nextbillion.maps.compose_demo.R
import ai.nextbillion.maps.style.layers.Property
import android.text.TextUtils
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

class NBRouteLineConfig(
    val lineWidth: Float,
    val routeName: String,
    val lineColor: String,
    val lineCap: String,
    val lineJoin: String,
    @field:DrawableRes val originalIcon: Int,
    @field:DrawableRes val destinationIcon: Int
) {

    class Builder {
        private var routeName: String? = null
        private var lineWidth = 0f
        private var lineColor: String? = null
        private var lineCap: String? = null
        private var lineJoin: String? = null

        @DrawableRes
        private var originalIcon = -1

        @DrawableRes
        private var destinationIcon = -1
        fun setRouteName(routeName: String?): Builder {
            this.routeName = routeName
            return this
        }

        fun setLineWidth(lineWidth: Float): Builder {
            this.lineWidth = lineWidth
            return this
        }

        fun setLineColor(lineColor: String?): Builder {
            this.lineColor = lineColor
            return this
        }

        fun setLineColor(@ColorInt lineColor: Int): Builder {
            if (lineColor < 0x1000000) {
                this.lineColor = String.format("#%06X", 0xFFFFFF and lineColor)
            } else {
                this.lineColor = String.format("#%08X", lineColor)
            }
            return this
        }

        fun setLineCap(lineCap: String?): Builder {
            this.lineCap = lineCap
            return this
        }

        fun setLineJoin(lineJoin: String?): Builder {
            this.lineJoin = lineJoin
            return this
        }

        fun setOriginIcon(@DrawableRes resId: Int): Builder {
            originalIcon = resId
            return this
        }

        fun setDestinationIcon(@DrawableRes resId: Int): Builder {
            destinationIcon = resId
            return this
        }

        fun build(): NBRouteLineConfig {
            if (TextUtils.isEmpty(routeName)) {
                throw RuntimeException("RouteName can not be null")
            }
            if (TextUtils.isEmpty(lineColor)) {
                lineColor = "#f54242"
            }
            if (TextUtils.isEmpty(lineCap)) {
                lineCap = Property.LINE_CAP_ROUND
            }
            if (TextUtils.isEmpty(lineJoin)) {
                lineJoin = Property.LINE_JOIN_ROUND
            }
            if (lineWidth < 0.01f) {
                lineWidth = 3.0f
            }
            if (originalIcon == -1) {
                originalIcon = R.mipmap.blue_marker
            }
            if (destinationIcon == -1) {
                destinationIcon = R.mipmap.red_marker
            }
            return NBRouteLineConfig(
                lineWidth,
                routeName!!,
                lineColor!!,
                lineCap!!,
                lineJoin!!,
                originalIcon,
                destinationIcon
            )
        }
    }
}

