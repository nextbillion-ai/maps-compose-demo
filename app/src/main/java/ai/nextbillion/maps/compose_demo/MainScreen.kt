package ai.nextbillion.maps.compose_demo


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen() {
    val featureItems = stringArrayResource(id = R.array.main_feature_items)
    FlowRow(modifier = Modifier.padding(15.dp).fillMaxSize(), mainAxisSpacing = 15.dp, crossAxisSpacing = 10.dp) {
        featureItems.forEach {
            FeatureItemContent(item = it) {
                MainRepository.handleMainFeatureItemClick(it)
            }
        }
    }
}

@Composable
private fun FeatureItemContent(item: String, onClick: () -> Unit) {
    val currentOnClick by rememberUpdatedState(newValue = onClick)
    Button(onClick = currentOnClick) {
        Text(text = item)
    }
}