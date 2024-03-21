package ai.nextbillion.maps.compose_demo.components

import ai.nextbillion.maps.compose_demo.R
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun ShowOpenGPSDialog(onPositiveClick: () -> Unit, onDismiss: () -> Unit) {
    SimpleDialog(
        positiveButtonResId = R.string.nb_map_location_gps_dialog_ok,
        negativeButtonResId = R.string.nb_map_location_gps_dialog_cancel,
        content = stringResource(id = R.string.nb_map_location_gps_no_open),
        onPositiveClick = onPositiveClick,
        onNegativeClick = onDismiss,
        onDismiss = onDismiss
    )
}

@Composable
fun SimpleDialog(
    @StringRes positiveButtonResId: Int,
    @StringRes negativeButtonResId: Int,
    content: String,
    onPositiveClick:()->Unit,
    onNegativeClick:()->Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .width(283.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp),
            color = Color(0XFFFFFFFF)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(19.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = content,
                        style = TextStyle(
                            color = Color(0XFF333333),
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 19.dp, end = 19.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .height(42.dp)
                ) {

                    SubmitButton(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(),
                        buttonHeight = 42.dp,
                        background = Color(0XFFF0F2F5),
                        textColor = Color(0xFF668EF7),
                        buttonTextRes = negativeButtonResId,
                        onClick = onNegativeClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    SubmitButton(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(),
                        background = Color(0xFF668EF7),
                        textColor = Color(0XFFFFFFFF),
                        buttonHeight = 42.dp,
                        buttonTextRes = positiveButtonResId,
                        onClick = onPositiveClick
                    )
                }
            }
        }
    }
}
