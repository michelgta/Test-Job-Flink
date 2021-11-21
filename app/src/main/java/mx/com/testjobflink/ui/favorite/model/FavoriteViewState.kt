package mx.com.testjobflink.ui.favorite.model

import mx.com.testjobflink.data.models.Movie

sealed class FavoriteViewState {
    object OnSuccessRemoveFavorite : FavoriteViewState()
    object OnSuccessAddFavorite : FavoriteViewState()
    object OnEmptyFavorites : FavoriteViewState()
    data class OnResultFavorites(val movies: List<Movie>) : FavoriteViewState()
}