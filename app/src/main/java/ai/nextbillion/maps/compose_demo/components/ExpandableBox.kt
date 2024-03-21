package ai.nextbillion.maps.compose_demo.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
internal fun ExpandableBox(
    initialState: Boolean,
    visible: Boolean,
    durationMillis: Int = 200,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = durationMillis,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(durationMillis))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = durationMillis,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(durationMillis))
    }
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(initialState) }
            .apply { targetState = visible },
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        content()
    }
}