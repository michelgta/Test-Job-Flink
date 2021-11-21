package mx.com.testjobflink.data.repository

import mx.com.testjobflink.data.local.MoviesDao
import mx.com.testjobflink.data.mapper.toDomainModel
import mx.com.testjobflink.data.models.Movie

interface FavoriteRepository : BaseRepository {
    suspend fun getFavorites(): List<Movie>
    suspend fun updateFavoriteState(id: Long, isFavorite: Boolean)
}

class FavoriteRepositoryImpl(private val moviesDao: MoviesDao) : FavoriteRepository {

    override suspend fun getFavorites(): List<Movie>
       =  moviesDao.getFavoritesMovies().map { result ->
            result.toDomainModel()
        }.sortedByDescending { item -> item.voteAverage }


    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        moviesDao.updateFavorite(id, isFavorite)
    }
}