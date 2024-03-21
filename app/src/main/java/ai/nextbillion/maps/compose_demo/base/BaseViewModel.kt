package ai.nextbillion.maps.compose_demo.base

import ai.nextbillion.maps.compose_demo.state.IUiEffect
import androidx.lifecycle.*
import ai.nextbillion.maps.compose_demo.state.IUiEvent
import ai.nextbillion.maps.compose_demo.state.IUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<Event : IUiEvent, State : IUiState, Effect : IUiEffect> :
    ViewModel() {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    /**
     * Get current UI view state
     */
    protected val currentState: State
        get() = uiState.value

    /**
     * Current ui view state
     */
    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    /**
     * UI operation with effect
     */
    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    /**
     * The special user event , such favorite, delete and so on
     */
    private val _event : MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        subscribeToEvents()
    }

    /**
     * Execute the suspended task
     */
    fun asyncLaunch(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context = context) {
        block.invoke(this)
    }.apply {
        asyncJobs.add(this)
    }

    /**
     * When ViewModel is about to be destroyed, cancel all ongoing missions
     */
    override fun onCleared() {
        _effect.close()
        val iterator = asyncJobs.iterator()
        while(iterator.hasNext()) {
            val job = iterator.next()
            if(!job.isCancelled) {
                job.cancel()
            }
            iterator.remove()
        }
        super.onCleared()
    }

    /**
     * Set the current view state
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Effect operation of UI
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        asyncLaunch(Dispatchers.IO) {
            _effect.send(effectValue)
        }
    }

    /**
     * Send user event
     */
    protected fun setEvent(event : Event) {
        val newEvent = event
        asyncLaunch(Dispatchers.IO) {
            _event.emit(newEvent)
        }
    }

    private fun subscribeToEvents() = asyncLaunch(Dispatchers.IO) {
        _event.collect {
            handleEvents(it)
        }
    }

    /**
     * Handle user event
     */
    abstract fun handleEvents(event: Event)

}