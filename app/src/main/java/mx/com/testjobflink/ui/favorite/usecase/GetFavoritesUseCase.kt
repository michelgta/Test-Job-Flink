package mx.com.testjobflink.domain.usecase

import mx.com.testjobflink.data.repository.FavoriteRepository
import mx.com.testjobflink.base.BaseUseCase
import mx.com.testjobflink.data.models.Movie

interface GetFavoritesUseCase : BaseUseCase {
   suspend fun getFavorites(): List<Movie>
}

class GetFavoritesUseCaseImpl(private val favoriteRepository: FavoriteRepository) :
    GetFavoritesUseCase {

    override suspend fun getFavorites(): List<Movie> {
        return favoriteRepository.getFavorites()
    }

    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        favoriteRepository.updateFavoriteState(id, isFavorite)
    }
}