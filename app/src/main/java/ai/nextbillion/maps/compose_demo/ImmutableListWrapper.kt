
package ai.nextbillion.maps.compose_demo

import androidx.compose.runtime.Immutable

@Immutable
class ImmutableListWrapper<T: Any>(val items: List<T>)