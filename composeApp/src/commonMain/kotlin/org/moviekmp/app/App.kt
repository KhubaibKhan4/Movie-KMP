package org.moviekmp.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.moviekmp.app.data.model.Result
import org.moviekmp.app.theme.AppTheme
import org.moviekmp.app.ui.AppViewModel
import org.moviekmp.app.ui.component.MovieList
import org.moviekmp.app.utils.network.DataState
import org.moviekmp.app.viewmodel.PopularViewModel

@Composable
internal fun App() = AppTheme {

    val appViewModel: AppViewModel = AppViewModel()
    val popularViewModel: PopularViewModel = PopularViewModel()

    LaunchedEffect(true) {
        popularViewModel.nowPlayingView(1)
    }

    // Collect the data state
    val nowPlayingResponse by popularViewModel.nowPlayingResponse.collectAsState()


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (nowPlayingResponse) {
            is DataState.Loading -> {
                CircularProgressIndicator()
            }

            is DataState.Success -> {
                (nowPlayingResponse as DataState.Success<List<Result>>).data.let { movieList ->
                    MovieList(movieList)
                }
            }

            is DataState.Error -> {
                val error = (nowPlayingResponse as DataState.Error).exception
                Text(text = error.toString())
            }

            else -> {}
        }
    }


}


internal expect fun openUrl(url: String?)

