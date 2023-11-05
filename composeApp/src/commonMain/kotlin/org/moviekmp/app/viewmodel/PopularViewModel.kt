package org.moviekmp.app.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import org.moviekmp.app.data.model.Result
import org.moviekmp.app.repository.MovieRepository
import org.moviekmp.app.utils.network.DataState

class PopularViewModel : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val repo = MovieRepository()
    val nowPlayingResponse = MutableStateFlow<DataState<List<Result>>?>(DataState.Loading)

    fun nowPlayingView(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.popularMovie(page).collectLatest {
                nowPlayingResponse.value = it
            }
        }
    }
}