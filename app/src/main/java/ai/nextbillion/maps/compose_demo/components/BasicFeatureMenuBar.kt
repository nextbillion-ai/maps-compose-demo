package ai.nextbillion.maps.compose_demo.components

import ai.nextbillion.maps.compose_demo.ImmutableListWrapper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicFeatureMenuBar(
    modifier: Modifier,
    listWrapper: ImmutableListWrapper<String>,
    onStatusLabel:(@Composable (String) -> Unit),
    onItemClick: (String) -> Unit
) {
    var expandableState by rememberSaveable {  mutableStateOf(false) }
    Column(modifier = modifier) {
        MapMenuButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text =  if(expandableState) "Pack Up" else "Explain Menu",
            onClick = {
                expandableState = !expandableState
            }
        )
        ExpandableMenuList(
            visible = expandableState,
            listWrapper = listWrapper,
            onStatusLabel = onStatusLabel,
            onItemClick = onItemClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExpandableMenuList(
    visible: Boolean,
    listWrapper: ImmutableListWrapper<String>,
    onStatusLabel: (@Composable (String) -> Unit),
    onItemClick: (String) -> Unit
) {
    val currentOnItemClick by rememberUpdatedState(newValue = onItemClick)
    ExpandableBox(
        initialState = false,
        visible = visible,
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth().verticalScroll( rememberScrollState()),
            mainAxisSpacing = 8.dp
        ) {
            listWrapper.items.forEach {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    MapMenuButton(
                        text = it,
                        onClick = {
                            currentOnItemClick.invoke(it)
                        }
                    )
                    onStatusLabel(it)
                }
            }
        }
    }
}