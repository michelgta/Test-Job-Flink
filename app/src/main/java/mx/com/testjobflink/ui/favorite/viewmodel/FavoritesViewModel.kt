package mx.com.testjobflink.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.com.testjobflink.base.BaseViewModel
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.domain.usecase.GetFavoritesUseCase
import mx.com.testjobflink.ui.favorite.model.FavoriteViewState


class FavoritesViewModel(private val useCase: GetFavoritesUseCase) : BaseViewModel() {

    private var _favoriteViewState = MutableLiveData<FavoriteViewState>()

    val favoriteMoviesViewState: LiveData<FavoriteViewState>
        get() = _favoriteViewState

    init {
        getFavoritesMovies()
    }

    fun getFavoritesMovies(){
        main {
            runCatching {
                useCase.getFavorites()
            }.onSuccess {
                handleFavoritesListResult(it)
            }.onFailure {
                _favoriteViewState.postValue(FavoriteViewState.OnEmptyFavorites)
            }
        }
    }

    fun updateFavorite(id: Long, isFavorite: Boolean) {
        main {
            runCatching {
                useCase.updateFavoriteState(id, isFavorite)
            }.onSuccess {
                getFavoritesMovies()
                _favoriteViewState.postValue(getFavoriteViewStateWhenUpdate(isFavorite))
            }
        }
    }

    private fun handleFavoritesListResult(movies: List<Movie>) {
        if (movies.isNullOrEmpty()) {
            _favoriteViewState.postValue(FavoriteViewState.OnEmptyFavorites)

        } else {
            _favoriteViewState.postValue(FavoriteViewState.OnResultFavorites(movies))
        }
    }

}