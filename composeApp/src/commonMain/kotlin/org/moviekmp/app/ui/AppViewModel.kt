package org.moviekmp.app.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import org.moviekmp.app.data.model.Movies
import org.moviekmp.app.data.model.Result
import org.moviekmp.app.repository.MovieRepository
import org.moviekmp.app.utils.network.DataState

class AppViewModel : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val repo = MovieRepository()
    val searchData: MutableState<DataState<Movies>?> = mutableStateOf(null)

    val popularMovieResponse = MutableStateFlow<DataState<List<Result>>?>(DataState.Loading)
    val topRatedMovieResponse = MutableStateFlow<DataState<List<Result>>?>(DataState.Loading)
    val upComingMovieResponse = MutableStateFlow<DataState<List<Result>>?>(DataState.Loading)

    fun popularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.popularMovie(page).collectLatest {
                popularMovieResponse.value = it
            }
        }
    }

    fun topRatedMovies(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.topRatedMovie(page).collectLatest {
                topRatedMovieResponse.value = it
            }
        }
    }

    fun upcomingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.upComingMovie(page).collectLatest {
                upComingMovieResponse.value = it
            }
        }
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.searchMovie(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                    }
                    searchData.value = it
                }
        }
    }

}