package ai.nextbillion.maps.compose_demo.viewmodel


import ai.nextbillion.maps.compose_demo.base.BaseViewModel
import ai.nextbillion.maps.compose_demo.contract.GestureDetectorContract

class GestureDetectorViewModel :
    BaseViewModel<GestureDetectorContract.Event, GestureDetectorContract.State, GestureDetectorContract.Effect>(){


    override fun createInitialState(): GestureDetectorContract.State {
        return GestureDetectorContract.State(
            locationLatLng = null,
        )
    }

    override fun handleEvents(event: GestureDetectorContract.Event) {

    }

}