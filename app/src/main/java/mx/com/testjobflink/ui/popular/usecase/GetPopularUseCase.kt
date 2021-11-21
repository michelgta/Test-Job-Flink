package mx.com.testjobflink.domain.usecase

import mx.com.testjobflink.data.repository.FavoriteRepository
import mx.com.testjobflink.data.repository.PopularRepository

import mx.com.testjobflink.base.BaseUseCase
import mx.com.testjobflink.data.models.Movie

interface GetPopularUseCase : BaseUseCase {
    suspend fun getPopularDB(page: Int): List<Movie>
    suspend fun getPopularNetwork(page: Int): List<Movie>
}

class GetPopularUseCaseImpl(private val repositoryPopular: PopularRepository,
                                  private val repositoryFavorite: FavoriteRepository) : GetPopularUseCase {

    override suspend fun getPopularDB(page: Int): List<Movie> = repositoryPopular.getPopularDB(page)

    override suspend fun getPopularNetwork(page: Int): List<Movie> = repositoryPopular.getPopularNetwork(page)

    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        repositoryFavorite.updateFavoriteState(id, isFavorite)
    }
}
